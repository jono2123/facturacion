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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findByAlmaId", query = "SELECT a FROM Almacen a WHERE a.almaId = :almaId"),
    @NamedQuery(name = "Almacen.findByAlmaNombre", query = "SELECT a FROM Almacen a WHERE a.almaNombre = :almaNombre"),
    @NamedQuery(name = "Almacen.findByAlmaDireccion", query = "SELECT a FROM Almacen a WHERE a.almaDireccion = :almaDireccion"),
    @NamedQuery(name = "Almacen.findByAlmaRuc", query = "SELECT a FROM Almacen a WHERE a.almaRuc = :almaRuc"),
    @NamedQuery(name = "Almacen.findByAlmaTelefono", query = "SELECT a FROM Almacen a WHERE a.almaTelefono = :almaTelefono"),
    @NamedQuery(name = "Almacen.findByAlmaEstado", query = "SELECT a FROM Almacen a WHERE a.almaEstado = :almaEstado"),
    @NamedQuery(name = "Almacen.findByAlmaIva", query = "SELECT a FROM Almacen a WHERE a.almaIva = :almaIva"),
    @NamedQuery(name = "Almacen.findByAlmaDireccionServidor", query = "SELECT a FROM Almacen a WHERE a.almaDireccionServidor = :almaDireccionServidor"),
    @NamedQuery(name = "Almacen.findByAlmaPropietario", query = "SELECT a FROM Almacen a WHERE a.almaPropietario = :almaPropietario"),
    @NamedQuery(name = "Almacen.findByAlmaFacturaAutonumerada", query = "SELECT a FROM Almacen a WHERE a.almaFacturaAutonumerada = :almaFacturaAutonumerada"),
    @NamedQuery(name = "Almacen.findByAlmaNumFactura", query = "SELECT a FROM Almacen a WHERE a.almaNumFactura = :almaNumFactura")})
public class Almacen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "alma_id", nullable = false)
    private Integer almaId;
    @Size(max = 50)
    @Column(name = "alma_nombre", length = 50)
    private String almaNombre;
    @Size(max = 50)
    @Column(name = "alma_direccion", length = 50)
    private String almaDireccion;
    @Size(max = 13)
    @Column(name = "alma_ruc", length = 13)
    private String almaRuc;
    @Size(max = 15)
    @Column(name = "alma_telefono", length = 15)
    private String almaTelefono;
    @Column(name = "alma_estado")
    private Integer almaEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "alma_iva", precision = 17, scale = 17)
    private Double almaIva;
    @Size(max = 80)
    @Column(name = "alma_direccion_servidor", length = 80)
    private String almaDireccionServidor;
    @Size(max = 50)
    @Column(name = "alma_propietario", length = 50)
    private String almaPropietario;
    @Column(name = "alma_factura_autonumerada")
    private Boolean almaFacturaAutonumerada;
    @Column(name = "alma_num_factura")
    private Integer almaNumFactura;

    public Almacen() {
    }

    public Almacen(Integer almaId) {
        this.almaId = almaId;
    }

    public Integer getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Integer almaId) {
        this.almaId = almaId;
    }

    public String getAlmaNombre() {
        return almaNombre;
    }

    public void setAlmaNombre(String almaNombre) {
        this.almaNombre = almaNombre;
    }

    public String getAlmaDireccion() {
        return almaDireccion;
    }

    public void setAlmaDireccion(String almaDireccion) {
        this.almaDireccion = almaDireccion;
    }

    public String getAlmaRuc() {
        return almaRuc;
    }

    public void setAlmaRuc(String almaRuc) {
        this.almaRuc = almaRuc;
    }

    public String getAlmaTelefono() {
        return almaTelefono;
    }

    public void setAlmaTelefono(String almaTelefono) {
        this.almaTelefono = almaTelefono;
    }

    public Integer getAlmaEstado() {
        return almaEstado;
    }

    public void setAlmaEstado(Integer almaEstado) {
        this.almaEstado = almaEstado;
    }

    public Double getAlmaIva() {
        return almaIva;
    }

    public void setAlmaIva(Double almaIva) {
        this.almaIva = almaIva;
    }

    public String getAlmaDireccionServidor() {
        return almaDireccionServidor;
    }

    public void setAlmaDireccionServidor(String almaDireccionServidor) {
        this.almaDireccionServidor = almaDireccionServidor;
    }

    public String getAlmaPropietario() {
        return almaPropietario;
    }

    public void setAlmaPropietario(String almaPropietario) {
        this.almaPropietario = almaPropietario;
    }

    public Boolean getAlmaFacturaAutonumerada() {
        return almaFacturaAutonumerada;
    }

    public void setAlmaFacturaAutonumerada(Boolean almaFacturaAutonumerada) {
        this.almaFacturaAutonumerada = almaFacturaAutonumerada;
    }

    public Integer getAlmaNumFactura() {
        return almaNumFactura;
    }

    public void setAlmaNumFactura(Integer almaNumFactura) {
        this.almaNumFactura = almaNumFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (almaId != null ? almaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.almaId == null && other.almaId != null) || (this.almaId != null && !this.almaId.equals(other.almaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Almacen[ almaId=" + almaId + " ]";
    }
    
}
