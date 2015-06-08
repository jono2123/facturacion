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
@Table(name = "unidad_de_medida", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadDeMedida.findAll", query = "SELECT u FROM UnidadDeMedida u"),
    @NamedQuery(name = "UnidadDeMedida.findByUmedId", query = "SELECT u FROM UnidadDeMedida u WHERE u.umedId = :umedId"),
    @NamedQuery(name = "UnidadDeMedida.findByAlmaId", query = "SELECT u FROM UnidadDeMedida u WHERE u.almaId = :almaId"),
    @NamedQuery(name = "UnidadDeMedida.findByUmedNombre", query = "SELECT u FROM UnidadDeMedida u WHERE u.umedNombre = :umedNombre")})
public class UnidadDeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "umed_id", nullable = false)
    private Integer umedId;
    @Column(name = "alma_id")
    private Integer almaId;
    @Size(max = 20)
    @Column(name = "umed_nombre", length = 20)
    private String umedNombre;
    @OneToMany(mappedBy = "umedId")
    private List<Articulo> articuloList;

    public UnidadDeMedida() {
    }

    public UnidadDeMedida(Integer umedId) {
        this.umedId = umedId;
    }

    public Integer getUmedId() {
        return umedId;
    }

    public void setUmedId(Integer umedId) {
        this.umedId = umedId;
    }

    public Integer getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Integer almaId) {
        this.almaId = almaId;
    }

    public String getUmedNombre() {
        return umedNombre;
    }

    public void setUmedNombre(String umedNombre) {
        this.umedNombre = umedNombre;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (umedId != null ? umedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadDeMedida)) {
            return false;
        }
        UnidadDeMedida other = (UnidadDeMedida) object;
        if ((this.umedId == null && other.umedId != null) || (this.umedId != null && !this.umedId.equals(other.umedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.UnidadDeMedida[ umedId=" + umedId + " ]";
    }
    
}
