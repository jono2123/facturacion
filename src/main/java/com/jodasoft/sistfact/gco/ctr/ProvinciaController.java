/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.mdl.Provincia;
import com.jodasoft.sistfact.gco.util.exp.ProvinciaValidadorException;
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
@ManagedBean(name = "provinciaController")
@ViewScoped
public class ProvinciaController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of ProvinciaController
     */
    @EJB
    com.jodasoft.sistfact.gco.dao.ProvinciaFacade provinciaFacade;
     @EJB
    com.jodasoft.sistfact.gco.dao.PaisFacade paisFacade;
    
    
    private List<Pais> paises;
    private Pais pais;
    private Provincia provincia;
    private String nombre;
    private int idPais;
    
    public ProvinciaController() {
    }

    public void vaciaTextos(){
        provincia= new Provincia();
        pais=null;
        idPais=0;
        setNombre("");
        
    }
    
    public void saveProvincia()
    {
        pais=paisFacade.find(idPais);
        provincia= new Provincia();
        provincia.setPaisId(pais);
        provincia.setProvNombre(nombre);
        try {
            provinciaFacade.save(provincia);
            closeDialog();
            displayInfoMessageToUser("Provincia creada correctamente");
            vaciaTextos();
        } catch (ProvinciaValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    /////////////////////////gets y sets//////////////////////
    public List<Pais> getPaises() {
        if(paises==null)
            paises=paisFacade.findAll();
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }
    
  
    
    
    
}
