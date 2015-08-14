/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.TipoArticulo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author johnny
 */
@ManagedBean(name = "seleccionProductosController")
@ViewScoped
public class SeleccionProductosController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of SeleccionProductosController
     */
    private int nivel = 0;
    private List<TipoArticulo> tipos;
    private TipoArticulo padre;
    private boolean hoja;
    private List<Articulo> articulos;
    private double cantidad;
    private List<DetalleFactura> detalles;
    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoArticuloFacade tipoArticuloFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;

    public SeleccionProductosController() {
        nivel = 0;
        padre = null;
        hoja = false;
    }

    public void ingresaTipo(TipoArticulo tipo) {

        padre = tipo;
        nivel++;
        if (!tipo.getTiarHoja()) {
            tipos = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
        } else {
            if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDiferenciarPrecios()) {
                articulos = articuloFacade.listar(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), FacturaController.getInstance().getCliente().getTiclId(), tipo);
            } else {
                articulos = articuloFacade.findByTiarIdAndArtiEstado(tipo, true);
            }
            detalles = new ArrayList<DetalleFactura>();
            for (Articulo articulo : articulos) {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setArtiId(articulo);
                detalle.setDefaCantidad(1d);
                detalle.setDefaPrecioVenta(articulo.getArtiPrecioVenta());
                detalles.add(detalle);
            }
            hoja = true;
        }
    }

    public void regresar() {
        if (padre == null || padre.getTiarPadre() == null) {
            if (nivel > 0) {
                nivel--;
            }
            tipos = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            padre = null;
        } else {
            padre = tipoArticuloFacade.find(padre.getTiarPadre());
            tipos = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
            nivel--;
        }
        if (hoja) {
            hoja = false;
        }
    }

    public void agregar(DetalleFactura articulo) {
        FacturaController.getInstance().setArticulo(articulo.getArtiId());
        FacturaController.getInstance().setCantidad(articulo.getDefaCantidad());
        FacturaController.getInstance().setPrecio(articulo.getDefaPrecioVenta());
        FacturaController.getInstance().agregarItem();
        displayInfoMessageToUser("Artículo Agregado: " + articulo.getArtiId().getArtiDescripcion());
        articulo.setDefaCantidad(1d);
    }
    
    public void addCantidad(DetalleFactura articulo)
    {
        articulo.setDefaCantidad(articulo.getDefaCantidad()+0.5d);
    }
    
    public void minCantidad(DetalleFactura articulo)
    {
        if(articulo.getDefaCantidad()>=1)
            articulo.setDefaCantidad(articulo.getDefaCantidad()-0.5d);
    }
    
    public void addPrecioVenta(DetalleFactura articulo){
        articulo.setDefaPrecioVenta(articulo.getDefaPrecioVenta()+0.01d);
    }
    public void minPrecioVenta(DetalleFactura articulo){
        if(articulo.getDefaPrecioVenta()>0)
            articulo.setDefaPrecioVenta(articulo.getDefaPrecioVenta()-0.01d);
    }

    public int getAgregados() {
        return FacturaController.getInstance().getDetalle().size();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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

    public String getTitulo() {
        if (nivel == 0) {
            return "Tipos principales de Ítems";
        } else {
            return "Tipos de " + padre.getTiarNombre();
        }
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

    public boolean getHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public List<DetalleFactura> getDetalles() {
        if (detalles == null) {
            detalles = new ArrayList<DetalleFactura>();
        }
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

}
