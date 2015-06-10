/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByAlmaId", query = "SELECT u FROM Usuario u WHERE u.usuarioPK.almaId = :almaId"),
    @NamedQuery(name = "Usuario.findByUsuaId", query = "SELECT u FROM Usuario u WHERE u.usuarioPK.usuaId = :usuaId"),
    @NamedQuery(name = "Usuario.findByUsuaNombre", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre"),
    @NamedQuery(name = "Usuario.findByUsuaNombreAndUsuaClaveAndEstado", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre and u.usuaClave = :usuaClave and u.usuaEstado = :usuaEstado"),
    @NamedQuery(name = "Usuario.findByUsuaClave", query = "SELECT u FROM Usuario u WHERE u.usuaClave = :usuaClave"),
    @NamedQuery(name = "Usuario.findByUsuaRol", query = "SELECT u FROM Usuario u WHERE u.usuaRol = :usuaRol"),
    @NamedQuery(name = "Usuario.findByUsuaEstado", query = "SELECT u FROM Usuario u WHERE u.usuaEstado = :usuaEstado")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioPK usuarioPK;
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
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id", nullable = false, insertable = false, updatable = true)
    @ManyToOne(optional = false)
    private Almacen almacen;

    public Usuario() {
    }

    public Usuario(UsuarioPK usuarioPK) {
        this.usuarioPK = usuarioPK;
    }

    public Usuario(int almaId, int usuaId) {
        this.usuarioPK = new UsuarioPK(almaId, usuaId);
    }

    public UsuarioPK getUsuarioPK() {
        return usuarioPK;
    }

    public void setUsuarioPK(UsuarioPK usuarioPK) {
        this.usuarioPK = usuarioPK;
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

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioPK != null ? usuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioPK == null && other.usuarioPK != null) || (this.usuarioPK != null && !this.usuarioPK.equals(other.usuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Usuario[ usuarioPK=" + usuarioPK + " ]";
    }
    
}
