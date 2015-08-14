/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.mdl.Modulo;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.mdl.Ventana;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author johnny
 */
@Named(value = "permisosController")
//@SessionScoped
//@ManagedBean(name = "permisosController")
@SessionScoped
public class PermisosController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of PermisosController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.RolFacade rolFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenModuloFacade almoFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ModuloFacade moduloFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.VentanaFacade ventanaFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.PermisoFacade permisoFacade;

    private Rol rol;
    private List<Rol> roles;
    private List<AlmacenModulo> modulos;
    private List<Ventana> ventanas;
    private Permiso permiso;
    private List<Permiso> permisos;
    private Modulo modulo;
    private Ventana ventana;
    private int idMod;
    private int idRol;
    private int idVent;
    private boolean crear;
    private boolean modificar;
    private boolean eliminar;
    private boolean consultar;
    private int activeIndex;

    public PermisosController() {
        this.activeIndex = 0;

    }
///////////////////////////////agregar permiso////////////////////////////////

    public void agregar() {
        ventana = ventanaFacade.find(idVent);
        if (this.rol == null || this.rol.getRolId() == null) {
            displayErrorMessageToUser("Por favor seleccione un rol");
            return;
        }
        if (ventana == null) {
            displayErrorMessageToUser("Por favor seleccione un proceso");
            return;
        }
        permiso = new Permiso();
        for (Permiso permi : permisos) {
            if (permi.getVentId().getVentId().intValue() == ventana.getVentId().intValue()) {
                displayErrorMessageToUser("Ya se han agregado permisos sobre esta ventana");
                return;
            }
        }
        permiso.setPermConsultar(consultar);
        permiso.setPermCrear(crear);
        permiso.setPermEliminar(eliminar);
        permiso.setPermModificar(modificar);
        permiso.setRolId(rol);
        permiso.setVentId(ventana);
        permisoFacade.create(permiso);
        permisos.add(permiso);
        limpiarTodo();
    }

   
     public void agregar2() {
        ventana = ventanaFacade.find(idVent);
        if (this.rol == null || this.rol.getRolId() == null) {
            displayErrorMessageToUser("Por favor seleccione un rol");
            return;
        }
        if (ventana == null) {
            displayErrorMessageToUser("Por favor seleccione un proceso");
            return;
        }
        permiso = new Permiso();
        for (Permiso permi : permisos) {
            if (permi.getVentId().getVentId().intValue() == ventana.getVentId().intValue()) {
                displayErrorMessageToUser("Ya se han agregado permisos sobre esta ventana");
                return;
            }
        }
        permiso.setPermConsultar(consultar);
        permiso.setPermCrear(crear);
        permiso.setPermEliminar(eliminar);
        permiso.setPermModificar(modificar);
        permiso.setRolId(rol);
        permiso.setVentId(ventana);
        permisoFacade.create(permiso);
        permisos.add(permiso);
        limpiarTodo();
    }
public void limpiarTodo() {
        idMod = 0;
        modulo = new Modulo();
        idVent = 0;
        ventanas = new ArrayList<Ventana>();
        crear = false;
        consultar = false;
        eliminar = false;
        modificar = false;
        activeIndex=1;
        idRol=0;
    }

    public void borrarPermiso(Permiso perm) {
        permiso = perm;
        permisos.remove(permiso);
        permisoFacade.remove(permiso);

    }

    public void onRowSelect(SelectEvent event) {
        permisos = permisoFacade.findByRol(rol);

    }

    public Rol getRol() {
        if (rol == null) {
            rol = new Rol();
        }
        return rol;
    }

    public void cambiaPermiso(Permiso perm, int numPerm) {
        permiso = perm;
        switch (numPerm) {
            case 1:
                if (permiso.getPermCrear()) {
                    permiso.setPermCrear(false);
                } else {
                    permiso.setPermCrear(true);
                }
                break;
            case 2:
                if (permiso.getPermModificar()) {
                    permiso.setPermModificar(false);
                } else {
                    permiso.setPermModificar(true);
                }
                break;
            case 3:
                if (permiso.getPermEliminar()) {
                    permiso.setPermEliminar(false);
                } else {
                    permiso.setPermEliminar(true);
                }
                break;
            case 4:
                if (permiso.getPermConsultar()) {
                    permiso.setPermConsultar(false);
                } else {
                    permiso.setPermConsultar(true);
                }
                break;
        }
        permisoFacade.edit(permiso);
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        if (roles == null) {
            roles = rolFacade.findByAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<AlmacenModulo> getModulos() {
        if (modulos == null) {
            modulos = almoFacade.findByAlmaID(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return modulos;
    }

    public void setModulos(List<AlmacenModulo> modulos) {
        this.modulos = modulos;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Ventana getVentana() {
        return ventana;
    }

    public void setVentana(Ventana ventana) {
        this.ventana = ventana;
    }

    public int getIdMod() {
        return idMod;
    }

    public void setIdMod(int idMod) {
        this.idMod = idMod;
    }

    public int getIdVent() {
        return idVent;
    }

    public void setIdVent(int idVent) {
        this.idVent = idVent;
    }

    public List<Ventana> getVentanas() {
        if (ventanas == null) {
            ventanas = new ArrayList<Ventana>();
        }
        return ventanas;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void setVentanas(List<Ventana> ventanas) {
        this.ventanas = ventanas;
    }

    public void PresetGroupChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int aux = (int) event.getNewValue();
            for (Rol rolAux : roles) {
                if (rolAux.getRolId() == aux) {
                    this.rol = rolAux;
                    break;
                }
            }

            permisos = permisoFacade.findByRol(rol);

        }

    }

    public void onModuloChange() {
        if (idMod != 0) {
            modulo = moduloFacade.find(idMod);
            if (modulo != null) {
                ventanas = ventanaFacade.findByModuId(modulo);
            }
        } else {
            ventanas = new ArrayList<Ventana>();
        }
    }

    public void onRolChange() {
        for (Rol Aux : roles) {
            if (Aux.getRolId() == idRol) {
                this.rol = Aux;
                break;
            }
        }
        permisos = permisoFacade.findByRol(rol);

    }

    public boolean getCrear() {
        return crear;
    }

    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public boolean getConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public List<Permiso> getPermisos() {
        if (permisos == null) {
            permisos = new ArrayList<Permiso>();
        }
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public void nuevoPersmiso() {
        activeIndex = 1;
        limpiarTodo();
    }

    public void editPermiso(Permiso permiso) {
        this.permiso = permiso;
        permisos = permisoFacade.findByRol(rol);

    }

}
