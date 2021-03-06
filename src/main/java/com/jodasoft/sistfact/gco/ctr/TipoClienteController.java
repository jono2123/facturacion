/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import com.jodasoft.sistfact.gco.util.exp.TipoClienteValidadorException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author johnny
 */
@ManagedBean(name = "tipoClienteController")
@ViewScoped
public class TipoClienteController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of TipoClienteController
     */
    private List<TipoCliente> tipos;
    private TipoCliente tipo;
    private String descripcion;
    private Permiso permiso;
    private int activeIndex;

    @EJB
    com.jodasoft.sistfact.gco.dao.TipoClienteFacade tipoClienteFacade;

    public TipoClienteController() {
        activeIndex = 0;
    }

    public void vaciaTextos() {
        tipo = new TipoCliente();
        setDescripcion("");
        activeIndex = 1;

    }

    public void saveTipo() {
        tipo = new TipoCliente();
        tipo.setTiclDescripcion(descripcion);
        tipo.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        tipo.setTiclEstado(true);
        try {
            tipoClienteFacade.save(tipo);
            closeDialog();
            displayInfoMessageToUser("Tipo de cliente creado correctamente");
            tipos.add(tipo);
            vaciaTextos();
        } catch (TipoClienteValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public void updateTipo() {
        tipo.setTiclDescripcion(descripcion);
        try {
            tipoClienteFacade.update(tipo);
            closeDialog();
            displayInfoMessageToUser("Tipo de cliente modificado correctamente");
            vaciaTextos();
        } catch (TipoClienteValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }

    }

    public void deleteTipo(TipoCliente tipo) {
        this.tipo = tipo;
        tipoClienteFacade.delete(tipo);
        tipos.remove(tipo);
        displayInfoMessageToUser("Tipo de cliente eliminado correctamente");
    }

    public void onRowSelect(SelectEvent event) {
        setDescripcion(tipo.getTiclDescripcion());
    }

    public List<TipoCliente> getTipos() {
        if (tipos == null) {
            tipos = tipoClienteFacade.findByAlmaIdAndEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        }
        return tipos;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void setTipos(List<TipoCliente> tipos) {
        this.tipos = tipos;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public Permiso getPermiso() {
        if (permiso == null) {
            permiso = LoginController.getInstance().getPermiso("Tipo Cliente");
        }
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public void nuevoTipoCliente() {
        activeIndex = 1;
        vaciaTextos();
    }

    public void editArticulo(TipoCliente tipocliente) {
        activeIndex = 1;

        this.tipo = tipocliente;
        setDescripcion(tipo.getTiclDescripcion());

    }

    public void guardar() {
        if (tipo == null) {
            tipo = new TipoCliente();
        }
        if (tipo.getTiclId() == null) {
            if (permiso.getPermCrear()) {
                saveTipo();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        } else {
            if (permiso.getPermModificar()) {
                updateTipo();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        }
        activeIndex = 0;
    }
}
