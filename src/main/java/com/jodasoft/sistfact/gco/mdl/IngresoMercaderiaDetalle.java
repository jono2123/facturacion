/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "ingreso_mercaderia_detalle", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoMercaderiaDetalle.findAll", query = "SELECT i FROM IngresoMercaderiaDetalle i"),
    @NamedQuery(name = "IngresoMercaderiaDetalle.findByImdeId", query = "SELECT i FROM IngresoMercaderiaDetalle i WHERE i.imdeId = :imdeId"),
    @NamedQuery(name = "IngresoMercaderiaDetalle.findByLoteId", query = "SELECT i FROM IngresoMercaderiaDetalle i WHERE i.loteId = :loteId"),
    @NamedQuery(name = "IngresoMercaderiaDetalle.findByImdeCantidad", query = "SELECT i FROM IngresoMercaderiaDetalle i WHERE i.imdeCantidad = :imdeCantidad"),
    @NamedQuery(name = "IngresoMercaderiaDetalle.findByImdeCostoUnitario", query = "SELECT i FROM IngresoMercaderiaDetalle i WHERE i.imdeCostoUnitario = :imdeCostoUnitario"),
    @NamedQuery(name = "IngresoMercaderiaDetalle.findByImdeIva", query = "SELECT i FROM IngresoMercaderiaDetalle i WHERE i.imdeIva = :imdeIva")})
public class IngresoMercaderiaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "imde_id", nullable = false)
    private Integer imdeId;
    @JoinColumn(name = "lote_id", referencedColumnName = "lote_id")
    @ManyToOne
    private Lote loteId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "imde_cantidad", precision = 17, scale = 17)
    private Double imdeCantidad;
    @Column(name = "imde_costo_unitario", precision = 17, scale = 17)
    private Double imdeCostoUnitario;
    @Column(name = "imde_iva", precision = 17, scale = 17)
    private Double imdeIva;
    @JoinColumn(name = "arti_id", referencedColumnName = "arti_id")
    @ManyToOne
    private Articulo artiId;
    @JoinColumn(name = "reg_id", referencedColumnName = "reg_id")
    @ManyToOne
    private IngresoMercaderia regId;
    @Transient 
    private Integer numItem;
    @Transient
    private String loteCodigo;

    public IngresoMercaderiaDetalle() {
    }

    public IngresoMercaderiaDetalle(Integer imdeId) {
        this.imdeId = imdeId;
    }

    public Integer getImdeId() {
        return imdeId;
    }

    public void setImdeId(Integer imdeId) {
        this.imdeId = imdeId;
    }

    public Lote getLoteId() {
        return loteId;
    }

    public void setLoteId(Lote loteId) {
        this.loteId = loteId;
    }



    public Double getImdeCantidad() {
        return imdeCantidad;
    }

    public void setImdeCantidad(Double imdeCantidad) {
        this.imdeCantidad = imdeCantidad;
    }

    public Double getImdeCostoUnitario() {
        return imdeCostoUnitario;
    }

    public void setImdeCostoUnitario(Double imdeCostoUnitario) {
        this.imdeCostoUnitario = imdeCostoUnitario;
    }

    public Double getImdeIva() {
        return imdeIva;
    }

    public void setImdeIva(Double imdeIva) {
        this.imdeIva = imdeIva;
    }

    public Articulo getArtiId() {
        return artiId;
    }

    public void setArtiId(Articulo artiId) {
        this.artiId = artiId;
    }

    public IngresoMercaderia getRegId() {
        return regId;
    }

    public void setRegId(IngresoMercaderia regId) {
        this.regId = regId;
    }

    public Integer getNumItem() {
        return numItem;
    }

    public void setNumItem(Integer numItem) {
        this.numItem = numItem;
    }

    public String getLoteCodigo() {
        return loteCodigo;
    }

    public void setLoteCodigo(String loteCodigo) {
        this.loteCodigo = loteCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imdeId != null ? imdeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoMercaderiaDetalle)) {
            return false;
        }
        IngresoMercaderiaDetalle other = (IngresoMercaderiaDetalle) object;
        if ((this.imdeId == null && other.imdeId != null) || (this.imdeId != null && !this.imdeId.equals(other.imdeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.IngresoMercaderiaDetalle[ imdeId=" + imdeId + " ]";
    }
    
}
