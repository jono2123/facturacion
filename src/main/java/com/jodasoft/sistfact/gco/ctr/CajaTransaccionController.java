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
import com.jodasoft.sistfact.gco.mdl.CajaTransaccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author f35
 */
public class CajaTransaccionController implements Serializable {

    public CajaTransaccionController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CajaTransaccion cajaTransaccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caja catrNumCaja = cajaTransaccion.getCatrNumCaja();
            if (catrNumCaja != null) {
                catrNumCaja = em.getReference(catrNumCaja.getClass(), catrNumCaja.getCajaId());
                cajaTransaccion.setCatrNumCaja(catrNumCaja);
            }
            em.persist(cajaTransaccion);
            if (catrNumCaja != null) {
                catrNumCaja.getCajaTransaccionList().add(cajaTransaccion);
                catrNumCaja = em.merge(catrNumCaja);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCajaTransaccion(cajaTransaccion.getCatrId()) != null) {
                throw new PreexistingEntityException("CajaTransaccion " + cajaTransaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CajaTransaccion cajaTransaccion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CajaTransaccion persistentCajaTransaccion = em.find(CajaTransaccion.class, cajaTransaccion.getCatrId());
            Caja catrNumCajaOld = persistentCajaTransaccion.getCatrNumCaja();
            Caja catrNumCajaNew = cajaTransaccion.getCatrNumCaja();
            if (catrNumCajaNew != null) {
                catrNumCajaNew = em.getReference(catrNumCajaNew.getClass(), catrNumCajaNew.getCajaId());
                cajaTransaccion.setCatrNumCaja(catrNumCajaNew);
            }
            cajaTransaccion = em.merge(cajaTransaccion);
            if (catrNumCajaOld != null && !catrNumCajaOld.equals(catrNumCajaNew)) {
                catrNumCajaOld.getCajaTransaccionList().remove(cajaTransaccion);
                catrNumCajaOld = em.merge(catrNumCajaOld);
            }
            if (catrNumCajaNew != null && !catrNumCajaNew.equals(catrNumCajaOld)) {
                catrNumCajaNew.getCajaTransaccionList().add(cajaTransaccion);
                catrNumCajaNew = em.merge(catrNumCajaNew);
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
                Integer id = cajaTransaccion.getCatrId();
                if (findCajaTransaccion(id) == null) {
                    throw new NonexistentEntityException("The cajaTransaccion with id " + id + " no longer exists.");
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
            CajaTransaccion cajaTransaccion;
            try {
                cajaTransaccion = em.getReference(CajaTransaccion.class, id);
                cajaTransaccion.getCatrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajaTransaccion with id " + id + " no longer exists.", enfe);
            }
            Caja catrNumCaja = cajaTransaccion.getCatrNumCaja();
            if (catrNumCaja != null) {
                catrNumCaja.getCajaTransaccionList().remove(cajaTransaccion);
                catrNumCaja = em.merge(catrNumCaja);
            }
            em.remove(cajaTransaccion);
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

    public List<CajaTransaccion> findCajaTransaccionEntities() {
        return findCajaTransaccionEntities(true, -1, -1);
    }

    public List<CajaTransaccion> findCajaTransaccionEntities(int maxResults, int firstResult) {
        return findCajaTransaccionEntities(false, maxResults, firstResult);
    }

    private List<CajaTransaccion> findCajaTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CajaTransaccion.class));
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

    public CajaTransaccion findCajaTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CajaTransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajaTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CajaTransaccion> rt = cq.from(CajaTransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
