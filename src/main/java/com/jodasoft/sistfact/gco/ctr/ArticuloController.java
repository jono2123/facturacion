/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author javila
 */
@Named(value = "articuloController")
@RequestScoped
public class ArticuloController extends AbstractMB {

    /**
     * Creates a new instance of ArticuloController
     */
    private String codigo;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private boolean iva;
    private int umedida_id;
    private String infoAdicional;
    
    /////////////////////ejb del facade
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade clienteFacade;
    
    public ArticuloController() {
    }
    
    ////////////////////////// Gets y Sets //////////////////////////////////////////

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public int getUmedida_id() {
        return umedida_id;
    }

    public void setUmedida_id(int umedida_id) {
        this.umedida_id = umedida_id;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }
    
    
    
    
}
