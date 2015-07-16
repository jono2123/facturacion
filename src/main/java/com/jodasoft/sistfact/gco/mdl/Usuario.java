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
    @UniqueConstraint(columnNames = {"usua_nombre"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuaId", query = "SELECT u FROM Usuario u WHERE u.usuaId = :usuaId"),
    @NamedQuery(name = "Usuario.findByUsuaNombre", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre"),
    @NamedQuery(name = "Usuario.findByUsuaClave", query = "SELECT u FROM Usuario u WHERE u.usuaClave = :usuaClave"),
    @NamedQuery(name = "Usuario.findByUsuaNombreAndUsuaClaveAndEstado", query = "SELECT u FROM Usuario u WHERE u.usuaNombre = :usuaNombre and u.usuaClave = :usuaClave and u.usuaEstado = :usuaEstado"),
    @NamedQuery(name = "Usuario.findByUsuaEstadoAndUsuaAlmaId", query = "SELECT u FROM Usuario u WHERE u.usuaEstado = :usuaEstado and u.rolId.almaId = :almaId")})
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
    @Size(max = 100)
    @Column(name = "usua_clave", length = 100)
    private String usuaClave;
    @Column(name = "usua_estado")
    private Boolean usuaEstado;
    @OneToMany(mappedBy = "usuaId")
    private List<Factura> facturaList;
    @OneToMany(mappedBy = "usuaId")
    private List<AlmacenTransaccion> almacenTransaccionList;
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    @ManyToOne
    private Rol rolId;

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

    public Boolean getUsuaEstado() {
        return usuaEstado;
    }

    public void setUsuaEstado(Boolean usuaEstado) {
        this.usuaEstado = usuaEstado;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }
    @XmlTransient
    public List<AlmacenTransaccion> getAlmacenTransaccionList() {
        return almacenTransaccionList;
    }

    public void setAlmacenTransaccionList(List<AlmacenTransaccion> almacenTransaccionList) {
        this.almacenTransaccionList = almacenTransaccionList;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
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
