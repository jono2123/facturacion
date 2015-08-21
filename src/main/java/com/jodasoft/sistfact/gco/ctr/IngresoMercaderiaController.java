/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Cardex;
import com.jodasoft.sistfact.gco.mdl.ConfiguracionInventario;
import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.IngresoMercaderia;
import com.jodasoft.sistfact.gco.mdl.IngresoMercaderiaDetalle;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.util.exp.IngresoMercaderiaValidadorException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author johnny
 */
@Named(value = "ingresoMercaderiaController")
@SessionScoped
public class IngresoMercaderiaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of IngresoMercaderiaController
     */
    private String codigo;
    private double cantidad;
    private double costo;
    private String descripcion;
    private Articulo articulo;
    private List<IngresoMercaderiaDetalle> detalle;
    private IngresoMercaderia ingreso;
    private Date fechaIngreso;
    private String concepto;
    private int i;
    private ConfiguracionInventario configuracion;

    //totales de la factura
    private double subtTotal;
    private double subTotalSinIva;
    private double subTotalConIva;
    private double iva;
    private double total;
    
    private Permiso permiso;

    @EJB
    com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;

    @EJB
    com.jodasoft.sistfact.gco.dao.ConfiguracionInventarioFacade configuracionInventarioFacade;

    @EJB
    com.jodasoft.sistfact.gco.dao.IngresoMercaderiaFacade ingresoMercaderiaFacade;
    
    @EJB
    com.jodasoft.sistfact.gco.dao.CardexFacade cardexFacade;
    
    public IngresoMercaderiaController() {
        fechaIngreso = new Date();
        i = 0;
    }

    public void buscaArticuloPorCodigo() {
        articulo = articuloFacade.findByCodigoAndAlmacen(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), codigo);

        if (articulo == null) {
            articulo = new Articulo();
            displayErrorMessageToUser("No se encontró ningún artículo con ese código");
            setCosto(0);
            setCantidad(0);
            setDescripcion("");
        } else {
            setDescripcion(articulo.getArtiDescripcion());
            setCosto(articulo.getArtiPrecioCompra());
            setCantidad(1);
        }
    }

    public void agregarItem() {
        if (articulo == null) {
            displayErrorMessageToUser("No se ha seleccionado ningún producto para agregar");
            return;
        }
        if (cantidad <= 0) {
            displayErrorMessageToUser("Ingrese una cantidad");
            return;
        }
        if (costo < 0) {
            displayErrorMessageToUser("El precio no puede ser un valor negativo");
            return;
        }
        if (getConfiguracion().getCoinDesglozarIvaCosto()&& articulo.getArtiIva()) {
            costo = costo / (1 + LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaIva() / 100d);
        }
        
        IngresoMercaderiaDetalle item = new IngresoMercaderiaDetalle();
        if(articulo.getArtiIva())
        {
            item.setImdeIva(redondear(costo*LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaIva()/100d));
        }else
        {
            item.setImdeIva(0d);
        }
        item.setArtiId(articulo);
        item.setImdeCantidad(cantidad);
        item.setImdeCostoUnitario(redondear(costo));
        item.setLoteCodigo(obtenCodLote(item));
        item.setNumItem(i);
        int pos = existe(item);
        if (pos == -1) {
            detalle.add(item);
            i++;
        } else {
            detalle.get(pos).setImdeCantidad(detalle.get(pos).getImdeCantidad() + item.getImdeCantidad());
        }
        calcular();
        articulo = null;
        setCodigo("");
        setCantidad(0);
        setCosto(0);
        setDescripcion("");
    }

    public void onCellEdit(CellEditEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        calcular();
        requestContext.execute("document.getElementById('frmPrincipal:cmdActualizar').click();");
    }

    public void calcular() {
        subtTotal = 0;
        subTotalConIva = 0;
        subTotalSinIva = 0;

        iva = 0;
        total = 0;
        for (IngresoMercaderiaDetalle deta : detalle) {
            subtTotal += deta.getImdeCantidad() * deta.getImdeCostoUnitario();
            if (deta.getArtiId().getArtiIva()) {
                subTotalConIva = subTotalConIva + deta.getImdeCantidad() * deta.getImdeCostoUnitario();
            } else {
                subTotalSinIva = subTotalSinIva + deta.getImdeCantidad() * deta.getImdeCostoUnitario();
            }

        }

        iva = (subTotalConIva) * LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaIva() / 100d;

        total = subTotalConIva + subTotalSinIva + iva;
    }

    private int existe(IngresoMercaderiaDetalle item) {
        for (int i = 0; i < detalle.size(); i++) {
            if (item.getArtiId().getArtiId().equals(detalle.get(i).getArtiId().getArtiId()) && item.getImdeCostoUnitario().equals(detalle.get(i).getImdeCostoUnitario())) {
                return i;
            }
        }
        return -1;
    }
    private boolean existeParaCod(IngresoMercaderiaDetalle item) {
        for (int i = 0; i < detalle.size(); i++) {
            if (item.getArtiId().getArtiId().equals(detalle.get(i).getArtiId().getArtiId())) {
                return true;
            }
        }
        return false;
    }

    public String obtenCodLote(IngresoMercaderiaDetalle item) {
            Date d;
        if(existeParaCod(item)){
            d= new Date();
        }
        else{
            d=fechaIngreso;
        }
        String codLote = "";
        String dia = String.valueOf(d.getDate());
        String mes = String.valueOf(d.getMonth());
        String anio = Integer.toHexString(d.getYear());
        int tiempo=d.getHours()*1000+d.getMinutes()*100+d.getSeconds();
        String hora = Integer.toString(tiempo, 30);
        codLote = dia + mes + anio+hora.toUpperCase();
        return codLote;
    }

    public void quitar(IngresoMercaderiaDetalle item) {
        for (int i = 0; i < detalle.size(); i++) {
            if (detalle.get(i).getArtiId().equals(item.getArtiId())) {
                detalle.remove(i);
            }
        }
        calcular();
    }

    public void guardarIngreso(){
        ingreso = new IngresoMercaderia();
        ingreso.setRegConcepto(concepto);
        ingreso.setRegEstado(true);
        ingreso.setRegFecha(fechaIngreso);
        ingreso.setRegIva(iva);
        ingreso.setRegSubtotalSinIva(subTotalSinIva);
        ingreso.setRegSubutotal(subtTotal);
        ingreso.setRegTotal(total);
        ingreso.setUsuaId(LoginController.getInstance().getUsuario());
        for(IngresoMercaderiaDetalle deta : detalle){
            deta.setRegId(ingreso);
        } 
        ingreso.setIngresoMercaderiaDetalleList(detalle);
        try {
            ingresoMercaderiaFacade.save(ingreso);
            guardarKardex(ingreso);
            closeDialog();
            displayInfoMessageToUser("Ingreso guardado correctamente");
            limpiaTodo();
        } catch (IngresoMercaderiaValidadorException ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
        
        
    }
    
    public void guardarKardex(IngresoMercaderia ingreso){
        for(IngresoMercaderiaDetalle deta : ingreso.getIngresoMercaderiaDetalleList())
        {
            Cardex kardex= new Cardex();
            kardex.setCardCantidad(deta.getImdeCantidad());
            kardex.setCardFecha(ingreso.getRegFecha());
            kardex.setCardOperacion("ENTRADA");
            kardex.setCardTipoDocumento("INGRESO");
            kardex.setLoteId(deta.getLoteId());
            kardex.setNumeroDocumento(ingreso.getRegId());
            cardexFacade.createCardex(kardex);
        }
    }
    
    
    public void limpiaTodo(){
        setCodigo("");
        setCantidad(0);
        setCosto(0);
        ingreso = new IngresoMercaderia();
        detalle = new ArrayList<IngresoMercaderiaDetalle>();
        iva=0;
        subTotalConIva=0;
        subTotalSinIva=0;
        total=0;
        articulo=null;
        descripcion="";
        fechaIngreso= new Date();
        i=0;
        subtTotal=0;
        
    }
    
    //////////////////////////////////getters and setters/////////////////////////////
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo.toUpperCase();
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<IngresoMercaderiaDetalle> getDetalle() {
        if (detalle == null) {
            detalle = new ArrayList<IngresoMercaderiaDetalle>();
        }
        return detalle;
    }

    public void setDetalle(List<IngresoMercaderiaDetalle> detalle) {
        this.detalle = detalle;
    }

    public IngresoMercaderia getIngreso() {
        return ingreso;
    }

    public void setIngreso(IngresoMercaderia ingreso) {
        this.ingreso = ingreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public ConfiguracionInventario getConfiguracion() {
        if (configuracion == null) {
            configuracion = configuracionInventarioFacade.findByAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId());
        }
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionInventario configuracion) {
        this.configuracion = configuracion;
    }

    public double getSubtTotal() {
        return subtTotal;
    }

    public void setSubtTotal(double subtTotal) {
        this.subtTotal = subtTotal;
    }

    public double getSubTotalSinIva() {
        return subTotalSinIva;
    }

    public void setSubTotalSinIva(double subTotalSinIva) {
        this.subTotalSinIva = subTotalSinIva;
    }

    public double getSubTotalConIva() {
        return subTotalConIva;
    }

    public void setSubTotalConIva(double subTotalConIva) {
        this.subTotalConIva = subTotalConIva;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Permiso getPermiso() {
        permiso = LoginController.getInstance().getPermiso("Ingreso de Mercadería");
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
