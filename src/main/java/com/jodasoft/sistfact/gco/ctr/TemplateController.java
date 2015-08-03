/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author javila
 */
@Named(value = "templateController")
@SessionScoped
public class TemplateController implements Serializable {

    /**
     * Creates a new instance of TemplateController
     */
    private Usuario usuario;
    private String almacen;
    private String url;
    private List<Permiso> permisos;
    private boolean tienePermiso;
    

    public TemplateController() {
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/public/login.xhtml?faces-redirect=true";
    }
    
    public String goHome(){
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

    public boolean getLogueado() {
        return LoginController.getInstance().estaLogueado();
    }

    public boolean getTienePermiso() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        uri = uri.substring(uri.indexOf("private")-1);
        permisos=LoginController.getInstance().getPermisos();
        if(uri.endsWith("principal.xhtml")||uri.endsWith("ventanas.xhtml")){
            return true;
        }
        for(Permiso permiso:permisos)
        {
            if(permiso.getVentId().getVentUrl().equals(uri)){
                return true;
            }
        }
        return false;
    }


    
    public boolean getPuedeAcceder(){
        return getLogueado() && getTienePermiso();
    }
    
}
