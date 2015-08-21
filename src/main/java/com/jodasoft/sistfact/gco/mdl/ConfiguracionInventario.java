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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "configuracion_inventario", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracionInventario.findAll", query = "SELECT c FROM ConfiguracionInventario c"),
    @NamedQuery(name = "ConfiguracionInventario.findByCoinId", query = "SELECT c FROM ConfiguracionInventario c WHERE c.coinId = :coinId"),
    @NamedQuery(name = "ConfiguracionInventario.findByCoinDesglozarIvaCosto", query = "SELECT c FROM ConfiguracionInventario c WHERE c.coinDesglozarIvaCosto = :coinDesglozarIvaCosto"),
    @NamedQuery(name = "ConfiguracionInventario.findByCoinVariosLotes", query = "SELECT c FROM ConfiguracionInventario c WHERE c.coinVariosLotes = :coinVariosLotes"),
    @NamedQuery(name = "ConfiguracionInventario.findByCoinLifo", query = "SELECT c FROM ConfiguracionInventario c WHERE c.coinLifo = :coinLifo"),
    @NamedQuery(name = "ConfiguracionInventario.findByAlmaId", query = "SELECT c FROM ConfiguracionInventario c WHERE c.almaId = :almaId")})
public class ConfiguracionInventario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coin_id", nullable = false)
    private Integer coinId;
    @Column(name = "coin_desglozar_iva_costo")
    private Boolean coinDesglozarIvaCosto;
    @Column(name = "coin_varios_lotes")
    private Boolean coinVariosLotes;
    @Column(name = "coin_lifo")
    private Boolean coinLifo;
    @Column(name = "alma_id")
    private Integer almaId;

    public ConfiguracionInventario() {
    }

    public ConfiguracionInventario(Integer coinId) {
        this.coinId = coinId;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public Boolean getCoinDesglozarIvaCosto() {
        return coinDesglozarIvaCosto;
    }

    public void setCoinDesglozarIvaCosto(Boolean coinDesglozarIvaCosto) {
        this.coinDesglozarIvaCosto = coinDesglozarIvaCosto;
    }

    public Boolean getCoinVariosLotes() {
        return coinVariosLotes;
    }

    public void setCoinVariosLotes(Boolean coinVariosLotes) {
        this.coinVariosLotes = coinVariosLotes;
    }

    public Boolean getCoinLifo() {
        return coinLifo;
    }

    public void setCoinLifo(Boolean coinLifo) {
        this.coinLifo = coinLifo;
    }

    public Integer getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Integer almaId) {
        this.almaId = almaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coinId != null ? coinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfiguracionInventario)) {
            return false;
        }
        ConfiguracionInventario other = (ConfiguracionInventario) object;
        if ((this.coinId == null && other.coinId != null) || (this.coinId != null && !this.coinId.equals(other.coinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.ConfiguracionInventario[ coinId=" + coinId + " ]";
    }
    
}
