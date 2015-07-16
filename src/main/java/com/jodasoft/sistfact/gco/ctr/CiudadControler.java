/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Ciudad;
import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.mdl.Provincia;
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
@ManagedBean(name = "ciudadControler")
@ViewScoped
public class CiudadControler extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of CiudadControler
     */
    private List<Pais> paises;
    private List<Provincia> provincias;
    private List<Ciudad> ciudades;
    private Pais pais;
    private Provincia provincia;
    private Ciudad ciudad;
    private String nombre;
    private int idPais;
    private int idProvincia;
    
    @EJB
    com.jodasoft.sistfact.gco.dao.CiudadFacade ciudadFacade;
    @EJB
    com.jodasoft.sistfact.gco.dao.ProvinciaFacade provinciaFacade;    
    @EJB
    com.jodasoft.sistfact.gco.dao.PaisFacade paisFacade;
    
    
    
    
    public CiudadControler() {
    }
    
    public void onPaisChange(){
        if(idPais!=0){
            pais= paisFacade.find(idPais);
            provincias= pais.getProvinciaList();
            idProvincia=0;
        }else{
            provincias=null;
            idProvincia=0;
        }
    }
    
    public void limiarTextos(){
        nombre="";
        ciudad=new Ciudad();
        provincia=null;
        pais=null;
        provincias=null;
        idPais=0;
        idProvincia=0;
        
    }
    
    public void saveCiudad(){
        ciudad = new Ciudad();
        ciudad.setCiudNombre(nombre);
        provincia = provinciaFacade.find(idProvincia);
        ciudad.setProvId(provincia);
        try {
            ciudadFacade.saveCiudad(ciudad);
            closeDialog();
            displayInfoMessageToUser("Ciudad creada correctamente");
            limiarTextos();
        } catch (CiudadValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public List<Pais> getPaises() {
        if(paises==null)
            paises=paisFacade.findAll();
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Provincia> getProvincias() {
        if(provincias==null)
            provincias= new ArrayList<Provincia>();
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Ciudad> getCiudades() {
        if(ciudades==null)
            ciudades = new ArrayList<Ciudad>();
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
    
    
    
}
