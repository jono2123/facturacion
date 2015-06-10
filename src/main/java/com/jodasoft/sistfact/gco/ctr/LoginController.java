/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
    public String login(){
        usuario=null;
        usuario = usuarioFacade.findByUserAndPass(nomUsuario, pass);
        if(usuario!=null){
            if (usuario.getAlmacen().getAlmaEstado()==0){
                return "/private/principal.xhtml?faces-redirect=true";
            }else if(usuario.getAlmacen().getAlmaEstado()==1){
                displayErrorMessageToUser("El Usuario ingresado tiene restringido el ingreso\nComuníquese con el administrador para más información");
            }else {
                displayErrorMessageToUser("Nombre de Usuario o Contraseña incorrectos");
            }
            
        }else
        {
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
    
    public boolean estaLogueado(){
        return usuario!=null;
    }
    
    
    
    
}