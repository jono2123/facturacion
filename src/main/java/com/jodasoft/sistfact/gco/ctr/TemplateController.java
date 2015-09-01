/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.dao.AlmacenModuloFacade;
import com.jodasoft.sistfact.gco.dao.CajaSesionFacade;
import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
import com.jodasoft.sistfact.gco.mdl.CajaSesion;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author javila
 */
@Named(value = "templateController")
@SessionScoped
public class TemplateController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of TemplateController
     */
    private Usuario usuario;
    private String almacen;
    private String url;
    private List<Permiso> permisos;
    private boolean tienePermiso;
    private boolean tienecajas;

    @EJB
    private AlmacenModuloFacade almacenModuloFacade;
    @EJB
    private CajaSesionFacade cajaSesionFacade;

    public TemplateController() {
    }

    public String logout() {
        List<CajaSesion> aux = cajaSesionFacade.openBoxesByUser(LoginController.getInstance().getUsuario());
        if (aux.isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/public/login.xhtml?faces-redirect=true";
        } else {
            for (CajaSesion cajaSesion : aux) {
                keepDialogOpen();

                displayErrorMessageToUser("La caja :" + cajaSesion.getCaseNumCaja().getCajaNumCaja() + ". Esta abierta! Cierre todas las cajas para continuar.");
                RequestContext.getCurrentInstance().update("form1:growl");

            }

        }

        return "";
    }

    public String goHome() {
        return "/private/principal.xhtml?faces-redirect=true";
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = LoginController.getInstance().getUsuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public boolean isTienecajas() {
        return getTieneCajas();
    }

    public void setTienecajas(boolean tienecajas) {
        this.tienecajas = tienecajas;
    }

    public boolean getLogueado() {
        return LoginController.getInstance().estaLogueado();
    }

    public boolean getTienePermiso() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        uri = uri.substring(uri.indexOf("private") - 1);
        permisos = LoginController.getInstance().getPermisos();
        if (uri.endsWith("principal.xhtml") || uri.endsWith("ventanas.xhtml")) {
            return true;
        }
        for (Permiso permiso : permisos) {
            if (permiso.getVentId().getVentUrl().equals(uri)) {
                return true;
            }
        }
        return false;
    }

    public boolean getPuedeAcceder() {
        return getLogueado() && getTienePermiso();
    }

    public boolean getTieneCajas() {
        List<AlmacenModulo> modulos = almacenModuloFacade.findByAlmaID(LoginController.getInstance().getUsuario().getRolId().getAlmaId());

        boolean cajas = false;
        for (AlmacenModulo am : modulos) {
            if (am.getModuId().getModuId() == 5) {
                cajas = true;

            }
        }
        if (cajas && LoginController.getInstance().getCajaSesion() == null) {
            return false;

        }
        return true;
    }

}
