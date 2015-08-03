/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Modulo;
import com.jodasoft.sistfact.gco.mdl.Ventana;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author johnny
 */
@Named(value = "ventanasController")
@SessionScoped
public class VentanasController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of VentanasController
     */
    Modulo modulo;
    List<Ventana> ventanas;

    public VentanasController() {
    }

    public static VentanasController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{ventanasController}", VentanasController.class);
        return (VentanasController) ex.getValue(context);
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
        ventanas = new ArrayList<Ventana>();
        for (Ventana ventana : modulo.getVentanaList()) {
            if (ventana.getVentMostrarMenu()) {
                ventanas.add(ventana);
            }
        }
    }
    
    public String ingresar(String url){
        return url+"?faces-redirect=true";
    }

    public List<Ventana> getVentanas() {
        if (ventanas == null) {
            ventanas = new ArrayList<Ventana>();
        }
        return ventanas;
    }

    public void setVentanas(List<Ventana> ventanas) {
        this.ventanas = ventanas;
    }

}
