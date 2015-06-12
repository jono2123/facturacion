/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.util.exp.FacturaValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author javila
 */
@Named(value = "facturaController")
@SessionScoped
public class FacturaController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of FacturaController
     */
    //encabezado de la factura
    @EJB
    private com.jodasoft.sistfact.gco.dao.ClienteFacade clienteFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;
    
    @EJB
    private com.jodasoft.sistfact.gco.dao.FacturaFacade facturaFacade;

    private int numero;
    private Cliente cliente;
    private Date fecha = new Date();
    private String guia;
    private String cedula;
    private String nombres;
    private String direccion;
    private String telefono;

    //variables para el producto
    private String codigo;
    private double cantidad;
    private double precio;
    private Articulo articulo;

    //variables para la factura
    private Factura factura;
    private List<DetalleFactura> detalle;
    private DetalleFactura detalleSelected;
    int i = 0;

    //totales de la factura
    private double subtTotal;
    private double subTotalSinIva;
    private double subTotalConIva;
    private double iva;
    private double descuento;
    private double tota;

    public FacturaController() {
        cliente = new Cliente();
    }

    public void selecciona() {
        cliente = ListaClientesController.getInstance().cliente;
        setCedula(cliente.getClieCedula());
        setNombres(cliente.getClieNombres() + " " + cliente.getClieApellidos());
        setDireccion(cliente.getClieDireccion());
        setTelefono(cliente.getClieTelefono());
        setFecha(new Date());
    }

    public void seleccionaArticulo() {
        articulo = ListaArticulosController.getInstance().getArticulo();
        setCodigo(articulo.getArtiCodigo());
        setPrecio(articulo.getArtiPrecioVenta());
        setCantidad(1);
    }

    public void limpiarTodo() {
        cliente = new Cliente();
        setCedula("");
        setNombres("");
        setDireccion("");
        setTelefono("");
        setFecha(new Date());
        setNumero(0);
        setCodigo("");
        setCantidad(0);
        setPrecio(0);
        setDetalle(new ArrayList<DetalleFactura>());
        setArticulo(null);
        setSubtTotal(0);
        setTota(0);
        setSubTotalConIva(0);
        setSubTotalSinIva(0);
        setIva(0);
        setDescuento(0);
        factura = new Factura();
        i=0;
    }

    public void buscarPorCedula() {
        cliente = clienteFacade.findClienteByCedula(cedula, LoginController.getInstance().getUsuario().getAlmaId());
        if (cliente == null) {
            cliente = new Cliente();
            displayErrorMessageToUser("No se encontró ningún cliente con esa cédula");
        } else {
            setNombres(cliente.getClieNombres() + " " + cliente.getClieApellidos());
            setDireccion(cliente.getClieDireccion());
            setTelefono(cliente.getClieTelefono());
            setFecha(new Date());
        }
    }

    public void buscaArticuloPorCodigo() {
        articulo = articuloFacade.findByCodigoAndAlmacen(LoginController.getInstance().getUsuario().getAlmaId(), codigo);
        if (articulo == null) {
            articulo = new Articulo();
            displayErrorMessageToUser("No se encontró ningún artículo con ese código");
            setPrecio(0);
        } else {
            setPrecio(articulo.getArtiPrecioVenta());
            setCantidad(1);
        }
    }

    ////////////////////////////agregar articulos a la factura////////////////////////////////////////////
    public void agregarItem() {
        if (articulo == null) {
            displayErrorMessageToUser("No se ha seleccionado ningún producto para agregar");
            return;
        }
        if (cantidad <= 0) {
            displayErrorMessageToUser("Ingrese una cantidad");
            return;
        }
        if (precio < 0) {
            displayErrorMessageToUser("El precio no puede ser un valor negativo");
            return;
        }
        DetalleFactura item = new DetalleFactura();
        item.setArtiId(articulo);
        item.setDefaCantidad(cantidad);
        item.setDefaPrecioVenta(precio/1.12d);
        item.setDefaObservaciones("");
        item.setNumItem(i);
        int pos = existe(item);
        if (pos == -1) {
            detalle.add(item);
            i++;
        } else {
            detalle.get(pos).setDefaCantidad(detalle.get(pos).getDefaCantidad() + item.getDefaCantidad());
        }
        calcular();
        articulo = null;
        setCodigo("");
        setCantidad(0);
        setPrecio(0);
    }

    public void calcular() {
        subtTotal = 0;
        subTotalConIva = 0;
        subTotalSinIva = 0;
        iva = 0;
        tota = 0;
        for (DetalleFactura deta : detalle) {
            subtTotal = deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            if (deta.getArtiId().getArtiIva()) {
                subTotalConIva = subTotalConIva + deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            } else {
                subTotalSinIva = subTotalSinIva + deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            }
            iva=subTotalConIva*0.12;
            tota=subTotalConIva+subTotalSinIva+iva;
        }
    }

    private int existe(DetalleFactura item) {
        for (int i = 0; i < detalle.size(); i++) {
            if (item.getArtiId().getArtiId().equals(detalle.get(i).getArtiId().getArtiId()) && item.getDefaPrecioVenta().equals(detalle.get(i).getDefaPrecioVenta()) && item.getDefaObservaciones().equals(detalle.get(i).getDefaObservaciones())) {
                return i;
            }
        }
        return -1;
    }
    
    public void guardarFactura(){
        factura = new Factura();
        factura.setFactNumero(numero);
        factura.setClieId(cliente);
        factura.setAlmaId(cliente.getAlmaId());
        factura.setFactDescuento(descuento);
        factura.setFactIva(iva);
        factura.setFactSubtotalConIva(subTotalConIva);
        factura.setFactSubtotalSinIva(subTotalSinIva);
        factura.setFactTotal(subtTotal);
        factura.setUsuaId(LoginController.getInstance().getUsuario());
        factura.setFactFecha(fecha);
        for(DetalleFactura deta:detalle)
        {
            deta.setFactId(factura);
        }
        factura.setDetalleFacturaList(detalle);
        try {
            facturaFacade.save(factura);
            displayInfoMessageToUser("Factura guardada correctamente");
            limpiarTodo();
        } catch (FacturaValidadorException ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
        
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /////////////////////////gets y sets producto/////////////////
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public String formateaFecha(Date fec) {
        return fec.toLocaleString();
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<DetalleFactura> getDetalle() {
        if (detalle == null) {
            detalle = new ArrayList<DetalleFactura>();
        }
        return detalle;
    }

    public void setDetalle(List<DetalleFactura> detalle) {
        this.detalle = detalle;
    }

    public DetalleFactura getDetalleSelected() {
        return detalleSelected;
    }

    public void setDetalleSelected(DetalleFactura detalleSelected) {
        this.detalleSelected = detalleSelected;
    }
    ///////////////////////////////gets y sets de los totales

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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTota() {
        return tota;
    }

    public void setTota(double tota) {
        this.tota = tota;
    }

}
