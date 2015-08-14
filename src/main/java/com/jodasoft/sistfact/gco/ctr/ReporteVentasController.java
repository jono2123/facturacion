/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.AlmacenTransaccion;
import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.mdl.ItemVendido;
import com.jodasoft.sistfact.gco.util.XmlManager;
import com.jodasoft.sistfact.gco.util.exp.AlmacenTransaccionValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author johnny
 */
@Named(value = "reporteVentasController")
@SessionScoped
//@ManagedBean(name = "reporteVentasController")
//@ViewScoped
public class ReporteVentasController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ReporteVentasController
     */
    private List<Factura> facturas;
    private List<Factura> facturasFiltradas;
    private List<ItemVendido> items;
    private List<ItemVendido> itemsFiltrados;
    private List<Factura> facturasCedula;
    private List<Factura> facturasCedulaFiltradas;
    private double total = 0;
    private double totalCedula = 0;
    
    private Date desde;
    private Date hasta;
    private Date desdeItem;
    private Date hastaItem;
    private String cedula;
    private int estadoFactura;
    private int estadoFacturaCliente;
    private int numero;
    private int estadoFacturaItem;
    @EJB
    private com.jodasoft.sistfact.gco.dao.FacturaFacade facturaFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenFacade almacenFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ReporteItemVendidoFacade reporteItemFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.ItemVendidoFacade ItemVendidoFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.AmacenTransaccionFacade transaccionFacade;

    public ReporteVentasController() {
    }

    public List<Factura> getFacturasCedula() {
        if(facturasCedula==null)
            facturasCedula= new ArrayList<Factura>();
        return facturasCedula;
    }

    public void setFacturasCedula(List<Factura> facturasCedula) {
        this.facturasCedula = facturasCedula;
    }

    public List<Factura> getFacturasCedulaFiltradas() {
        return facturasCedulaFiltradas;
    }

    public void setFacturasCedulaFiltradas(List<Factura> facturasCedulaFiltradas) {
        this.facturasCedulaFiltradas = facturasCedulaFiltradas;
    }

    public List<Factura> getFacturas() {
        if (facturas == null) {
            facturas = new ArrayList<Factura>();

        }
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Factura> getFacturasFiltradas() {
        return facturasFiltradas;
    }

    public void setFacturasFiltradas(List<Factura> facturasFiltradas) {
        this.facturasFiltradas = facturasFiltradas;
    }

    public double getTotal() {
        total = 0;
        for (Factura factura : getFacturas()) {
            total = total + factura.getFactTotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void conectarWs() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String functionCrear = "creaWebSocket('" + LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDireccionServidor() + "')";
        requestContext.execute(functionCrear);
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<ItemVendido> getItems() {
        if (items == null) {
            items = new ArrayList<ItemVendido>();
        }
        return items;
    }

    public void setItems(List<ItemVendido> items) {
        this.items = items;
    }

    public List<ItemVendido> getItemsFiltrados() {
        return itemsFiltrados;
    }

    public void setItemsFiltrados(List<ItemVendido> itemsFiltrados) {
        this.itemsFiltrados = itemsFiltrados;
    }

    public void buscarPorFechas() {
        facturas = facturaFacade.findByFactFechaRange(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), estadoFactura, desde, hasta);
        total = 0;
        for (Factura factura : getFacturas()) {
            total = total + factura.getFactTotal();
        }
    }

    public void buscarPorCedula() {
        facturasCedula = facturaFacade.findByPersCedulaAndAlmaIdandFactEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), estadoFacturaCliente, cedula);
        totalCedula = 0;
        for (Factura factura : getFacturasCedula()) {
            totalCedula = totalCedula + factura.getFactTotal();
        }
    }

    public double getTotalCedula() {
        return totalCedula;
    }

    public void setTotalCedula(double totalCedula) {
        this.totalCedula = totalCedula;
    }

    public void buscarItemsPorFecha() {
        items = ItemVendidoFacade.findByDateRangeAndAlmaId(desdeItem, hastaItem, LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId().intValue(), estadoFacturaItem);
    }

    public int getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(int estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public int getEstadoFacturaCliente() {
        return estadoFacturaCliente;
    }

    public void setEstadoFacturaCliente(int estadoFacturaCliente) {
        this.estadoFacturaCliente = estadoFacturaCliente;
    }

    public Date getDesdeItem() {
        return desdeItem;
    }

    public void setDesdeItem(Date desdeItem) {
        this.desdeItem = desdeItem;
    }

    public Date getHastaItem() {
        return hastaItem;
    }

    public void setHastaItem(Date hastaItem) {
        this.hastaItem = hastaItem;
    }

    public int getEstadoFacturaItem() {
        return estadoFacturaItem;
    }

    public void setEstadoFacturaItem(int estadoFacturaItem) {
        this.estadoFacturaItem = estadoFacturaItem;
    }

    public void imprimirFactura(Factura factura) {
        if (factura.getFactEstado() != 1) {
            factura.setFactEstado(1);
            if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaFacturaAutonumerada()) {
                numero = almacenFacade.find(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId()).getAlmaNumFactura().intValue();
                factura.setFactNumero(numero);
                LoginController.getInstance().getUsuario().getRolId().getAlmaId().setAlmaNumFactura(numero + 1);
                almacenFacade.edit(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            }
            try {
                facturaFacade.edit(factura);
                facturas.remove(factura);
                String xml = new XmlManager().createFactura(factura);
                for (int i = 0; i < LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaNumCopias(); i++) {
                    print(xml);
                }
                guardaTransaccion(factura);
            } catch (Exception ex) {
                keepDialogOpen();
                displayErrorMessageToUser(ex.getMessage());
            }
        } else {
            String xml = new XmlManager().createFactura(factura);
            for (int i = 0; i < LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaNumCopias(); i++) {
                print(xml);
            }
        }

    }

    public void guardaTransaccion(Factura factura) throws AlmacenTransaccionValidadorException {
        AlmacenTransaccion transaccion = new AlmacenTransaccion();
        transaccion.setTransConcepto("FACTURA");
        transaccion.setTransFecha(new Date());
        transaccion.setTransNumDocumento(factura.getFactNumero());
        transaccion.setUsuaId(factura.getUsuaId());
        transaccionFacade.save(transaccion);
    }

    public void print(String xml) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        String mensaje = xml.substring(xml.indexOf("<f"), xml.lastIndexOf(">") + 1);

        String funcion = "enviaMensaje('" + mensaje + "')";
        System.out.println(funcion);
        requestContext.execute(funcion);
    }

    public String verFactura(Factura factura) {
        FacturaController.getInstance().setFactura(factura);
        return "FacturaUI.xhtml?faces-redirect=true";
    }

    public String abrirFactura(Factura factura) {
        VerFacturaController.getInstance().setFactura(factura);
        return "VerFacturaUI.xhtml?faces-redirect=true";
    }

    public void actualizaItems() {
        items = null;
        itemsFiltrados = null;
    }

}
