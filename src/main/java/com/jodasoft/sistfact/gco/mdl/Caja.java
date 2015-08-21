/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnny
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c"),
    @NamedQuery(name = "Caja.findByCajaId", query = "SELECT c FROM Caja c WHERE c.cajaId = :cajaId"),
    @NamedQuery(name = "Caja.findByCajaNumCaja", query = "SELECT c FROM Caja c WHERE c.cajaNumCaja = :cajaNumCaja"),
    @NamedQuery(name = "Caja.findByCajaIpImpresion", query = "SELECT c FROM Caja c WHERE c.cajaIpImpresion = :cajaIpImpresion"),
    @NamedQuery(name = "Caja.findByCajaNumFactura", query = "SELECT c FROM Caja c WHERE c.cajaNumFactura = :cajaNumFactura"),
    @NamedQuery(name = "Caja.findByCajaIdAlma", query = "SELECT c FROM Caja c WHERE c.cajaIdAlma = :cajaIdAlma")})
public class Caja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "caja_id", nullable = false)
    private Integer cajaId;
    @Column(name = "caja_num_caja")
    private Integer cajaNumCaja;
    @Size(max = 50)
    @Column(name = "caja_ip_impresion", length = 50)
    private String cajaIpImpresion;
    @Column(name = "caja_num_factura")
    private Integer cajaNumFactura;
    @Column(name = "caja_id_alma")
    private Integer cajaIdAlma;
    @OneToMany(mappedBy = "catrNumCaja")
    private List<CajaTransaccion> cajaTransaccionList;
    @OneToMany(mappedBy = "caseNumCaja")
    private List<CajaSesion> cajaSesionList;

    public Caja() {
    }

    public Caja(Integer cajaId) {
        this.cajaId = cajaId;
    }

    public Integer getCajaId() {
        return cajaId;
    }

    public void setCajaId(Integer cajaId) {
        this.cajaId = cajaId;
    }

    public Integer getCajaNumCaja() {
        return cajaNumCaja;
    }

    public void setCajaNumCaja(Integer cajaNumCaja) {
        this.cajaNumCaja = cajaNumCaja;
    }

    public String getCajaIpImpresion() {
        return cajaIpImpresion;
    }

    public void setCajaIpImpresion(String cajaIpImpresion) {
        this.cajaIpImpresion = cajaIpImpresion;
    }

    public Integer getCajaNumFactura() {
        return cajaNumFactura;
    }

    public void setCajaNumFactura(Integer cajaNumFactura) {
        this.cajaNumFactura = cajaNumFactura;
    }

    public Integer getCajaIdAlma() {
        return cajaIdAlma;
    }

    public void setCajaIdAlma(Integer cajaIdAlma) {
        this.cajaIdAlma = cajaIdAlma;
    }

    @XmlTransient
    public List<CajaTransaccion> getCajaTransaccionList() {
        return cajaTransaccionList;
    }

    public void setCajaTransaccionList(List<CajaTransaccion> cajaTransaccionList) {
        this.cajaTransaccionList = cajaTransaccionList;
    }

    @XmlTransient
    public List<CajaSesion> getCajaSesionList() {
        return cajaSesionList;
    }

    public void setCajaSesionList(List<CajaSesion> cajaSesionList) {
        this.cajaSesionList = cajaSesionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajaId != null ? cajaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.cajaId == null && other.cajaId != null) || (this.cajaId != null && !this.cajaId.equals(other.cajaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Caja[ cajaId=" + cajaId + " ]";
    }
    
}
