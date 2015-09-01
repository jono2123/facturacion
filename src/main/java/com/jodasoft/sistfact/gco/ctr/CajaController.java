/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.ctr.exceptions.NonexistentEntityException;
import com.jodasoft.sistfact.gco.ctr.exceptions.PreexistingEntityException;
import com.jodasoft.sistfact.gco.ctr.exceptions.RollbackFailureException;
import com.jodasoft.sistfact.gco.dao.CajaFacade;
import com.jodasoft.sistfact.gco.dao.CajaSesionFacade;
import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Caja;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jodasoft.sistfact.gco.mdl.CajaTransaccion;
import java.util.ArrayList;
import java.util.List;
import com.jodasoft.sistfact.gco.mdl.CajaSesion;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Freddy Sumba
 */
@ManagedBean(name = "cajaController")
@ViewScoped
public class CajaController extends AbstractMB implements Serializable {

    private List<Caja> cajas;
    private Caja caja;
    private double valorInicial;
    private double valorFinal;
    private int activeIndex;
    private Permiso permiso;
    Almacen almacen;

    @EJB
    CajaFacade cajaFacade;
    @EJB
    CajaSesionFacade cajaSesionFacade;

    public CajaController() {
        this.cajas = new ArrayList<>();

        this.activeIndex = 0;
        almacen = LoginController.getInstance().getUsuario().getRolId().getAlmaId();

    }

    @PostConstruct
    public void llenarCajas() {
        this.permiso = LoginController.getInstance().getPermiso("Cajas de facturación");
        this.cajas = cajaFacade.findByAlmacen(almacen);

    }

