/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.util.exp.PaisValidadorException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author johnny
 */
@ManagedBean(name = "paisController")
@ViewScoped
public class PaisController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of PaisController
     */
    private Pais pais;
    private String nombre;
    private List<Pais> paises;
    
    @EJB
    com.jodasoft.sistfact.gco.dao.PaisFacade paisFacade;
    
    public PaisController() {
    }
    
    public void vaciaTextos(){
        setNombre("");
        setPais(new Pais());
    }
    
    public void savePais(){
        pais = new Pais();
        pais.setPaisNombre(nombre);
        try {
            paisFacade.save(pais);
            closeDialog();
            displayInfoMessageToUser("País creado correctamente");
            vaciaTextos();
        } catch (PaisValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    ////////////////////gets y sets//////////////////////////////

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public List<Pais> getPaises() {
        if(paises==null)
            paises= paisFacade.findAll();
        return paises;
    }
    
    

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
    
    
    
}
