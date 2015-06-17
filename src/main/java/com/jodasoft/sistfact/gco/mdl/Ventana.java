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
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventana.findAll", query = "SELECT v FROM Ventana v"),
    @NamedQuery(name = "Ventana.findByVentId", query = "SELECT v FROM Ventana v WHERE v.ventId = :ventId"),
    @NamedQuery(name = "Ventana.findByVentUrl", query = "SELECT v FROM Ventana v WHERE v.ventUrl = :ventUrl"),
    @NamedQuery(name = "Ventana.findByVentNombre", query = "SELECT v FROM Ventana v WHERE v.ventNombre = :ventNombre")})
public class Ventana implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vent_id", nullable = false)
    private Integer ventId;
    @Size(max = 100)
    @Column(name = "vent_url", length = 100)
    private String ventUrl;
    @Size(max = 50)
    @Column(name = "vent_nombre", length = 50)
    private String ventNombre;
    @JoinColumn(name = "modu_id", referencedColumnName = "modu_id")
    @ManyToOne
    private Modulo moduId;
    @OneToMany(mappedBy = "ventId")
    private List<Permiso> permisoList;

    public Ventana() {
    }

    public Ventana(Integer ventId) {
        this.ventId = ventId;
    }

    public Integer getVentId() {
        return ventId;
    }

    public void setVentId(Integer ventId) {
        this.ventId = ventId;
    }

    public String getVentUrl() {
        return ventUrl;
    }

    public void setVentUrl(String ventUrl) {
        this.ventUrl = ventUrl;
    }

    public String getVentNombre() {
        return ventNombre;
    }

    public void setVentNombre(String ventNombre) {
        this.ventNombre = ventNombre;
    }

    public Modulo getModuId() {
        return moduId;
    }

    public void setModuId(Modulo moduId) {
        this.moduId = moduId;
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventId != null ? ventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventana)) {
            return false;
        }
        Ventana other = (Ventana) object;
        if ((this.ventId == null && other.ventId != null) || (this.ventId != null && !this.ventId.equals(other.ventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Ventana[ ventId=" + ventId + " ]";
    }
    
}
