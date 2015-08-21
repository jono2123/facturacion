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
    @NamedQuery(name = "Lote.findAll", query = "SELECT l FROM Lote l"),
    @NamedQuery(name = "Lote.findByLoteId", query = "SELECT l FROM Lote l WHERE l.loteId = :loteId"),
    @NamedQuery(name = "Lote.findByLoteCantidadInicial", query = "SELECT l FROM Lote l WHERE l.loteCantidadInicial = :loteCantidadInicial"),
    @NamedQuery(name = "Lote.findByLoteCantidadActual", query = "SELECT l FROM Lote l WHERE l.loteCantidadActual = :loteCantidadActual"),
    @NamedQuery(name = "Lote.findByLoteCosto", query = "SELECT l FROM Lote l WHERE l.loteCosto = :loteCosto"),
    @NamedQuery(name = "Lote.findByLoteFechaCaducidad", query = "SELECT l FROM Lote l WHERE l.loteFechaCaducidad = :loteFechaCaducidad")})
public class Lote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lote_id", nullable = false)
    private Integer loteId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lote_cantidad_inicial", precision = 17, scale = 17)
    private Double loteCantidadInicial;
    @Column(name = "lote_cantidad_actual", precision = 17, scale = 17)
    private Double loteCantidadActual;
    @Column(name = "lote_costo", precision = 17, scale = 17)
    private Double loteCosto;
    @Column(name = "lote_fecha_caducidad")
    @Temporal(TemporalType.DATE)
    private Date loteFechaCaducidad;
    @Column(name="lote_codigo")
    private String loteCodigo;
    @Column(name = "lote_fecha_elaboracion")
    @Temporal(TemporalType.DATE)
    private Date loteFechaElaboracion;
    
    @JoinColumn(name = "arti_id", referencedColumnName = "arti_id")
    @ManyToOne
    private Articulo artiId;
    @OneToMany(mappedBy = "loteId")
    private List<Cardex> cardexList;

    public Lote() {
    }

    public Lote(Integer loteId) {
        this.loteId = loteId;
    }

    public Integer getLoteId() {
        return loteId;
    }

    public void setLoteId(Integer loteId) {
        this.loteId = loteId;
    }

    public Double getLoteCantidadInicial() {
        return loteCantidadInicial;
    }

    public void setLoteCantidadInicial(Double loteCantidadInicial) {
        this.loteCantidadInicial = loteCantidadInicial;
    }

    public Double getLoteCantidadActual() {
        return loteCantidadActual;
    }

    public void setLoteCantidadActual(Double loteCantidadActual) {
        this.loteCantidadActual = loteCantidadActual;
    }

    public Double getLoteCosto() {
        return loteCosto;
    }

    public void setLoteCosto(Double loteCosto) {
        this.loteCosto = loteCosto;
    }

    public Date getLoteFechaCaducidad() {
        return loteFechaCaducidad;
    }

    public void setLoteFechaCaducidad(Date loteFechaCaducidad) {
        this.loteFechaCaducidad = loteFechaCaducidad;
    }

    public Articulo getArtiId() {
        return artiId;
    }

    public void setArtiId(Articulo artiId) {
        this.artiId = artiId;
    }

    public String getLoteCodigo() {
        return loteCodigo;
    }

    public void setLoteCodigo(String loteCodigo) {
        this.loteCodigo = loteCodigo;
    }

    public Date getLoteFechaElaboracion() {
        return loteFechaElaboracion;
    }

    public void setLoteFechaElaboracion(Date loteFechaElaboracion) {
        this.loteFechaElaboracion = loteFechaElaboracion;
    }

    @XmlTransient
    public List<Cardex> getCardexList() {
        return cardexList;
    }

    public void setCardexList(List<Cardex> cardexList) {
        this.cardexList = cardexList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loteId != null ? loteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.loteId == null && other.loteId != null) || (this.loteId != null && !this.loteId.equals(other.loteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Lote[ loteId=" + loteId + " ]";
    }
    
}
