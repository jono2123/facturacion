/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.util.exp.AlmacenValidadorException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author javila
 */
//@Named(value = "administracionController")
//@RequestScoped
@ManagedBean(name="administracionController")
@ViewScoped
public class AdministracionController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of AdministracionController
     */

    private Permiso permiso;
    private Almacen almacen;
    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenFacade almacenFacade;
    public AdministracionController() {
    }
    
    public void updateAlmacen(){
        try {
            almacenFacade.update(almacen);
            closeDialog();
            displayInfoMessageToUser("Configuraciones guardadas correctamente");
        } catch (AlmacenValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
        
    }
    
    
    
    
    
    
    //////////////////////gets y sets//////////////////////////////////

    public Almacen getAlmacen() {
        if(almacen==null){
            int id = LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId().intValue();
            almacen = almacenFacade.find(id);
        }
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Permiso getPermiso() {
        if(permiso==null)
            permiso=LoginController.getInstance().getPermiso("Configuraciones");
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
    
    
    
    
   
    
}
