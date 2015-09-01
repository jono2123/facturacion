/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.ctr.exceptions.NonexistentEntityException;
import com.jodasoft.sistfact.gco.ctr.exceptions.PreexistingEntityException;
import com.jodasoft.sistfact.gco.ctr.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jodasoft.sistfact.gco.mdl.Caja;
import com.jodasoft.sistfact.gco.mdl.CajaSesion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author f35
 */
public class CajaSesionController implements Serializable {

    public CajaSesionController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CajaSesion cajaSesion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caja caseNumCaja = cajaSesion.getCaseNumCaja();
            if (caseNumCaja != null) {
                caseNumCaja = em.getReference(caseNumCaja.getClass(), caseNumCaja.getCajaId());
                cajaSesion.setCaseNumCaja(caseNumCaja);
            }
            em.persist(cajaSesion);
            if (caseNumCaja != null) {
                caseNumCaja.getCajaSesionList().add(cajaSesion);
                caseNumCaja = em.merge(caseNumCaja);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCajaSesion(cajaSesion.getCaseId()) != null) {
                throw new PreexistingEntityException("CajaSesion " + cajaSesion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CajaSesion cajaSesion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CajaSesion persistentCajaSesion = em.find(CajaSesion.class, cajaSesion.getCaseId());
            Caja caseNumCajaOld = persistentCajaSesion.getCaseNumCaja();
            Caja caseNumCajaNew = cajaSesion.getCaseNumCaja();
            if (caseNumCajaNew != null) {
                caseNumCajaNew = em.getReference(caseNumCajaNew.getClass(), caseNumCajaNew.getCajaId());
                cajaSesion.setCaseNumCaja(caseNumCajaNew);
            }
            cajaSesion = em.merge(cajaSesion);
            if (caseNumCajaOld != null && !caseNumCajaOld.equals(caseNumCajaNew)) {
                caseNumCajaOld.getCajaSesionList().remove(cajaSesion);
                caseNumCajaOld = em.merge(caseNumCajaOld);
            }
            if (caseNumCajaNew != null && !caseNumCajaNew.equals(caseNumCajaOld)) {
                caseNumCajaNew.getCajaSesionList().add(cajaSesion);
                caseNumCajaNew = em.merge(caseNumCajaNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cajaSesion.getCaseId();
                if (findCajaSesion(id) == null) {
                    throw new NonexistentEntityException("The cajaSesion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CajaSesion cajaSesion;
            try {
                cajaSesion = em.getReference(CajaSesion.class, id);
                cajaSesion.getCaseId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajaSesion with id " + id + " no longer exists.", enfe);
            }
            Caja caseNumCaja = cajaSesion.getCaseNumCaja();
            if (caseNumCaja != null) {
                caseNumCaja.getCajaSesionList().remove(cajaSesion);
                caseNumCaja = em.merge(caseNumCaja);
            }
            em.remove(cajaSesion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CajaSesion> findCajaSesionEntities() {
        return findCajaSesionEntities(true, -1, -1);
    }

    public List<CajaSesion> findCajaSesionEntities(int maxResults, int firstResult) {
        return findCajaSesionEntities(false, maxResults, firstResult);
    }

    private List<CajaSesion> findCajaSesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CajaSesion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CajaSesion findCajaSesion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CajaSesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajaSesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CajaSesion> rt = cq.from(CajaSesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