    public CajaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
        this.cajas = new ArrayList<>();
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caja caja) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (caja.getCajaTransaccionList() == null) {
            caja.setCajaTransaccionList(new ArrayList<CajaTransaccion>());
        }
        if (caja.getCajaSesionList() == null) {
            caja.setCajaSesionList(new ArrayList<CajaSesion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<CajaTransaccion> attachedCajaTransaccionList = new ArrayList<CajaTransaccion>();
            for (CajaTransaccion cajaTransaccionListCajaTransaccionToAttach : caja.getCajaTransaccionList()) {
                cajaTransaccionListCajaTransaccionToAttach = em.getReference(cajaTransaccionListCajaTransaccionToAttach.getClass(), cajaTransaccionListCajaTransaccionToAttach.getCatrId());
                attachedCajaTransaccionList.add(cajaTransaccionListCajaTransaccionToAttach);
            }
            caja.setCajaTransaccionList(attachedCajaTransaccionList);
            List<CajaSesion> attachedCajaSesionList = new ArrayList<CajaSesion>();
            for (CajaSesion cajaSesionListCajaSesionToAttach : caja.getCajaSesionList()) {
                cajaSesionListCajaSesionToAttach = em.getReference(cajaSesionListCajaSesionToAttach.getClass(), cajaSesionListCajaSesionToAttach.getCaseId());
                attachedCajaSesionList.add(cajaSesionListCajaSesionToAttach);
            }
            caja.setCajaSesionList(attachedCajaSesionList);
            em.persist(caja);
            for (CajaTransaccion cajaTransaccionListCajaTransaccion : caja.getCajaTransaccionList()) {
                Caja oldCatrNumCajaOfCajaTransaccionListCajaTransaccion = cajaTransaccionListCajaTransaccion.getCatrNumCaja();
                cajaTransaccionListCajaTransaccion.setCatrNumCaja(caja);
                cajaTransaccionListCajaTransaccion = em.merge(cajaTransaccionListCajaTransaccion);
                if (oldCatrNumCajaOfCajaTransaccionListCajaTransaccion != null) {
                    oldCatrNumCajaOfCajaTransaccionListCajaTransaccion.getCajaTransaccionList().remove(cajaTransaccionListCajaTransaccion);
                    oldCatrNumCajaOfCajaTransaccionListCajaTransaccion = em.merge(oldCatrNumCajaOfCajaTransaccionListCajaTransaccion);
                }
            }
            for (CajaSesion cajaSesionListCajaSesion : caja.getCajaSesionList()) {
                Caja oldCaseNumCajaOfCajaSesionListCajaSesion = cajaSesionListCajaSesion.getCaseNumCaja();
                cajaSesionListCajaSesion.setCaseNumCaja(caja);
                cajaSesionListCajaSesion = em.merge(cajaSesionListCajaSesion);
                if (oldCaseNumCajaOfCajaSesionListCajaSesion != null) {
                    oldCaseNumCajaOfCajaSesionListCajaSesion.getCajaSesionList().remove(cajaSesionListCajaSesion);
                    oldCaseNumCajaOfCajaSesionListCajaSesion = em.merge(oldCaseNumCajaOfCajaSesionListCajaSesion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCaja(caja.getCajaId()) != null) {
                throw new PreexistingEntityException("Caja " + caja + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caja caja) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caja persistentCaja = em.find(Caja.class, caja.getCajaId());
            List<CajaTransaccion> cajaTransaccionListOld = persistentCaja.getCajaTransaccionList();
            List<CajaTransaccion> cajaTransaccionListNew = caja.getCajaTransaccionList();
            List<CajaSesion> cajaSesionListOld = persistentCaja.getCajaSesionList();
            List<CajaSesion> cajaSesionListNew = caja.getCajaSesionList();
            List<CajaTransaccion> attachedCajaTransaccionListNew = new ArrayList<CajaTransaccion>();
            for (CajaTransaccion cajaTransaccionListNewCajaTransaccionToAttach : cajaTransaccionListNew) {
                cajaTransaccionListNewCajaTransaccionToAttach = em.getReference(cajaTransaccionListNewCajaTransaccionToAttach.getClass(), cajaTransaccionListNewCajaTransaccionToAttach.getCatrId());
                attachedCajaTransaccionListNew.add(cajaTransaccionListNewCajaTransaccionToAttach);
            }
            cajaTransaccionListNew = attachedCajaTransaccionListNew;
            caja.setCajaTransaccionList(cajaTransaccionListNew);
            List<CajaSesion> attachedCajaSesionListNew = new ArrayList<CajaSesion>();
            for (CajaSesion cajaSesionListNewCajaSesionToAttach : cajaSesionListNew) {
                cajaSesionListNewCajaSesionToAttach = em.getReference(cajaSesionListNewCajaSesionToAttach.getClass(), cajaSesionListNewCajaSesionToAttach.getCaseId());
                attachedCajaSesionListNew.add(cajaSesionListNewCajaSesionToAttach);
            }
            cajaSesionListNew = attachedCajaSesionListNew;
            caja.setCajaSesionList(cajaSesionListNew);
            caja = em.merge(caja);
            for (CajaTransaccion cajaTransaccionListOldCajaTransaccion : cajaTransaccionListOld) {
                if (!cajaTransaccionListNew.contains(cajaTransaccionListOldCajaTransaccion)) {
                    cajaTransaccionListOldCajaTransaccion.setCatrNumCaja(null);
                    cajaTransaccionListOldCajaTransaccion = em.merge(cajaTransaccionListOldCajaTransaccion);
                }
            }
            for (CajaTransaccion cajaTransaccionListNewCajaTransaccion : cajaTransaccionListNew) {
                if (!cajaTransaccionListOld.contains(cajaTransaccionListNewCajaTransaccion)) {
                    Caja oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion = cajaTransaccionListNewCajaTransaccion.getCatrNumCaja();
                    cajaTransaccionListNewCajaTransaccion.setCatrNumCaja(caja);
                    cajaTransaccionListNewCajaTransaccion = em.merge(cajaTransaccionListNewCajaTransaccion);
                    if (oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion != null && !oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion.equals(caja)) {
                        oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion.getCajaTransaccionList().remove(cajaTransaccionListNewCajaTransaccion);
                        oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion = em.merge(oldCatrNumCajaOfCajaTransaccionListNewCajaTransaccion);
                    }
                }
            }
            for (CajaSesion cajaSesionListOldCajaSesion : cajaSesionListOld) {
                if (!cajaSesionListNew.contains(cajaSesionListOldCajaSesion)) {
                    cajaSesionListOldCajaSesion.setCaseNumCaja(null);
                    cajaSesionListOldCajaSesion = em.merge(cajaSesionListOldCajaSesion);
                }
            }
            for (CajaSesion cajaSesionListNewCajaSesion : cajaSesionListNew) {
                if (!cajaSesionListOld.contains(cajaSesionListNewCajaSesion)) {
                    Caja oldCaseNumCajaOfCajaSesionListNewCajaSesion = cajaSesionListNewCajaSesion.getCaseNumCaja();
                    cajaSesionListNewCajaSesion.setCaseNumCaja(caja);
                    cajaSesionListNewCajaSesion = em.merge(cajaSesionListNewCajaSesion);
                    if (oldCaseNumCajaOfCajaSesionListNewCajaSesion != null && !oldCaseNumCajaOfCajaSesionListNewCajaSesion.equals(caja)) {
                        oldCaseNumCajaOfCajaSesionListNewCajaSesion.getCajaSesionList().remove(cajaSesionListNewCajaSesion);
                        oldCaseNumCajaOfCajaSesionListNewCajaSesion = em.merge(oldCaseNumCajaOfCajaSesionListNewCajaSesion);
                    }
                }
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
                Integer id = caja.getCajaId();
                if (findCaja(id) == null) {
                    throw new NonexistentEntityException("The caja with id " + id + " no longer exists.");
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
            Caja caja;
            try {
                caja = em.getReference(Caja.class, id);
                caja.getCajaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caja with id " + id + " no longer exists.", enfe);
            }
            List<CajaTransaccion> cajaTransaccionList = caja.getCajaTransaccionList();
            for (CajaTransaccion cajaTransaccionListCajaTransaccion : cajaTransaccionList) {
                cajaTransaccionListCajaTransaccion.setCatrNumCaja(null);
                cajaTransaccionListCajaTransaccion = em.merge(cajaTransaccionListCajaTransaccion);
            }
            List<CajaSesion> cajaSesionList = caja.getCajaSesionList();
            for (CajaSesion cajaSesionListCajaSesion : cajaSesionList) {
                cajaSesionListCajaSesion.setCaseNumCaja(null);
                cajaSesionListCajaSesion = em.merge(cajaSesionListCajaSesion);
            }
            em.remove(caja);
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

    public List<Caja> findCajaEntities() {
        return findCajaEntities(true, -1, -1);
    }

    public List<Caja> findCajaEntities(int maxResults, int firstResult) {
        return findCajaEntities(false, maxResults, firstResult);
    }

    private List<Caja> findCajaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caja.class));
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

    public Caja findCaja(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caja.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caja> rt = cq.from(Caja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void nuevoCaja() {

        vaciar();
        activeIndex = 1;

    }

    public void vaciar() {
        this.caja = new Caja();
        this.caja.setCajaNumCaja(null);
        this.caja.setCajaNumFactura(null);
        this.caja.setCajaIpImpresion("");
        activeIndex = 1;
    }

    public void onRowSelect(SelectEvent event) {
        if (event != null) {
            try {
                setCaja((Caja) event.getObject());
            } catch (Exception e) {
            }
        }
    }

    public void editCaja(Caja caja) {
        activeIndex = 1;
        this.caja = caja;
        setCaja(caja);

    }

    public void deleteCaja(Caja caja) {
        this.caja = caja;
        cajaFacade.remove(this.caja);
        displayInfoMessageToUser("Caja eliminada correctamente");
        cajas.remove(this.caja);
    }

    public void guardar() throws RollbackFailureException, Exception {
        permiso = LoginController.getInstance().getPermiso("Cajas de facturación");

        if (caja == null) {
            caja = new Caja();
        }
        if (caja.getCajaId() == null) {
            if (permiso.getPermCrear() != null) {
                if (permiso.getPermCrear()) {
                    Almacen almacen = LoginController.getInstance().getUsuario().getRolId().getAlmaId();
                    caja.setAlmaId(almacen);
                    cajaFacade.save(caja);
                    this.cajas = cajaFacade.findByAlmacen(almacen);

                } else {
                    displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                    return;
                }
            }

        } else {
            if (permiso.getPermModificar() != null) {
                if (permiso.getPermModificar()) {
                    cajaFacade.edit(caja);
                } else {
                    displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                    return;
                }
            }

        }
        activeIndex = 0;
    }

    public String estadoCaja(Caja caja) {
        caja = cajaFacade.findByIdCaja(caja.getCajaId());

        String estado = "Cerrada";
        if (caja.getCajaSesionList().size() > 0) {
            estado = caja.getCajaSesionList().get(caja.getCajaSesionList().size() - 1).getCaseEstado();
        }

        return estado;
    }

    public void abrirCaja() {

        if (caja != null) {
            if (cajaSesionFacade.findSingIn(caja)) {
                CajaSesion sesion = new CajaSesion();
                sesion.setCaseEstado("Abierta");
                sesion.setCaseIdUsuario(LoginController.getInstance().getUsuario());
                sesion.setCaseMontoApertura(valorInicial);
                sesion.setCaseMontoCierre(0.0);
                sesion.setCaseNumCaja(caja);
                Calendar calendar = Calendar.getInstance();
                sesion.setCaseTimeApertura(calendar.getTime());
                sesion.setCaseTimeCierre(null);
                cajaSesionFacade.create(sesion);
                

                cajaSesionFacade.setCookie("sesion_jodasoft_" + LoginController.getInstance().getUsuario().getUsuaNombre(), sesion.getCaseId() + "", 999999999);
//                FacesContext.getCurrentInstance()
//                        .getExternalContext()
//                        .addResponseCookie("sesion_jodasoft_" + LoginController.getInstance().getUsuario().getUsuaNombre(), sesion.getCaseId() + "", null);
                LoginController.getInstance().setCajaSesion(sesion);
                displayInfoMessageToUser("Apertura de caja correcta.");

            } else {
                displayErrorMessageToUser("La caja ya esta abierta");
            }
        } else {
            displayErrorMessageToUser("Seleccione una caja");
        }

    }

    public void cerrarCaja() {
        if (caja != null) {
            if (!cajaSesionFacade.findSingIn(caja)) {
                CajaSesion sesionAbierta = cajaSesionFacade.findSesion(caja);
                if (Objects.equals(sesionAbierta.getCaseIdUsuario().getUsuaId(), LoginController.getInstance().getUsuario().getUsuaId())) {
                    sesionAbierta.setCaseEstado("Cerrada");
                    sesionAbierta.setCaseMontoCierre(valorFinal);
                    Calendar calendar = Calendar.getInstance();
                    sesionAbierta.setCaseTimeCierre(calendar.getTime());
                    cajaSesionFacade.edit(sesionAbierta);
                    LoginController.getInstance().setCajaSesion(null);
                    cajaSesionFacade.setCookie("sesion_jodasoft_" + LoginController.getInstance().getUsuario().getUsuaNombre(), 0 + "", 9999999);
//                    FacesContext.getCurrentInstance()
//                            .getExternalContext()
//                            .addResponseCookie("sesion_jodasoft_" + LoginController.getInstance().getUsuario().getUsuaNombre(), 0 + "", null);
                } else {
                    displayInfoMessageToUser("La caja seleccionada no fue abierta por el usuario: " + LoginController.getInstance().getUsuario().getUsuaNombre());
                }
                displayInfoMessageToUser("Cierre de caja correcta.");
            } else {
                displayErrorMessageToUser("La caja ya esta cerrada.");
            }
        } else {
            displayErrorMessageToUser("Seleccione una caja");
        }

    }

    /**
     * @Author Freddy Sumba getter and setter
     */
    public List<Caja> getCajas() {
        this.cajas = cajaFacade.findByAlmacen(almacen);
        return cajas;
    }

    public void setCajas(List<Caja> cajas) {
        this.cajas = cajas;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Caja getCaja() {
        if (caja == null) {
            caja = new Caja();
        }
        return caja;

    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public double getValorInicial() {

        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

}
