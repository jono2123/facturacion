/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findByAlmaId", query = "SELECT a FROM Almacen a WHERE a.almaId = :almaId"),
    @NamedQuery(name = "Almacen.findByAlmaNombre", query = "SELECT a FROM Almacen a WHERE a.almaNombre = :almaNombre"),
    @NamedQuery(name = "Almacen.findByAlmaDireccion", query = "SELECT a FROM Almacen a WHERE a.almaDireccion = :almaDireccion"),
    @NamedQuery(name = "Almacen.findByAlmaRuc", query = "SELECT a FROM Almacen a WHERE a.almaRuc = :almaRuc"),
    @NamedQuery(name = "Almacen.findByAlmaTelefono", query = "SELECT a FROM Almacen a WHERE a.almaTelefono = :almaTelefono")})
public class Almacen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "alma_id", nullable = false)
    private Integer almaId;
    @Size(max = 50)
    @Column(name = "alma_nombre", length = 50)
    private String almaNombre;
    @Size(max = 50)
    @Column(name = "alma_direccion", length = 50)
    private String almaDireccion;
    @Size(max = 13)
    @Column(name = "alma_ruc", length = 13)
    private String almaRuc;
    @Size(max = 15)
    @Column(name = "alma_telefono", length = 15)
    private String almaTelefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "almacen")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "almacen")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "almaId")
    private List<Articulo> articuloList;

    public Almacen() {
    }

    public Almacen(Integer almaId) {
        this.almaId = almaId;
    }

    public Integer getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Integer almaId) {
        this.almaId = almaId;
    }

    public String getAlmaNombre() {
        return almaNombre;
    }

    public void setAlmaNombre(String almaNombre) {
        this.almaNombre = almaNombre;
    }

    public String getAlmaDireccion() {
        return almaDireccion;
    }

    public void setAlmaDireccion(String almaDireccion) {
        this.almaDireccion = almaDireccion;
    }

    public String getAlmaRuc() {
        return almaRuc;
    }

    public void setAlmaRuc(String almaRuc) {
        this.almaRuc = almaRuc;
    }

    public String getAlmaTelefono() {
        return almaTelefono;
    }

    public void setAlmaTelefono(String almaTelefono) {
        this.almaTelefono = almaTelefono;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        hash += (almaId != null ? almaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.almaId == null && other.almaId != null) || (this.almaId != null && !this.almaId.equals(other.almaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Almacen[ almaId=" + almaId + " ]";
    }
    
}
