/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 *
 * @author johnny
 */
@Named(value = "verFacturaController")
@SessionScoped
public class VerFacturaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of VerFacturaController
     */
    private Factura factura;
    private Permiso permiso;
    private String guia;
    public VerFacturaController() {
    }
    @EJB
    private com.jodasoft.sistfact.gco.dao.FacturaFacade facturaFacade;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    public static VerFacturaController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{verFacturaController}", VerFacturaController.class);
        return (VerFacturaController) ex.getValue(context);
    }
    
    public boolean getAnulada(){
        return factura.getFactEstado().intValue()==2;
    }
    
    public boolean getImpresa(){
        return factura.getFactEstado().intValue()==1;
    }

    public Permiso getPermiso() {
        if(permiso==null)
            permiso=LoginController.getInstance().getPermiso("Ver Factura");
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }
    
    public void anular(){
        facturaFacade.anularFactura(factura);
        displayInfoMessageToUser("Factura anulada Exitosamente");
    }
    
    public String salir(){
        return "reporteVentas.xhtml?faces-redirect=true";
    }
    
    public boolean getPuedeAnular(){
        return (!getAnulada() & getPermiso().getPermEliminar());
    }
}
