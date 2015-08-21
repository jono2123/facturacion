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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "caja_transaccion", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajaTransaccion.findAll", query = "SELECT c FROM CajaTransaccion c"),
    @NamedQuery(name = "CajaTransaccion.findByCatrId", query = "SELECT c FROM CajaTransaccion c WHERE c.catrId = :catrId"),
    @NamedQuery(name = "CajaTransaccion.findByCatrDocumento", query = "SELECT c FROM CajaTransaccion c WHERE c.catrDocumento = :catrDocumento"),
    @NamedQuery(name = "CajaTransaccion.findByCatrTipoDocumento", query = "SELECT c FROM CajaTransaccion c WHERE c.catrTipoDocumento = :catrTipoDocumento"),
    @NamedQuery(name = "CajaTransaccion.findByCatrTimestamp", query = "SELECT c FROM CajaTransaccion c WHERE c.catrTimestamp = :catrTimestamp"),
    @NamedQuery(name = "CajaTransaccion.findByCatrValorTransaccion", query = "SELECT c FROM CajaTransaccion c WHERE c.catrValorTransaccion = :catrValorTransaccion"),
    @NamedQuery(name = "CajaTransaccion.findByCatrConcepto", query = "SELECT c FROM CajaTransaccion c WHERE c.catrConcepto = :catrConcepto"),
    @NamedQuery(name = "CajaTransaccion.findByCatrTipoTransaccion", query = "SELECT c FROM CajaTransaccion c WHERE c.catrTipoTransaccion = :catrTipoTransaccion"),
    @NamedQuery(name = "CajaTransaccion.findByCatrIdUsuario", query = "SELECT c FROM CajaTransaccion c WHERE c.catrIdUsuario = :catrIdUsuario")})
public class CajaTransaccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "catr_id", nullable = false)
    private Integer catrId;
    @Size(max = 100)
    @Column(name = "catr_documento", length = 100)
    private String catrDocumento;
    @Size(max = 50)
    @Column(name = "catr_tipo_documento", length = 50)
    private String catrTipoDocumento;
    @Column(name = "catr_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date catrTimestamp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "catr_valor_transaccion", precision = 17, scale = 17)
    private Double catrValorTransaccion;
    @Size(max = 800)
    @Column(name = "catr_concepto", length = 800)
    private String catrConcepto;
    @Size(max = 50)
    @Column(name = "catr_tipo_transaccion", length = 50)
    private String catrTipoTransaccion;
    @Column(name = "catr_id_usuario")
    private Integer catrIdUsuario;
    @JoinColumn(name = "catr_num_caja", referencedColumnName = "caja_id")
    @ManyToOne
    private Caja catrNumCaja;

    public CajaTransaccion() {
    }

    public CajaTransaccion(Integer catrId) {
        this.catrId = catrId;
    }

    public Integer getCatrId() {
        return catrId;
    }

    public void setCatrId(Integer catrId) {
        this.catrId = catrId;
    }

    public String getCatrDocumento() {
        return catrDocumento;
    }

    public void setCatrDocumento(String catrDocumento) {
        this.catrDocumento = catrDocumento;
    }

    public String getCatrTipoDocumento() {
        return catrTipoDocumento;
    }

    public void setCatrTipoDocumento(String catrTipoDocumento) {
        this.catrTipoDocumento = catrTipoDocumento;
    }

    public Date getCatrTimestamp() {
        return catrTimestamp;
    }

    public void setCatrTimestamp(Date catrTimestamp) {
        this.catrTimestamp = catrTimestamp;
    }

    public Double getCatrValorTransaccion() {
        return catrValorTransaccion;
    }

    public void setCatrValorTransaccion(Double catrValorTransaccion) {
        this.catrValorTransaccion = catrValorTransaccion;
    }

    public String getCatrConcepto() {
        return catrConcepto;
    }

    public void setCatrConcepto(String catrConcepto) {
        this.catrConcepto = catrConcepto;
    }

    public String getCatrTipoTransaccion() {
        return catrTipoTransaccion;
    }

    public void setCatrTipoTransaccion(String catrTipoTransaccion) {
        this.catrTipoTransaccion = catrTipoTransaccion;
    }

    public Integer getCatrIdUsuario() {
        return catrIdUsuario;
    }

    public void setCatrIdUsuario(Integer catrIdUsuario) {
        this.catrIdUsuario = catrIdUsuario;
    }

    public Caja getCatrNumCaja() {
        return catrNumCaja;
    }

    public void setCatrNumCaja(Caja catrNumCaja) {
        this.catrNumCaja = catrNumCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catrId != null ? catrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajaTransaccion)) {
            return false;
        }
        CajaTransaccion other = (CajaTransaccion) object;
        if ((this.catrId == null && other.catrId != null) || (this.catrId != null && !this.catrId.equals(other.catrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.CajaTransaccion[ catrId=" + catrId + " ]";
    }
    
}
