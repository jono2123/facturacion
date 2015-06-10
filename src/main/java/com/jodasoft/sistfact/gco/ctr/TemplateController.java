/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Usuario;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author javila
 */
@Named(value = "templateController")
@RequestScoped
public class TemplateController implements Serializable {

    /**
     * Creates a new instance of TemplateController
     */
    private Usuario usuario;
    private String almacen;
    
    public TemplateController() {
    }

    public Usuario getUsuario() {
        if(usuario==null)
            usuario=LoginController.getInstance().getUsuario();
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
    public boolean getLogueado(){
        return LoginController.getInstance().estaLogueado();
    }
    
}
