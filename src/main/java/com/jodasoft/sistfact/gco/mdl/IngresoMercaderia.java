/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "ingreso_mercaderia", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoMercaderia.findAll", query = "SELECT i FROM IngresoMercaderia i"),
    @NamedQuery(name = "IngresoMercaderia.findByRegId", query = "SELECT i FROM IngresoMercaderia i WHERE i.regId = :regId"),
    @NamedQuery(name = "IngresoMercaderia.findByRegFecha", query = "SELECT i FROM IngresoMercaderia i WHERE i.regFecha = :regFecha"),
    @NamedQuery(name = "IngresoMercaderia.findByRegEstado", query = "SELECT i FROM IngresoMercaderia i WHERE i.regEstado = :regEstado"),
    @NamedQuery(name = "IngresoMercaderia.findByRegConcepto", query = "SELECT i FROM IngresoMercaderia i WHERE i.regConcepto = :regConcepto"),
    @NamedQuery(name = "IngresoMercaderia.findByRegSubutotal", query = "SELECT i FROM IngresoMercaderia i WHERE i.regSubutotal = :regSubutotal"),
    @NamedQuery(name = "IngresoMercaderia.findByRegIva", query = "SELECT i FROM IngresoMercaderia i WHERE i.regIva = :regIva"),
    @NamedQuery(name = "IngresoMercaderia.findByRegTotal", query = "SELECT i FROM IngresoMercaderia i WHERE i.regTotal = :regTotal"),
    @NamedQuery(name = "IngresoMercaderia.findByRegSubtotalSinIva", query = "SELECT i FROM IngresoMercaderia i WHERE i.regSubtotalSinIva = :regSubtotalSinIva")})
public class IngresoMercaderia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reg_id", nullable = false)
    private Integer regId;
    @Column(name = "reg_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regFecha;
    @Column(name = "reg_estado")
    private Boolean regEstado;
    @Size(max = 2147483647)
    @Column(name = "reg_concepto", length = 2147483647)
    private String regConcepto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "reg_subutotal", precision = 17, scale = 17)
    private Double regSubutotal;
    @Column(name = "reg_iva", precision = 17, scale = 17)
    private Double regIva;
    @Column(name = "reg_total", precision = 17, scale = 17)
    private Double regTotal;
    @Column(name = "reg_subtotal_sin_iva", precision = 17, scale = 17)
    private Double regSubtotalSinIva;
    @OneToMany(mappedBy = "regId",cascade = CascadeType.ALL)
    private List<IngresoMercaderiaDetalle> ingresoMercaderiaDetalleList;
    @JoinColumn(name = "usua_id", referencedColumnName = "usua_id")
    @ManyToOne
    private Usuario usuaId;

    public IngresoMercaderia() {
    }

    public IngresoMercaderia(Integer regId) {
        this.regId = regId;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public Date getRegFecha() {
        return regFecha;
    }

    public void setRegFecha(Date regFecha) {
        this.regFecha = regFecha;
    }

    public Boolean getRegEstado() {
        return regEstado;
    }

    public void setRegEstado(Boolean regEstado) {
        this.regEstado = regEstado;
    }

    public String getRegConcepto() {
        return regConcepto;
    }

    public void setRegConcepto(String regConcepto) {
        this.regConcepto = regConcepto;
    }

    public Double getRegSubutotal() {
        return regSubutotal;
    }

    public void setRegSubutotal(Double regSubutotal) {
        this.regSubutotal = regSubutotal;
    }

    public Double getRegIva() {
        return regIva;
    }

    public void setRegIva(Double regIva) {
        this.regIva = regIva;
    }

    public Double getRegTotal() {
        return regTotal;
    }

    public void setRegTotal(Double regTotal) {
        this.regTotal = regTotal;
    }

    public Double getRegSubtotalSinIva() {
        return regSubtotalSinIva;
    }

    public void setRegSubtotalSinIva(Double regSubtotalSinIva) {
        this.regSubtotalSinIva = regSubtotalSinIva;
    }

    @XmlTransient
    public List<IngresoMercaderiaDetalle> getIngresoMercaderiaDetalleList() {
        return ingresoMercaderiaDetalleList;
    }

    public void setIngresoMercaderiaDetalleList(List<IngresoMercaderiaDetalle> ingresoMercaderiaDetalleList) {
        this.ingresoMercaderiaDetalleList = ingresoMercaderiaDetalleList;
    }

    public Usuario getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Usuario usuaId) {
        this.usuaId = usuaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regId != null ? regId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoMercaderia)) {
            return false;
        }
        IngresoMercaderia other = (IngresoMercaderia) object;
        if ((this.regId == null && other.regId != null) || (this.regId != null && !this.regId.equals(other.regId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.IngresoMercaderia[ regId=" + regId + " ]";
    }
    
}
