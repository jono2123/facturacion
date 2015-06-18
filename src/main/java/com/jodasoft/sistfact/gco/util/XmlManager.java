/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.util;

import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.Factura;
import java.text.DecimalFormat;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author javila
 */
public class XmlManager {

    private DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");

    public double redondear(double num) {
        num *= 100.0D;
        num = Math.round(num);
        int numr = (int) num;
        return numr / 100.0D;
    }

    public String Formato(double num) {
        return df2.format(num);
    }

    public String createFactura(Factura fact) {
        String xml = "";
        Element factura = new Element("factura");
        Document documento = new Document(factura);
        Element encabezado = new Element("encabezado");
        Element numero = new Element("numero");
        numero.setText(fact.getFactNumero().toString());
        encabezado.addContent(numero);

        Element cedula = new Element("cedula");
        cedula.setText(fact.getClieId().getPersCedula());
        encabezado.addContent(cedula);

        Element nombre = new Element("nombre");
        nombre.setText(fact.getClieId().getPersNombres() + " " + fact.getClieId().getPersApellidos());
        encabezado.addContent(nombre);

        Element direccion = new Element("direccion");
        direccion.setText(fact.getClieId().getPersDireccion());
        encabezado.addContent(direccion);

        Element telefono = new Element("telefono");
        telefono.setText(fact.getClieId().getPersTelefono());
        encabezado.addContent(telefono);

        Element fecha = new Element("fecha");
        fecha.setText(fact.getFactFecha().toLocaleString().substring(0, fact.getFactFecha().toLocaleString().indexOf(" ")));
        encabezado.addContent(fecha);

        factura.addContent(encabezado);

        Element detalle = new Element("detalle");
        for (DetalleFactura deta : fact.getDetalleFacturaList()) {
            Element item = new Element("item");

            Element codigo = new Element("codigo");
            codigo.setText(deta.getArtiId().getArtiCodigo());
            item.addContent(codigo);

            Element cantidad = new Element("cantidad");
            cantidad.setText(Formato(deta.getDefaCantidad()));
            item.addContent(cantidad);

            Element descripcion = new Element("descripcion");
            descripcion.setText(deta.getArtiId().getArtiDescripcion());
            item.addContent(descripcion);

            Element valorUnitario = new Element("vunitario");
            valorUnitario.setText(Formato(deta.getDefaPrecioVenta()));
            item.addContent(valorUnitario);

            Element valorTotal = new Element("vtotal");
            valorTotal.setText(Formato(deta.getDefaCantidad() * deta.getDefaPrecioVenta()));
            item.addContent(valorTotal);

            detalle.addContent(item);

        }

        factura.addContent(detalle);
        
        Element totales = new Element("totales");
        
        Element subtotal = new Element("subtotal");
        subtotal.setText(Formato(fact.getFactSubtotalConIva()+ fact.getFactSubtotalSinIva()));
        totales.addContent(subtotal);
        
        Element subtotalSinIva = new Element("subtsiniva");
        subtotalSinIva.setText(Formato(fact.getFactSubtotalSinIva()));
        totales.addContent(subtotalSinIva);
        
        Element subtotalConIva = new Element("subtconiva");
        subtotalConIva.setText(Formato(fact.getFactSubtotalConIva()));
        totales.addContent(subtotalConIva);
        
        Element descuento = new Element("descuento");
        descuento.setText(Formato(fact.getFactDescuento()));
        totales.addContent(descuento);
        
        Element iva = new Element("iva");
        iva.setText(Formato(fact.getFactIva()));
        totales.addContent(iva);
        
        Element total = new Element("total");
        total.setText(Formato(fact.getFactTotal()));
        totales.addContent(total);
        
        factura.addContent(totales);
        
        
        
        XMLOutputter xmlOutput = new XMLOutputter();  
        xmlOutput.setFormat(Format.getCompactFormat());
        xml=xmlOutput.outputString(documento);
        return xml;
    }

}
