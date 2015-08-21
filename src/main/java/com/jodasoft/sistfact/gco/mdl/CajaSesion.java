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
@Table(name = "caja_sesion", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajaSesion.findAll", query = "SELECT c FROM CajaSesion c"),
    @NamedQuery(name = "CajaSesion.findByCaseId", query = "SELECT c FROM CajaSesion c WHERE c.caseId = :caseId"),
    @NamedQuery(name = "CajaSesion.findByCaseTimeApertura", query = "SELECT c FROM CajaSesion c WHERE c.caseTimeApertura = :caseTimeApertura"),
    @NamedQuery(name = "CajaSesion.findByCaseTimeCierre", query = "SELECT c FROM CajaSesion c WHERE c.caseTimeCierre = :caseTimeCierre"),
    @NamedQuery(name = "CajaSesion.findByCaseEstado", query = "SELECT c FROM CajaSesion c WHERE c.caseEstado = :caseEstado"),
    @NamedQuery(name = "CajaSesion.findByCaseIdUsuario", query = "SELECT c FROM CajaSesion c WHERE c.caseIdUsuario = :caseIdUsuario")})
public class CajaSesion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "case_id", nullable = false)
    private Integer caseId;
    @Column(name = "case_time_apertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caseTimeApertura;
    @Column(name = "case_time_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caseTimeCierre;
    @Size(max = 20)
    @Column(name = "case_estado", length = 20)
    private String caseEstado;
    @Column(name = "case_id_usuario")
    private Integer caseIdUsuario;
    @JoinColumn(name = "case_num_caja", referencedColumnName = "caja_id")
    @ManyToOne
    private Caja caseNumCaja;

    public CajaSesion() {
    }

    public CajaSesion(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Date getCaseTimeApertura() {
        return caseTimeApertura;
    }

    public void setCaseTimeApertura(Date caseTimeApertura) {
        this.caseTimeApertura = caseTimeApertura;
    }

    public Date getCaseTimeCierre() {
        return caseTimeCierre;
    }

    public void setCaseTimeCierre(Date caseTimeCierre) {
        this.caseTimeCierre = caseTimeCierre;
    }

    public String getCaseEstado() {
        return caseEstado;
    }

    public void setCaseEstado(String caseEstado) {
        this.caseEstado = caseEstado;
    }

    public Integer getCaseIdUsuario() {
        return caseIdUsuario;
    }

    public void setCaseIdUsuario(Integer caseIdUsuario) {
        this.caseIdUsuario = caseIdUsuario;
    }

    public Caja getCaseNumCaja() {
        return caseNumCaja;
    }

    public void setCaseNumCaja(Caja caseNumCaja) {
        this.caseNumCaja = caseNumCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caseId != null ? caseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajaSesion)) {
            return false;
        }
        CajaSesion other = (CajaSesion) object;
        if ((this.caseId == null && other.caseId != null) || (this.caseId != null && !this.caseId.equals(other.caseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.CajaSesion[ caseId=" + caseId + " ]";
    }
    
}
