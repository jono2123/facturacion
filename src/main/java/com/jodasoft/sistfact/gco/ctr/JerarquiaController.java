/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.TipoArticulo;
import com.jodasoft.sistfact.gco.util.exp.TipoArticuloValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author johnny
 */
@ManagedBean(name = "jerarquiaController")
@ViewScoped
public class JerarquiaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of JerarquiaController
     */
    private int nivel = 0;
    private String nombreTipo;
    private List<TipoArticulo> tipos;
    private TipoArticulo padre;

    public JerarquiaController() {
        nivel = 0;
        padre=null;
    }

    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoArticuloFacade tipoArticuloFacade;

    public void crearTipo() {
        TipoArticulo tipo = new TipoArticulo();
        tipo.setTiarNombre(nombreTipo);
        if (nivel == 0 || padre==null) {
            tipo.setTiarPadre(null);
        } else {
            tipo.setTiarPadre(padre.getTiarId());
        }
        tipo.setTiarNivel(nivel);
        tipo.setTiarEstado(true);
        tipo.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        tipo.setTiarHoja(true);
        try {
            tipoArticuloFacade.save(tipo,padre);
            tipos.add(tipo);
            displayInfoMessageToUser("Tipo creado correctamente");
        } catch (TipoArticuloValidadorException ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    public void ingresaTipo(TipoArticulo tipo)
    {
        padre=tipo;
        nivel++;
        tipos=tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo.toUpperCase(Locale.FRENCH);
    }

    public List<TipoArticulo> getTipos() {
        if (tipos == null) {
            tipos = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return tipos;
    }

    public void setTipos(List<TipoArticulo> tipos) {
        this.tipos = tipos;
    }

    public TipoArticulo getPadre() {
        return padre;
    }

    public void setPadre(TipoArticulo padre) {
        this.padre = padre;
    }
    public String getTitulo(){
        if(nivel==0){
            return "Tipos principales de Ítems";
        }else{
            return "Tipos de "+padre.getTiarNombre();
        }
    }

}
