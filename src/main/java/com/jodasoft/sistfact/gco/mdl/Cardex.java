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
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cardex.findAll", query = "SELECT c FROM Cardex c"),
    @NamedQuery(name = "Cardex.findByCardId", query = "SELECT c FROM Cardex c WHERE c.cardId = :cardId"),
    @NamedQuery(name = "Cardex.findByCardOperacion", query = "SELECT c FROM Cardex c WHERE c.cardOperacion = :cardOperacion"),
    @NamedQuery(name = "Cardex.findByCardCantidad", query = "SELECT c FROM Cardex c WHERE c.cardCantidad = :cardCantidad"),
    @NamedQuery(name = "Cardex.findByCardFecha", query = "SELECT c FROM Cardex c WHERE c.cardFecha = :cardFecha")})
public class Cardex implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "card_id", nullable = false)
    private Integer cardId;
    @Size(max = 8)
    @Column(name = "card_operacion", length = 8)
    private String cardOperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "card_cantidad", precision = 17, scale = 17)
    private Double cardCantidad;
    @Column(name = "card_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cardFecha;
    @Column(name="card_numero_documento")
    private Integer numeroDocumento;
    @Column(name = "card_tipo_documento")
    private String cardTipoDocumento;
    @JoinColumn(name = "lote_id", referencedColumnName = "lote_id")
    @ManyToOne
    private Lote loteId;

    public Cardex() {
    }

    public Cardex(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardOperacion() {
        return cardOperacion;
    }

    public void setCardOperacion(String cardOperacion) {
        this.cardOperacion = cardOperacion;
    }

    public Double getCardCantidad() {
        return cardCantidad;
    }

    public void setCardCantidad(Double cardCantidad) {
        this.cardCantidad = cardCantidad;
    }

    public Date getCardFecha() {
        return cardFecha;
    }

    public void setCardFecha(Date cardFecha) {
        this.cardFecha = cardFecha;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Lote getLoteId() {
        return loteId;
    }

    public void setLoteId(Lote loteId) {
        this.loteId = loteId;
    }

    public String getCardTipoDocumento() {
        return cardTipoDocumento;
    }

    public void setCardTipoDocumento(String cardTipoDocumento) {
        this.cardTipoDocumento = cardTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardId != null ? cardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cardex)) {
            return false;
        }
        Cardex other = (Cardex) object;
        if ((this.cardId == null && other.cardId != null) || (this.cardId != null && !this.cardId.equals(other.cardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Cardex[ cardId=" + cardId + " ]";
    }
    
}
