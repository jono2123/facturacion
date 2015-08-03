/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
import com.jodasoft.sistfact.gco.mdl.Modulo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author johnny
 */
@Named(value = "principalControlador")
@SessionScoped
public class PrincipalControlador extends AbstractMB implements Serializable {

    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenModuloFacade almoFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ModuloFacade moduloFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.VentanaFacade ventanaFacade;
    /**
     * Creates a new instance of PrincipalControlador
     */
    List<AlmacenModulo> modulos;

    public PrincipalControlador() {
    }

    public List<AlmacenModulo> getModulos() {
        if (modulos == null) {
            modulos = almoFacade.findByAlmaID(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return modulos;
    }

    public void setModulos(List<AlmacenModulo> modulos) {
        this.modulos = modulos;
    }
    
    public String ingresar(Modulo modulo){
        VentanasController.getInstance().setModulo(modulo);
        return "ventanas.xhtml?faces-redirect=true";
    }

}
