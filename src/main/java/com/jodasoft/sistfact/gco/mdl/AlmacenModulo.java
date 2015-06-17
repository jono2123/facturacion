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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Table(name = "almacen_modulo", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlmacenModulo.findAll", query = "SELECT a FROM AlmacenModulo a"),
    @NamedQuery(name = "AlmacenModulo.findByAlmoId", query = "SELECT a FROM AlmacenModulo a WHERE a.almoId = :almoId")})
public class AlmacenModulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "almo_id", nullable = false)
    private Integer almoId;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id")
    @ManyToOne
    private Almacen almaId;
    @JoinColumn(name = "modu_id", referencedColumnName = "modu_id")
    @ManyToOne
    private Modulo moduId;

    public AlmacenModulo() {
    }

    public AlmacenModulo(Integer almoId) {
        this.almoId = almoId;
    }

    public Integer getAlmoId() {
        return almoId;
    }

    public void setAlmoId(Integer almoId) {
        this.almoId = almoId;
    }

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    public Modulo getModuId() {
        return moduId;
    }

    public void setModuId(Modulo moduId) {
        this.moduId = moduId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (almoId != null ? almoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlmacenModulo)) {
            return false;
        }
        AlmacenModulo other = (AlmacenModulo) object;
        if ((this.almoId == null && other.almoId != null) || (this.almoId != null && !this.almoId.equals(other.almoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.AlmacenModulo[ almoId=" + almoId + " ]";
    }
    
}
