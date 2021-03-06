/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author javila
 */
//@Named(value = "listaArticulosController")
//@SessionScoped
@ManagedBean(name = "listaArticulosController")
@ViewScoped
public class ListaArticulosController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ListaArticulosController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;

    List<Articulo> articulos;
    List<Articulo> articulosFiltrados;

    private Articulo articulo;
    private TipoCliente tipo;

    public ListaArticulosController() {
    }

    public void cargarArticulos() {
        if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDiferenciarPrecios()) {
            articulos = articuloFacade.listar(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), tipo);
        } else {
            articulos = articuloFacade.listar(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        }
    }

    public static ListaArticulosController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{listaArticulosController}", ListaArticulosController.class);
        return (ListaArticulosController) ex.getValue(context);
    }

    public List<Articulo> getArticulos() {
        if (articulos == null) {
            articulos = new ArrayList<Articulo>();
        }

        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Articulo> getArticulosFiltrados() {
        return articulosFiltrados;
    }

    public void setArticulosFiltrados(List<Articulo> articulosFiltrados) {
        this.articulosFiltrados = articulosFiltrados;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public void reiniciaArticulos() {
        articulos = null;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

}
