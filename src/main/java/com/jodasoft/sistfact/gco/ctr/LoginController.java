/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.CajaSesion;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 *
 * @author javila
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of LoginController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.UsuarioFacade usuarioFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.PermisoFacade permisoFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.CajaSesionFacade cajaSesionFacade;

    public LoginController() {
    }

    public static LoginController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{loginController}", LoginController.class);
        return (LoginController) ex.getValue(context);
    }

    private String nomUsuario;
    private String pass;
    private Usuario usuario;
    private List<Permiso> permisos;
    private CajaSesion cajaSesion;

    public String login() {
        usuario = null;
        usuario = usuarioFacade.findByUserAndPass(nomUsuario, pass);
        if (usuario != null) {
            if (usuario.getRolId().getAlmaId().getAlmaEstado() == 0) {
                cajaSesion=getCajaSesion();
                return "/private/principal.xhtml?faces-redirect=true";
            } else if (usuario.getRolId().getAlmaId().getAlmaEstado() == 1) {
                displayErrorMessageToUser("El Usuario ingresado tiene restringido el ingreso\nComuníquese con el administrador para más información");
            } else {
                displayErrorMessageToUser("Nombre de Usuario o Contraseña incorrectos");
            }

        } else {
            displayErrorMessageToUser("Nombre de Usuario o Contraseña incorrectos");
        }
        return null;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean estaLogueado() {
        return usuario != null;
    }

    public List<Permiso> getPermisos() {
        if (permisos == null) {
            permisos = permisoFacade.findByRol(usuario.getRolId());
        }
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Permiso getPermiso(String nombre) {
        for (Permiso permiso : getPermisos()) {
            if (permiso.getVentId().getVentNombre().equals(nombre)) {
                return permiso;
            }
        }
        return null;
    }

    public CajaSesion getCajaSesion() {
        
        this.cajaSesion= cajaSesionFacade.findByUser(usuario);
        return cajaSesion;
    }
    

    public void setCajaSesion(CajaSesion cajaSesion) {
        this.cajaSesion = cajaSesion;
    }
    

}
