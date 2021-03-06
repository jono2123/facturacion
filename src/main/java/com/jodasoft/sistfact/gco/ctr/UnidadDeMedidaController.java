/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.UnidadDeMedida;
import com.jodasoft.sistfact.gco.util.exp.UnidadDeMedidaValidadorException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
//@Named(value = "unidadDeMedidaController")
//@SessionScoped
@ManagedBean(name = "unidadDeMedidaController")
@ViewScoped
public class UnidadDeMedidaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of UnidadDeMedidaController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.UnidadDeMedidaFacade umedFacade;
    private String nombre;
    private List<UnidadDeMedida> unidades;
    private UnidadDeMedida unidad;
    private Permiso permiso;
    private int activeIndex;

    public UnidadDeMedidaController() {
        activeIndex = 0;
    }

    //////////////////////////funciones//////////////////////////
    public void reiniciaUmed() {
        unidad = new UnidadDeMedida();
    }

    public void vaciaTextos() {
        setNombre("");
    }

    public void onRowSelect(SelectEvent event) {
        setNombre(unidad.getUmedNombre());
    }

    public void saveUmed() {
        reiniciaUmed();
        unidad.setUmedNombre(nombre);
        unidad.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId());
        unidad.setUmedEstado(true);
        try {
            umedFacade.save(unidad);
            unidades.add(unidad);
            closeDialog();
            displayInfoMessageToUser("Unidad de Medida guardada exitosamente");
            reiniciaUmed();
            vaciaTextos();
        } catch (UnidadDeMedidaValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }

    }

    public void updateUmed() {
        unidad.setUmedNombre(nombre);
        unidad.setUmedEstado(true);
        try {
            umedFacade.update(unidad);
            closeDialog();
            displayInfoMessageToUser("Unidad de Medida modificada exitosamente");
            reiniciaUmed();
            vaciaTextos();
        } catch (UnidadDeMedidaValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public void deleteUmed(UnidadDeMedida unidadMedida) {
        this.unidad = unidadMedida;
        umedFacade.delete(unidad);
        unidades.remove(unidad);
        closeDialog();
        displayInfoMessageToUser("Unidad de Medida eliminada exitosamente");
        reiniciaUmed();
        vaciaTextos();
    }

    ////////////////////////gets y sets///////////////////////////
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public List<UnidadDeMedida> getUnidades() {
        if (unidades == null) {
            unidades = umedFacade.listar(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId());
        }
        return unidades;
    }

    public void setUnidades(List<UnidadDeMedida> unidades) {
        this.unidades = unidades;
    }

    public UnidadDeMedida getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadDeMedida unidad) {
        this.unidad = unidad;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Permiso getPermiso() {
        if (permiso == null) {
            permiso = LoginController.getInstance().getPermiso("Unidades de Medida");
        }
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public void nuevoUnidadMedida() {
        activeIndex = 1;
        vaciaTextos();
    }

    public void editarUnidad(UnidadDeMedida unidadMedida) {
        this.unidad = unidadMedida;
        setNombre(unidad.getUmedNombre());
        activeIndex = 1;

    }

    public void guardar() {
        permiso = LoginController.getInstance().getPermiso("Unidades de Medida");

        if (unidad == null) {
            unidad = new UnidadDeMedida();
        }
        if (unidad.getUmedId() == null) {

            if (permiso.getPermCrear()) {
                saveUmed();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        } else {
            if (permiso.getPermModificar()) {
                updateUmed();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        }
        activeIndex = 0;
    }

}
