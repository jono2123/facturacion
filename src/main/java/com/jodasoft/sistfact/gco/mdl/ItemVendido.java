/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import static javax.persistence.ParameterMode.IN;
import javax.persistence.StoredProcedureParameter;

/**
 *
 * @author johnny
 */
@NamedStoredProcedureQuery(
    name = "findVentasByDateRange",
    resultClasses = ItemVendido.class,
    procedureName = "item_vendido_por_fechas",
    parameters = {
        @StoredProcedureParameter(mode=IN, name="fecha_ini", type=Date.class),
        @StoredProcedureParameter(mode=IN, name="fecha_fin", type=Date.class),
        @StoredProcedureParameter(mode=IN, name="almaid", type=Integer.class),
        @StoredProcedureParameter(mode=IN, name="estado", type=Integer.class)
            
    }
)
@Entity
@Cacheable(false)
public class ItemVendido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long numero;
    private Integer almacen;
    private Integer articulo;
    private String codigo;
    private String descripcion;
    private Double costo;
    private Double precio_venta_promedio;
    private Double cantidad;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Integer almacen) {
        this.almacen = almacen;
    }

    public Integer getArticulo() {
        return articulo;
    }

    public void setArticulo(Integer articulo) {
        this.articulo = articulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrecio_venta_promedio() {
        return precio_venta_promedio;
    }

    public void setPrecio_venta_promedio(Double precio_venta_promedio) {
        this.precio_venta_promedio = precio_venta_promedio;
    }



    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemVendido)) {
            return false;
        }
        ItemVendido other = (ItemVendido) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.ItemVendido[ id=" + numero + " ]";
    }
    
}
