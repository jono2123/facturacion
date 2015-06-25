/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author johnny
 */
@Named(value = "reporteVentasController")
@SessionScoped
public class ReporteVentasController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of ReporteVentasController
     */
    public ReporteVentasController() {
    }
    
}
