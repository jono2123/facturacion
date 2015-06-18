/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.UnidadDeMedida;
import com.jodasoft.sistfact.gco.util.exp.UnidadDeMedidaValidadorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
@Named(value = "unidadDeMedidaController")
@RequestScoped
public class UnidadDeMedidaController extends AbstractMB {

    /**
     * Creates a new instance of UnidadDeMedidaController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.UnidadDeMedidaFacade umedFacade;
    private String nombre;
    private List<UnidadDeMedida> unidades;
    private UnidadDeMedida unidad;

    public UnidadDeMedidaController() {
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

    public void deleteUmed() {
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

}
