/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.PrecioVenta;
import com.jodasoft.sistfact.gco.mdl.TipoArticulo;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
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
    private int activeIndex;
    private int tipoArticulo;
    private List<SelectItem> tiposArticulo;
    private List<TipoArticulo> tiposArt;
    private int nivel;
    private TipoArticulo padre;
    private String ruta;

    /////////////////////ejb del facade
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.UnidadDeMedidaFacade unidadDeMedidaFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoClienteFacade tipoClienteFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.PrecioVentaFacade precioVentaFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoArticuloFacade tipoArticuloFacade;

    public ArticuloController() {
        this.activeIndex = 0;
        nivel = 0;
        padre = null;
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
        tipoArticulo=0;
        nivel=0;
        padre=null;
        tiposArt=null;
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
        if (articulo.getTiarId() != null) {
            tipoArticulo = articulo.getTiarId().getTiarId().intValue();
            nivel=articulo.getTiarId().getTiarNivel();
            if (articulo.getTiarId().getTiarPadre() != null) {
                padre = tipoArticuloFacade.find(articulo.getTiarId().getTiarPadre());
                tiposArt = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
                
            } else {
                tiposArt = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            }
        }
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

    public void armaArbol() {
        tiposArticulo = new ArrayList<SelectItem>();
        List<TipoArticulo> tiposPrincipales = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        for (TipoArticulo tipoPrincipal : tiposPrincipales) {
            if (!tipoPrincipal.getTiarHoja()) {
                SelectItemGroup grupo = new SelectItemGroup(tipoPrincipal.getTiarNombre());
                grupo.setSelectItems(buscaHijos(grupo, tipoPrincipal));
                tiposArticulo.add(grupo);
            } else {
                SelectItem tipo = new SelectItem(tipoPrincipal.getTiarId(), tipoPrincipal.getTiarNombre());
                tiposArticulo.add(tipo);
            }

        }
    }

    public SelectItem[] buscaHijos(SelectItemGroup grupoPadre, TipoArticulo padre) {
        List<TipoArticulo> listaHijos = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
        SelectItem[] vectorHijos = new SelectItem[listaHijos.size()];
        int i = 0;
        for (TipoArticulo tipoHijo : listaHijos) {
            if (!tipoHijo.getTiarHoja()) {
                SelectItemGroup grupo = new SelectItemGroup(tipoHijo.getTiarNombre());
                grupo.setSelectItems(buscaHijos(grupo, tipoHijo));
                vectorHijos[i] = grupo;
            } else {
                SelectItem tipo = new SelectItem(tipoHijo.getTiarId(), tipoHijo.getTiarNombre());
                vectorHijos[i] = tipo;
            }
            i++;
        }
        return vectorHijos;
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
            if (tipoArticulo != 0) {
                TipoArticulo tipo;
                tipo = tipoArticuloFacade.find(tipoArticulo);
                articulo.setTiarId(tipo);
            }

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

    public void editArticulo(Articulo articulo) {
        activeIndex = 1;

        this.articulo = articulo;
        preciosAnteriores = null;
        setCodigo(articulo.getArtiCodigo());
        setDescripcion(articulo.getArtiDescripcion());
        setPrecioCompra(articulo.getArtiPrecioCompra());
        setPrecioVenta(articulo.getArtiPrecioVenta());
        setUmedida_id(articulo.getUmedId().getUmedId());
        setIva(articulo.getArtiIva());
        setInfoAdicional(articulo.getArtiInfoAdicional());
        //setPrecios(articulo.getPrecioVentaList());
        if (articulo.getTiarId() != null) {
            tipoArticulo = articulo.getTiarId().getTiarId().intValue();
            if (articulo.getTiarId().getTiarPadre() != null) {
                padre = tipoArticuloFacade.find(articulo.getTiarId().getTiarPadre());
                tiposArt = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
            } else {
                tiposArt = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            }
        }
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

    public void updateArticulo() {
        try {
            activeIndex = 1;

            this.articulo.setArtEstado(true);
            setCodigo(articulo.getArtiCodigo());
            this.articulo.setArtiDescripcion(descripcion);
            this.articulo.setArtiInfoAdicional(infoAdicional);
            this.articulo.setArtiIva(iva);
            this.articulo.setArtiPrecioCompra(precioCompra);
            this.articulo.setArtiPrecioVenta(precioVenta);
            if (this.tipoArticulo != 0) {
                TipoArticulo tipo;
                tipo = tipoArticuloFacade.find(this.tipoArticulo);
                this.articulo.setTiarId(tipo);
            }

            UnidadDeMedida umed = new UnidadDeMedida();
            umed.setUmedId(umedida_id);
            this.articulo.setUmedId(umed);
            if (getDiferenciado()) {
                for (PrecioVenta precio : getPreciosAnteriores()) {
                    precioVentaFacade.deletePrecio(precio);
                }
                articuloFacade.update(this.articulo, precios);
            } else {
                articuloFacade.update(this.articulo);
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

    public void deleteArticulo(Articulo articulo) {
        this.articulo = articulo;
        articuloFacade.delete(this.articulo);
        articulos.remove(this.articulo);
        articulosFiltrados.remove(this.articulo);
        vaciarTextos();
        closeDialog();
        displayInfoMessageToUser("Artículo Eliminado Correctamente");
        //DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("tblArticulos");
        //table.reset();
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    ////////////////////////// Gets y Sets //////////////////////////////////////////
    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

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

    public int getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(int tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public List<TipoArticulo> getTiposArt() {
        if (tiposArt == null) {
            tiposArt = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return tiposArt;
    }

    public void setTiposArt(List<TipoArticulo> tiposArt) {
        this.tiposArt = tiposArt;
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

    public List<SelectItem> getTiposArticulo() {
        if (tiposArticulo == null) {
            armaArbol();
        }
        return tiposArticulo;
    }

    public void setTiposArticulo(List<SelectItem> tiposArticulo) {
        this.tiposArticulo = tiposArticulo;
    }

    public void nuevoArticulo() {
        activeIndex = 1;
        vaciarTextos();
    }

    public void guardar() {
        if (articulo == null) {
            articulo = new Articulo();
        }
        if (articulo.getArtiCodigo() == null) {
            if (permiso.getPermCrear()) {
                saveArticulo();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        } else {
            if (permiso.getPermModificar()) {
                updateArticulo();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        }
        activeIndex = 0;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTitulo() {
        if (nivel == 0) {
            return "Tipos principales de Ítems";
        } else {
            return "Tipos de " + padre.getTiarNombre();
        }
    }

    public void ingresaTipo(TipoArticulo tipo) {
        if (!tipo.getTiarHoja()) {
            padre = tipo;
            nivel++;
            tiposArt = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
        } else {
            tipoArticulo = tipo.getTiarId();
        }
    }

    public void regresar() {
        if (padre == null || padre.getTiarPadre() == null) {
            if (nivel > 0) {
                nivel--;
            }
            tiposArt = tipoArticuloFacade.findAlmaIdAndNivel0(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            padre = null;
        } else {
            padre = tipoArticuloFacade.find(padre.getTiarPadre());
            tiposArt = tipoArticuloFacade.findByTiarPadre(padre.getTiarId());
            nivel--;
        }
    }

    public String getRuta() {
        String ruta = "";
        if (tipoArticulo != 0) {
            TipoArticulo tipoArt = tipoArticuloFacade.find(tipoArticulo);
            if (tipoArt.getTiarPadre() != null) {
                List<String> padres = new ArrayList<String>();
                while (tipoArt.getTiarPadre() != null) {
                    tipoArt = tipoArticuloFacade.find(tipoArt.getTiarPadre());
                    padres.add(tipoArt.getTiarNombre());
                }
                for (int i = padres.size() - 1; i >= 0; i--) {
                    ruta = ruta + padres.get(i) + ">>";
                }

                return ruta;
            } else {
                return "Tipos principales de Artículos";
            }
        }
        return "";
    }
}
