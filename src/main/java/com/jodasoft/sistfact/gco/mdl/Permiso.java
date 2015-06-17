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
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permiso.findByPermId", query = "SELECT p FROM Permiso p WHERE p.permId = :permId"),
    @NamedQuery(name = "Permiso.findByPermCrear", query = "SELECT p FROM Permiso p WHERE p.permCrear = :permCrear"),
    @NamedQuery(name = "Permiso.findByPermModificar", query = "SELECT p FROM Permiso p WHERE p.permModificar = :permModificar"),
    @NamedQuery(name = "Permiso.findByPermEliminar", query = "SELECT p FROM Permiso p WHERE p.permEliminar = :permEliminar"),
    @NamedQuery(name = "Permiso.findByPermConsultar", query = "SELECT p FROM Permiso p WHERE p.permConsultar = :permConsultar")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "perm_id", nullable = false)
    private Integer permId;
    @Column(name = "perm_crear")
    private Boolean permCrear;
    @Column(name = "perm_modificar")
    private Boolean permModificar;
    @Column(name = "perm_eliminar")
    private Boolean permEliminar;
    @Column(name = "perm_consultar")
    private Boolean permConsultar;
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    @ManyToOne
    private Rol rolId;
    @JoinColumn(name = "vent_id", referencedColumnName = "vent_id")
    @ManyToOne
    private Ventana ventId;

    public Permiso() {
    }

    public Permiso(Integer permId) {
        this.permId = permId;
    }

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public Boolean getPermCrear() {
        return permCrear;
    }

    public void setPermCrear(Boolean permCrear) {
        this.permCrear = permCrear;
    }

    public Boolean getPermModificar() {
        return permModificar;
    }

    public void setPermModificar(Boolean permModificar) {
        this.permModificar = permModificar;
    }

    public Boolean getPermEliminar() {
        return permEliminar;
    }

    public void setPermEliminar(Boolean permEliminar) {
        this.permEliminar = permEliminar;
    }

    public Boolean getPermConsultar() {
        return permConsultar;
    }

    public void setPermConsultar(Boolean permConsultar) {
        this.permConsultar = permConsultar;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    public Ventana getVentId() {
        return ventId;
    }

    public void setVentId(Ventana ventId) {
        this.ventId = ventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permId != null ? permId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.permId == null && other.permId != null) || (this.permId != null && !this.permId.equals(other.permId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Permiso[ permId=" + permId + " ]";
    }
    
}
