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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Cacheable(false)
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zona.findAll", query = "SELECT z FROM Zona z"),
    @NamedQuery(name = "Zona.findByZonaId", query = "SELECT z FROM Zona z WHERE z.zonaId = :zonaId"),
    @NamedQuery(name = "Zona.findByZonaNombre", query = "SELECT z FROM Zona z WHERE z.zonaNombre = :zonaNombre")})
public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zona_id", nullable = false)
    private Integer zonaId;
    @Size(max = 40)
    @Column(name = "zona_nombre", length = 40)
    private String zonaNombre;
    @JoinColumn(name = "ciud_id", referencedColumnName = "ciud_id")
    @ManyToOne
    private Ciudad ciudId;

    public Zona() {
    }

    public Zona(Integer zonaId) {
        this.zonaId = zonaId;
    }

    public Integer getZonaId() {
        return zonaId;
    }

    public void setZonaId(Integer zonaId) {
        this.zonaId = zonaId;
    }

    public String getZonaNombre() {
        return zonaNombre;
    }

    public void setZonaNombre(String zonaNombre) {
        this.zonaNombre = zonaNombre;
    }

    public Ciudad getCiudId() {
        return ciudId;
    }

    public void setCiudId(Ciudad ciudId) {
        this.ciudId = ciudId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zonaId != null ? zonaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.zonaId == null && other.zonaId != null) || (this.zonaId != null && !this.zonaId.equals(other.zonaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Zona[ zonaId=" + zonaId + " ]";
    }
    
}
