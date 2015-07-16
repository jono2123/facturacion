/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Ciudad;
import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.mdl.Provincia;
import com.jodasoft.sistfact.gco.mdl.Zona;
import com.jodasoft.sistfact.gco.util.exp.CiudadValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "zonaController")
@ViewScoped
public class ZonaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ZonaController
     */
    public List<Pais> paises;
    public List<Provincia> provincias;
    public List<Ciudad> ciudades;
    public Pais pais;
    public Provincia provincia;
    public Ciudad ciudad;
    public Zona zona;
    public int idPais;
    public int idCiudad;
    public int idProvincia;
    public int idZona;
    public String nombre;

    @EJB
    com.jodasoft.sistfact.gco.dao.ZonaFacade zonaFacade;
    @EJB
    com.jodasoft.sistfact.gco.dao.CiudadFacade ciudadFacade;
    @EJB
    com.jodasoft.sistfact.gco.dao.ProvinciaFacade provinciaFacade;
    @EJB
    com.jodasoft.sistfact.gco.dao.PaisFacade paisFacade;

    public ZonaController() {
    }
    
    public void onPaisChange(){
        if(idPais!=0){
            pais= paisFacade.find(idPais);
            provincias= pais.getProvinciaList();
            ciudades=null;
            idProvincia=0;
            idCiudad=0;
        }else{
            ciudades=null;
            provincias=null;
            idProvincia=0;
            idCiudad=0;
        }
    }
    
    public void onProvinciaChange(){
        if(idProvincia!=0){
            provincia=provinciaFacade.find(idProvincia);
            ciudades=provincia.getCiudadList();
            idCiudad=0;
        }else{
            ciudades=null;
            idCiudad=0;
        }
    }
    
    public void limpiarTextos(){
        nombre="";
        ciudad=null;
        provincia=null;
        pais=null;
        provincias=null;
        ciudades=null;
        zona = new Zona();
        idCiudad=0;
        idPais=0;
        idProvincia=0;
        
    }
    
    
    public void saveZona(){
        zona = new Zona();
        ciudad=ciudadFacade.find(idCiudad);
        zona.setCiudId(ciudad);
        zona.setZonaNombre(nombre);
        try {
            zonaFacade.save(zona);
            closeDialog();
            displayInfoMessageToUser("Zona Creada Correctamente");
            limpiarTextos();
        } catch (CiudadValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
        
       
    }
    
    

    public List<Pais> getPaises() {
        if (paises == null) {
            paises = paisFacade.findAll();
        }
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Provincia> getProvincias() {
        if(provincias==null)
        {
            provincias= new ArrayList<Provincia>();
        }
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Ciudad> getCiudades() {
        if(ciudades==null)
        {
            ciudades= new ArrayList<Ciudad>();
        }
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

}
