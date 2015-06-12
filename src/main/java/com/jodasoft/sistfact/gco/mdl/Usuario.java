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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"alma_id", "usua_id"}),
    @UniqueConstraint(columnNames = {"usua_nombre"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuaId", query = "SELECT u FROM Usuario u WHERE u.usuaId = :usuaId"),
    @NamedQuery(name = "Usuario.findByUsuaNombre", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre"),
    @NamedQuery(name = "Usuario.findByUsuaClave", query = "SELECT u FROM Usuario u WHERE u.usuaClave = :usuaClave"),
    @NamedQuery(name = "Usuario.findByUsuaRol", query = "SELECT u FROM Usuario u WHERE u.usuaRol = :usuaRol"),
    @NamedQuery(name = "Usuario.findByUsuaNombreAndUsuaClaveAndEstado", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre and u.usuaClave = :usuaClave and u.usuaEstado = :usuaEstado"),
    @NamedQuery(name = "Usuario.findByUsuaEstado", query = "SELECT u FROM Usuario u WHERE u.usuaEstado = :usuaEstado")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usua_id", nullable = false)
    private Integer usuaId;
    @Size(max = 30)
    @Column(name = "usua_nombre", length = 30)
    private String usuaNombre;
    @Size(max = 30)
    @Column(name = "usua_clave", length = 30)
    private String usuaClave;
    @Column(name = "usua_rol")
    private Integer usuaRol;
    @Column(name = "usua_estado")
    private Boolean usuaEstado;
   
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id", nullable = false)
    @ManyToOne(optional = false)
    private Almacen almaId;

    public Usuario() {
    }

    public Usuario(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public Integer getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public String getUsuaNombre() {
        return usuaNombre;
    }

    public void setUsuaNombre(String usuaNombre) {
        this.usuaNombre = usuaNombre;
    }

    public String getUsuaClave() {
        return usuaClave;
    }

    public void setUsuaClave(String usuaClave) {
        this.usuaClave = usuaClave;
    }

    public Integer getUsuaRol() {
        return usuaRol;
    }

    public void setUsuaRol(Integer usuaRol) {
        this.usuaRol = usuaRol;
    }

    public Boolean getUsuaEstado() {
        return usuaEstado;
    }

    public void setUsuaEstado(Boolean usuaEstado) {
        this.usuaEstado = usuaEstado;
    }

   

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuaId != null ? usuaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuaId == null && other.usuaId != null) || (this.usuaId != null && !this.usuaId.equals(other.usuaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Usuario[ usuaId=" + usuaId + " ]";
    }
    
}
