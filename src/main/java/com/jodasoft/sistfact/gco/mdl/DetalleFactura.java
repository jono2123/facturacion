/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Cacheable(false)
@Table(name = "detalle_factura", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d"),
    @NamedQuery(name = "DetalleFactura.findByDafaId", query = "SELECT d FROM DetalleFactura d WHERE d.dafaId = :dafaId"),
    @NamedQuery(name = "DetalleFactura.findByDefaCantidad", query = "SELECT d FROM DetalleFactura d WHERE d.defaCantidad = :defaCantidad"),
    @NamedQuery(name = "DetalleFactura.findByDefaPrecioVenta", query = "SELECT d FROM DetalleFactura d WHERE d.defaPrecioVenta = :defaPrecioVenta"),
    @NamedQuery(name = "DetalleFactura.findByDefaObservaciones", query = "SELECT d FROM DetalleFactura d WHERE d.defaObservaciones = :defaObservaciones")})
public class DetalleFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dafa_id", nullable = false)
    private Integer dafaId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "defa_cantidad", precision = 17, scale = 17)
    private Double defaCantidad;
    @Column(name = "defa_precio_venta", precision = 17, scale = 17)
    private Double defaPrecioVenta;
    @Size(max = 50)
    @Column(name = "defa_observaciones", length = 50)
    private String defaObservaciones;
    @Transient
    private int numItem;
    @JoinColumn(name = "arti_id", referencedColumnName = "arti_id")
    @ManyToOne
    private Articulo artiId;
    @JoinColumn(name = "fact_id", referencedColumnName = "fact_id")
    @ManyToOne
    private Factura factId;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer dafaId) {
        this.dafaId = dafaId;
    }

    public Integer getDafaId() {
        return dafaId;
    }

    public void setDafaId(Integer dafaId) {
        this.dafaId = dafaId;
    }

    public Double getDefaCantidad() {
        return defaCantidad;
    }

    public void setDefaCantidad(Double defaCantidad) {
        this.defaCantidad = defaCantidad;
    }

    public Double getDefaPrecioVenta() {
        
        return defaPrecioVenta;
    }

    public void setDefaPrecioVenta(Double defaPrecioVenta) {
        this.defaPrecioVenta = defaPrecioVenta;
    }

    public String getDefaObservaciones() {
        return defaObservaciones;
    }

    public void setDefaObservaciones(String defaObservaciones) {
        this.defaObservaciones = defaObservaciones;
    }

    public Articulo getArtiId() {
        return artiId;
    }

    public void setArtiId(Articulo artiId) {
        this.artiId = artiId;
    }

    public Factura getFactId() {
        return factId;
    }

    public void setFactId(Factura factId) {
        this.factId = factId;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dafaId != null ? dafaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.dafaId == null && other.dafaId != null) || (this.dafaId != null && !this.dafaId.equals(other.dafaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.DetalleFactura[ dafaId=" + dafaId + " ]";
    }
    
}
