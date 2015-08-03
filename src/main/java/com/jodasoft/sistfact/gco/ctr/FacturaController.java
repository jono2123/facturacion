/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.AlmacenTransaccion;
import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.PrecioVenta;
import com.jodasoft.sistfact.gco.util.XmlManager;
import com.jodasoft.sistfact.gco.util.exp.AlmacenTransaccionValidadorException;
import com.jodasoft.sistfact.gco.util.exp.FacturaValidadorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import org.primefaces.context.RequestContext;

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

    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenFacade almacenFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.PrecioVentaFacade precioVentaFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.AmacenTransaccionFacade transaccionFacade;

    private int numero;
    private Cliente cliente;
    private Date fecha = new Date();
    private String guia;
    private String cedula;
    private String nombres;
    private String direccion;
    private String telefono;
    private Permiso permiso;

    //variables para el producto
    private String codigo;
    private double cantidad;
    private double precio;
    private Articulo articulo;
    private String descripcion;

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
    private String codigoFactura;
    private double valorDescuento;

    private boolean modificar;

    public FacturaController() {
        cliente = new Cliente();
        modificar = false;

    }
    /*
     public void obtenCodigo(){
     int numAlmacen= LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId();
     String hexNumAlmacen = Integer.toString(numAlmacen, 35);
     int numUsuario = LoginController.getInstance().getUsuario().getUsuaId();
     String hexNumUsuario = Integer.toString(numUsuario, 35);
     Date d = new Date();
     int anio=d.getYear();
     String hexAnio = Integer.toString(anio, 35);
     int mes = d.getMonth();
     String hexMont = Integer.toHexString(mes);
     int dia = d.getDay();
     String hexDia = Integer.toString(dia, 31);
     int hora = d.getHours();
     String hexHora = Integer.toString(hora,24);
     int minuto = d.getMinutes();
     String hexMinuto = Integer.toString(minuto, 30);
     int segundo = d.getSeconds();
     String hexSegundo = Integer.toString(segundo, 30);
        
     codigoFactura = hexNumAlmacen+hexNumUsuario+hexAnio+hexMont+hexDia+hexHora+hexMinuto+hexSegundo;
     codigoFactura = codigoFactura.toUpperCase();
     }*/

    public void selecciona() {
        cliente = ListaClientesController.getInstance().cliente;
        setCedula(cliente.getPersCedula());
        setNombres(cliente.getPersNombres() + " " + cliente.getPersApellidos());
        setDireccion(cliente.getPersDireccion());
        setTelefono(cliente.getPersTelefono());
        setFecha(new Date());
    }

    public void seleccionaArticulo() {
        articulo = ListaArticulosController.getInstance().getArticulo();
        setCodigo(articulo.getArtiCodigo());
        setPrecio(articulo.getArtiPrecioVenta());
        setDescripcion(articulo.getArtiDescripcion());
        setCantidad(1);
    }

    public void quitar(DetalleFactura item) {
        detalle.remove(item);
        calcular();
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
        setDescripcion("");
        setValorDescuento(0);
        factura = new Factura();
        i = 0;
        modificar = false;
        /*if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaFacturaAutonumerada()) {
         numero = LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaNumFactura();
         }*/
    }

    public void guardaTransaccion(Factura factura) throws AlmacenTransaccionValidadorException {
        AlmacenTransaccion transaccion = new AlmacenTransaccion();
        transaccion.setTransConcepto("FACTURA");
        transaccion.setTransFecha(new Date());
        transaccion.setTransNumDocumento(factura.getFactNumero());
        transaccion.setUsuaId(factura.getUsuaId());
        transaccionFacade.save(transaccion);
    }

    public void buscarPorCedula() {
        cliente = clienteFacade.findClienteByCedula(cedula, LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        if (cliente == null) {
            cliente = new Cliente();
            displayErrorMessageToUser("No se encontró ningún cliente con esa cédula");
        } else {
            setNombres(cliente.getPersNombres() + " " + cliente.getPersApellidos());
            setDireccion(cliente.getPersDireccion());
            setTelefono(cliente.getPersTelefono());
            setFecha(new Date());
        }
    }

    public void buscaArticuloPorCodigo() {
        if (cliente == null || cliente.getTiclId() == null) {
            displayErrorMessageToUser("Primero Seleccione un cliente");
            return;
        }
        articulo = articuloFacade.findByCodigoAndAlmacen(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), codigo);

        if (articulo == null) {
            articulo = new Articulo();
            displayErrorMessageToUser("No se encontró ningún artículo con ese código");
            setPrecio(0);
        } else {
            if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDiferenciarPrecios()) {
                PrecioVenta pVenta = precioVentaFacade.findByArtIdAndTiclId(articulo, cliente.getTiclId());
                if (pVenta != null) {
                    setPrecio(pVenta.getPrecio());
                    setDescripcion(articulo.getArtiDescripcion());
                } else {
                    articulo = null;
                    displayErrorMessageToUser("No se ha asignado un precio de venta para este artículo y este tipo de cliente");
                    setPrecio(0);
                    return;
                }
            } else {
                setPrecio(articulo.getArtiPrecioVenta());
                setDescripcion(articulo.getArtiDescripcion());
            }
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
        if (item.getArtiId().getArtiIva() && LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDesglozar()) {
            item.setDefaPrecioVenta(precio / (1d + (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaIva()) / 100));
        } else {
            item.setDefaPrecioVenta(precio);
        }
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
        setDescripcion("");
    }

    public void calcular() {
        subtTotal = 0;
        subTotalConIva = 0;
        subTotalSinIva = 0;

        iva = 0;
        tota = 0;
        for (DetalleFactura deta : detalle) {
            subtTotal += deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            if (deta.getArtiId().getArtiIva()) {
                subTotalConIva = subTotalConIva + deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            } else {
                subTotalSinIva = subTotalSinIva + deta.getDefaCantidad() * deta.getDefaPrecioVenta();
            }

        }
        valorDescuento = (subTotalConIva + subTotalSinIva) * (descuento / 100);
        double descSubTotalConIva = subTotalConIva * (descuento / 100);

        iva = (subTotalConIva - descSubTotalConIva) * LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaIva() / 100d;

        tota = subTotalConIva + subTotalSinIva + iva - valorDescuento;
    }

    private int existe(DetalleFactura item) {
        for (int i = 0; i < detalle.size(); i++) {
            if (item.getArtiId().getArtiId().equals(detalle.get(i).getArtiId().getArtiId()) && item.getDefaPrecioVenta().equals(detalle.get(i).getDefaPrecioVenta()) && item.getDefaObservaciones().equals(detalle.get(i).getDefaObservaciones())) {
                return i;
            }
        }
        return -1;
    }

    public void guardarFactura() throws FacturaValidadorException {

        //factura.setFactNumero(numero);
        factura.setClieId(cliente);
        factura.setAlmaId(cliente.getAlmaId());
        factura.setFactDescuento(descuento);
        factura.setFactIva(iva);
        factura.setFactSubtotalConIva(subTotalConIva);
        factura.setFactSubtotalSinIva(subTotalSinIva);
        factura.setFactTotal(tota);
        factura.setUsuaId(LoginController.getInstance().getUsuario());
        factura.setFactFecha(fecha);
        for (DetalleFactura deta : detalle) {
            deta.setFactId(factura);
        }
        factura.setDetalleFacturaList(detalle);

        facturaFacade.save(factura);

        displayInfoMessageToUser("Factura guardada correctamente");

    }

    public String guardarCambiosEImprimir() {
        factura.setFactEstado(1);
        factura.setFactDescuento(descuento);
        factura.setFactIva(iva);
        factura.setFactSubtotalConIva(subTotalConIva);
        factura.setFactSubtotalSinIva(subTotalSinIva);
        factura.setFactTotal(tota);
        factura.setUsuaId(LoginController.getInstance().getUsuario());
        factura.setFactFecha(fecha);
        if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaFacturaAutonumerada()) {
            numero = almacenFacade.find(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId()).getAlmaNumFactura().intValue();
            factura.setFactNumero(numero);
            LoginController.getInstance().getUsuario().getRolId().getAlmaId().setAlmaNumFactura(numero + 1);
            almacenFacade.edit(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        facturaFacade.update(factura, detalle);
        closeDialog();
        displayInfoMessageToUser("Factura Modificada Correctamente");
        String xml = new XmlManager().createFactura(factura);
        for (int i = 0; i < LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaMetodoPago(); i++) {
            print(xml);
        }
        limpiarTodo();
        return "reporteVentas.xhtml?faces-redirect=true";
    }

    public void soloGuardar() {
        factura = new Factura();
        factura.setFactEstado(0);
        try {
            guardarFactura();
            closeDialog();
            displayInfoMessageToUser("Factura Guardada Correctamente");
            limpiarTodo();
        } catch (FacturaValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }

    }

    public void guardarEImprimir() {
        //RequestContext requestContext = RequestContext.getCurrentInstance();

        factura = new Factura();
        factura.setFactEstado(1);
        if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaFacturaAutonumerada()) {
            numero = almacenFacade.find(LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaId()).getAlmaNumFactura().intValue();
            factura.setFactNumero(numero);
            LoginController.getInstance().getUsuario().getRolId().getAlmaId().setAlmaNumFactura(numero + 1);
            almacenFacade.edit(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        try {
            guardarFactura();
            closeDialog();
            displayInfoMessageToUser("Factura Guardada Correctamente");
            String xml = new XmlManager().createFactura(factura);
            for (int i = 0; i < LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaNumCopias(); i++) {
                print(xml);
            }
            guardaTransaccion(factura);
            limpiarTodo();
        } catch (FacturaValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        } catch (AlmacenTransaccionValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }

    }

    public String guardarCambios() {
        factura.setFactDescuento(descuento);
        factura.setFactIva(iva);
        factura.setFactSubtotalConIva(subTotalConIva);
        factura.setFactSubtotalSinIva(subTotalSinIva);
        factura.setFactTotal(tota);
        factura.setUsuaId(LoginController.getInstance().getUsuario());
        factura.setFactFecha(fecha);
        facturaFacade.update(factura, detalle);
        closeDialog();
        displayInfoMessageToUser("Factura Modificada Correctamente");
        limpiarTodo();
        return "reporteVentas.xhtml?faces-redirect=true";
    }

    public String cancelar() {
        limpiarTodo();
        return "reporteVentas.xhtml?faces-redirect=true";
    }

    public int getNumero() {
        return numero;
    }

    public void conectarWs() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String functionCrear = "creaWebSocket('" + LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaDireccionServidor() + "')";
        requestContext.execute(functionCrear);
    }

    public void print(String xml) {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        String mensaje = xml.substring(xml.indexOf("<f"), xml.lastIndexOf(">") + 1);

        String funcion = "enviaMensaje('" + mensaje + "')";

        requestContext.execute(funcion);
    }

    public void cargarArticulos() {
        if (cliente == null || cliente.getTiclId() == null) {
            displayErrorMessageToUser("Primero seleccione un cliente");
            return;
        }
        ListaArticulosController.getInstance().setTipo(cliente.getTiclId());
        ListaArticulosController.getInstance().cargarArticulos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgItems').show();");
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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
        detalle = new ArrayList<DetalleFactura>();
        for (DetalleFactura deta : factura.getDetalleFacturaList()) {
            DetalleFactura objDeta = new DetalleFactura();
            objDeta.setArtiId(deta.getArtiId());
            objDeta.setDefaCantidad(deta.getDefaCantidad());
            objDeta.setDefaObservaciones(deta.getDefaObservaciones());
            objDeta.setDefaPrecioVenta(deta.getDefaPrecioVenta());
            objDeta.setFactId(deta.getFactId());
            objDeta.setNumItem(deta.getNumItem());
            detalle.add(objDeta);
        }
        cliente = (Cliente) factura.getClieId();
        fecha = factura.getFactFecha();
        cedula = cliente.getPersCedula();
        direccion = cliente.getPersDireccion();
        nombres = cliente.getPersNombres() + " " + cliente.getPersApellidos();
        telefono = cliente.getPersTelefono();
        descuento = factura.getFactDescuento();
        calcular();
        modificar = true;
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

    public Permiso getPermiso() {
        permiso = LoginController.getInstance().getPermiso("Facturar");
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public String getCodigoFactura() {
        //obtenCodigo();
        return codigoFactura;
    }

    public void setCodigoFactura(String codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public double getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(double valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static FacturaController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{facturaController}", FacturaController.class);
        return (FacturaController) ex.getValue(context);
    }

}
