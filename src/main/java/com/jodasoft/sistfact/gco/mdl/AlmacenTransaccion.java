/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "almacen_transaccion", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlmacenTransaccion.findAll", query = "SELECT a FROM AlmacenTransaccion a"),
    @NamedQuery(name = "AlmacenTransaccion.findByTransId", query = "SELECT a FROM AlmacenTransaccion a WHERE a.transId = :transId"),
    @NamedQuery(name = "AlmacenTransaccion.findByTransConcepto", query = "SELECT a FROM AlmacenTransaccion a WHERE a.transConcepto = :transConcepto"),
    @NamedQuery(name = "AlmacenTransaccion.findByTransNumDocumento", query = "SELECT a FROM AlmacenTransaccion a WHERE a.transNumDocumento = :transNumDocumento"),
    @NamedQuery(name = "AlmacenTransaccion.findByTransFecha", query = "SELECT a FROM AlmacenTransaccion a WHERE a.transFecha = :transFecha")})
public class AlmacenTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_id", nullable = false)
    private Integer transId;
    @Size(max = 50)
    @Column(name = "trans_concepto", length = 50)
    private String transConcepto;
    @Column(name = "trans_num_documento")
    private Integer transNumDocumento;
    @Column(name = "trans_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transFecha;
    @JoinColumn(name = "usua_id", referencedColumnName = "usua_id")
    @ManyToOne
    private Usuario usuaId;

    public AlmacenTransaccion() {
    }

    public AlmacenTransaccion(Integer transId) {
        this.transId = transId;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getTransConcepto() {
        return transConcepto;
    }

    public void setTransConcepto(String transConcepto) {
        this.transConcepto = transConcepto;
    }

    public Integer getTransNumDocumento() {
        return transNumDocumento;
    }

    public void setTransNumDocumento(Integer transNumDocumento) {
        this.transNumDocumento = transNumDocumento;
    }

    public Date getTransFecha() {
        return transFecha;
    }

    public void setTransFecha(Date transFecha) {
        this.transFecha = transFecha;
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
        hash += (transId != null ? transId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlmacenTransaccion)) {
            return false;
        }
        AlmacenTransaccion other = (AlmacenTransaccion) object;
        if ((this.transId == null && other.transId != null) || (this.transId != null && !this.transId.equals(other.transId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.AlmacenTransaccion[ transId=" + transId + " ]";
    }
    
}
