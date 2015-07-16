/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.PrecioVenta;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import com.jodasoft.sistfact.gco.mdl.UnidadDeMedida;
import com.jodasoft.sistfact.gco.util.exp.ArticuloValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
//@Named(value = "articuloController")
//@SessionScoped
@ManagedBean(name = "articuloController")
@ViewScoped
public class ArticuloController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ArticuloController
     */
    private String codigo;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private boolean iva;
    private int umedida_id;
    private String infoAdicional;
    private List<UnidadDeMedida> unidades;
    private Articulo articulo;
    private Permiso permiso;
    private List<Articulo> articulos;
    private List<Articulo> articulosFiltrados;
    private List<TipoCliente> tipos;
    private List<PrecioVenta> precios;
    private List<PrecioVenta> preciosAnteriores;

    /////////////////////ejb del facade
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.UnidadDeMedidaFacade unidadDeMedidaFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoClienteFacade tipoClienteFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.PrecioVentaFacade precioVentaFacade;

    public ArticuloController() {
    }

    //////////////////////funciones//////////////////////////////////////////////////
    private void reiniciaArticulo() {
        articulo = new Articulo();
        precios = null;
        preciosAnteriores = null;
    }

    public void vaciarTextos() {
        setCodigo("");
        setDescripcion("");
        setPrecioCompra(0);
        setPrecioVenta(0);
        setUmedida_id(1);
        setIva(false);
        setInfoAdicional("");
        articulo = new Articulo();
        precios = null;
        preciosAnteriores = null;
    }

    public void onRowSelect(SelectEvent event) {
        preciosAnteriores = null;
        setCodigo(articulo.getArtiCodigo());
        setDescripcion(articulo.getArtiDescripcion());
        setPrecioCompra(articulo.getArtiPrecioCompra());
        setPrecioVenta(articulo.getArtiPrecioVenta());
        setUmedida_id(articulo.getUmedId().getUmedId());
        setIva(articulo.getArtiIva());
        setInfoAdicional(articulo.getArtiInfoAdicional());
        //setPrecios(articulo.getPrecioVentaList());
        if (getDiferenciado()) {
            precios = new ArrayList<PrecioVenta>();
            for (TipoCliente tipo : getTipos()) {
                PrecioVenta pVenta = new PrecioVenta();
                pVenta.setArtiId(articulo);
                pVenta.setTiclId(tipo);
                pVenta.setPrecio(0d);
                for (PrecioVenta precio : getPreciosAnteriores()) {
                    if (precio.getArtiId().equals(articulo) && precio.getTiclId().equals(tipo)) {
                        pVenta.setPrecio(precio.getPrecio());
                        break;
                    }
                }
                precios.add(pVenta);
            }
        }
    }

    public void saveArticulo() {
        try {
            articulo = new Articulo();
            Almacen almacen = LoginController.getInstance().getUsuario().getRolId().getAlmaId();

            articulo.setAlmaId(almacen);
            articulo.setArtEstado(true);
            articulo.setArtiCodigo(codigo);
            articulo.setArtiDescripcion(descripcion);
            articulo.setArtiInfoAdicional(infoAdicional);
            articulo.setArtiIva(iva);
            articulo.setArtiPrecioCompra(precioCompra);
            articulo.setArtiPrecioVenta(precioVenta);
            UnidadDeMedida umed = new UnidadDeMedida();
            umed.setUmedId(umedida_id);
            articulo.setUmedId(umed);
            if (getDiferenciado()) {

                for (PrecioVenta precio : precios) {
                    precio.setArtiId(articulo);
                }
            }
            if (getDiferenciado()) {
                articuloFacade.save(articulo, precios);
            } else {
                articuloFacade.save(articulo);
            }
            closeDialog();
            displayInfoMessageToUser("Artículo Guardado Correctamente");
            articulos.add(articulo);
            reiniciaArticulo();
            vaciarTextos();
        } catch (ArticuloValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        } catch (javax.ejb.EJBException ex) {
            keepDialogOpen();
            if (ex.getCausedByException().getCause().getLocalizedMessage().contains("llave duplicada viola restricción de unicidad")) {
                displayErrorMessageToUser("Ya existe un artículo con ese código");
            }
        }

    }

    public void updateArticulo() {
        try {
            articulo.setArtEstado(true);
            articulo.setArtiCodigo(codigo);
            articulo.setArtiDescripcion(descripcion);
            articulo.setArtiInfoAdicional(infoAdicional);
            articulo.setArtiIva(iva);
            articulo.setArtiPrecioCompra(precioCompra);
            articulo.setArtiPrecioVenta(precioVenta);
            UnidadDeMedida umed = new UnidadDeMedida();
            umed.setUmedId(umedida_id);
            articulo.setUmedId(umed);
            if (getDiferenciado()) {
                for (PrecioVenta precio : getPreciosAnteriores()) {
                    precioVentaFacade.deletePrecio(precio);
                }
                articuloFacade.update(articulo, precios);
            } else {
                articuloFacade.update(articulo);
            }
            closeDialog();
            displayInfoMessageToUser("Artículo Modificado Correctamente");
            reiniciaArticulo();
            vaciarTextos();
        } catch (ArticuloValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public void deleteArticulo() {
        articuloFacade.delete(articulo);
        articulos.remove(articulo);
        vaciarTextos();
        closeDialog();
        displayInfoMessageToUser("Artículo Eliminado Correctamente");
    }

    ////////////////////////// Gets y Sets //////////////////////////////////////////
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo.toUpperCase();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public boolean getIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public int getUmedida_id() {
        return umedida_id;
    }

    public void setUmedida_id(int umedida_id) {
        this.umedida_id = umedida_id;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional.toUpperCase();
    }

    public List<UnidadDeMedida> getUnidades() {
        if (unidades == null) {
            unidades = unidadDeMedidaFacade.listar(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId());
        }
        return unidades;
    }

    public void setUnidades(List<UnidadDeMedida> unidades) {
        this.unidades = unidades;
    }

    public List<Articulo> getArticulos() {
        if (articulos == null) {
            Almacen almacen = getAlmacen();
            articulos = articuloFacade.listar(almacen, true);
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

    private Almacen getAlmacen() {
        return LoginController.getInstance().getUsuario().getRolId().getAlmaId();

    }

    public Permiso getPermiso() {
        if (permiso == null) {
            permiso = LoginController.getInstance().getPermiso("Items");
        }
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public boolean getDiferenciado() {
        return LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDiferenciarPrecios();
    }

    public List<TipoCliente> getTipos() {
        if (tipos == null) {
            tipos = tipoClienteFacade.findByAlmaIdAndEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        }
        return tipos;
    }

    public void setTipos(List<TipoCliente> tipos) {
        this.tipos = tipos;
    }

    public List<PrecioVenta> getPrecios() {
        if (precios == null) {
            precios = new ArrayList<PrecioVenta>();
            for (TipoCliente tipo : getTipos()) {
                PrecioVenta precio = new PrecioVenta();
                precio.setTiclId(tipo);
                precio.setPrecio(0d);
                precios.add(precio);
            }
        }
        return precios;
    }

    public void setPrecios(List<PrecioVenta> precios) {
        this.precios = precios;
    }

    public List<PrecioVenta> getPreciosAnteriores() {
        if (preciosAnteriores == null) {
            preciosAnteriores = precioVentaFacade.findByArtiId(articulo);
        }
        return preciosAnteriores;
    }

    public void setPreciosAnteriores(List<PrecioVenta> preciosAnteriores) {
        this.preciosAnteriores = preciosAnteriores;
    }

}
