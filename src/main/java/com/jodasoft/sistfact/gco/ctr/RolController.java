/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.util.exp.RolValidadorException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
@Named(value = "rolController")
@SessionScoped
public class RolController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of RolController
     */
    private String nombre;
    private Rol rol;
    private List<Rol> roles;
    private Permiso permiso;
    
    
     @EJB
    private com.jodasoft.sistfact.gco.dao.RolFacade rolFacade;
    
    public RolController() {
    }
    
    public void onRowSelect(SelectEvent event) {
        setNombre(rol.getRolNombre());
    }
    
    public void save(){
        rol= new Rol();
        rol.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        rol.setRolEstado(true);
        rol.setRolNombre(nombre);
        try {
            rolFacade.save(rol);
            displayInfoMessageToUser("Rol creado correctamente");
            roles.add(rol);
            rol = new Rol();
            setNombre("");
        } catch (RolValidadorException ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    public void updateRol(){
        rol.setRolNombre(nombre);
        try {
            rolFacade.update(rol);
            displayInfoMessageToUser("Rol modificado correctamente");
            rol = new Rol();
            setNombre("");
        } catch (RolValidadorException ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
        
    }
    
    public void deleteRol(){
        rolFacade.delete(rol);
        displayInfoMessageToUser("Rol eliminado correctamente");
        roles.remove(rol);
    }
    
    //////////////////////////////gets y sets//////////////////////////////

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        if(roles==null)
            roles = rolFacade.findByAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Permiso getPermiso() {
        if(permiso==null)
            permiso=LoginController.getInstance().getPermiso("Roles");
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
    
    
    
}
