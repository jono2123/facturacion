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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Cacheable(false)
@Table(name = "precio_venta", catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ticl_id", "arti_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrecioVenta.findAll", query = "SELECT p FROM PrecioVenta p"),
    @NamedQuery(name = "PrecioVenta.findByPrveId", query = "SELECT p FROM PrecioVenta p WHERE p.prveId = :prveId"),
    @NamedQuery(name = "PrecioVenta.findByArtiId", query = "SELECT p FROM PrecioVenta p WHERE p.artiId = :artiId"),
    @NamedQuery(name = "PrecioVenta.findByArtiIdAndTiclId", query = "SELECT p FROM PrecioVenta p WHERE p.artiId = :artiId and p.ticlId = :ticlId"),
    @NamedQuery(name = "PrecioVenta.findByPrecio", query = "SELECT p FROM PrecioVenta p WHERE p.precio = :precio")})
public class PrecioVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prve_id", nullable = false)
    private Integer prveId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double precio;
    @JoinColumn(name = "arti_id", referencedColumnName = "arti_id")
    @ManyToOne
    private Articulo artiId;
    @JoinColumn(name = "ticl_id", referencedColumnName = "ticl_id")
    @ManyToOne
    private TipoCliente ticlId;

    public PrecioVenta() {
    }

    public PrecioVenta(Integer prveId) {
        this.prveId = prveId;
    }

    public Integer getPrveId() {
        return prveId;
    }

    public void setPrveId(Integer prveId) {
        this.prveId = prveId;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Articulo getArtiId() {
        return artiId;
    }

    public void setArtiId(Articulo artiId) {
        this.artiId = artiId;
    }

    public TipoCliente getTiclId() {
        return ticlId;
    }

    public void setTiclId(TipoCliente ticlId) {
        this.ticlId = ticlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prveId != null ? prveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrecioVenta)) {
            return false;
        }
        PrecioVenta other = (PrecioVenta) object;
        if ((this.prveId == null && other.prveId != null) || (this.prveId != null && !this.prveId.equals(other.prveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.PrecioVenta[ prveId=" + prveId + " ]";
    }
    
}
