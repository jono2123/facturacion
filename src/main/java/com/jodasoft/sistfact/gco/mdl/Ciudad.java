/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnny
 */
@Entity
@Cacheable(false)
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCiudId", query = "SELECT c FROM Ciudad c WHERE c.ciudId = :ciudId"),
    @NamedQuery(name = "Ciudad.findByCiudNombre", query = "SELECT c FROM Ciudad c WHERE c.ciudNombre = :ciudNombre")})
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciud_id", nullable = false)
    private Integer ciudId;
    @Size(max = 35)
    @Column(name = "ciud_nombre", length = 35)
    private String ciudNombre;
    @OneToMany(mappedBy = "ciudId")
    private List<Zona> zonaList;
    @JoinColumn(name = "prov_id", referencedColumnName = "prov_id")
    @ManyToOne
    private Provincia provId;

    public Ciudad() {
    }

    public Ciudad(Integer ciudId) {
        this.ciudId = ciudId;
    }

    public Integer getCiudId() {
        return ciudId;
    }

    public void setCiudId(Integer ciudId) {
        this.ciudId = ciudId;
    }

    public String getCiudNombre() {
        return ciudNombre;
    }

    public void setCiudNombre(String ciudNombre) {
        this.ciudNombre = ciudNombre;
    }

    @XmlTransient
    public List<Zona> getZonaList() {
        return zonaList;
    }

    public void setZonaList(List<Zona> zonaList) {
        this.zonaList = zonaList;
    }

    public Provincia getProvId() {
        return provId;
    }

    public void setProvId(Provincia provId) {
        this.provId = provId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciudId != null ? ciudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciudId == null && other.ciudId != null) || (this.ciudId != null && !this.ciudId.equals(other.ciudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Ciudad[ ciudId=" + ciudId + " ]";
    }
    
}
