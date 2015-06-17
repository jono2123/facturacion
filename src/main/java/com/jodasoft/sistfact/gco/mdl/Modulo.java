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
@Table(catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findByModuId", query = "SELECT m FROM Modulo m WHERE m.moduId = :moduId"),
    @NamedQuery(name = "Modulo.findByModuNombre", query = "SELECT m FROM Modulo m WHERE m.moduNombre = :moduNombre"),
    @NamedQuery(name = "Modulo.findByModuValor", query = "SELECT m FROM Modulo m WHERE m.moduValor = :moduValor"),
    @NamedQuery(name = "Modulo.findByModuValorTransaccion", query = "SELECT m FROM Modulo m WHERE m.moduValorTransaccion = :moduValorTransaccion")})
public class Modulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "modu_id", nullable = false)
    private Integer moduId;
    @Size(max = 30)
    @Column(name = "modu_nombre", length = 30)
    private String moduNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "modu_valor", precision = 17, scale = 17)
    private Double moduValor;
    @Column(name = "modu_valor_transaccion", precision = 17, scale = 17)
    private Double moduValorTransaccion;
    @OneToMany(mappedBy = "moduId")
    private List<Ventana> ventanaList;
    @OneToMany(mappedBy = "moduId")
    private List<AlmacenModulo> almacenModuloList;

    public Modulo() {
    }

    public Modulo(Integer moduId) {
        this.moduId = moduId;
    }

    public Integer getModuId() {
        return moduId;
    }

    public void setModuId(Integer moduId) {
        this.moduId = moduId;
    }

    public String getModuNombre() {
        return moduNombre;
    }

    public void setModuNombre(String moduNombre) {
        this.moduNombre = moduNombre;
    }

    public Double getModuValor() {
        return moduValor;
    }

    public void setModuValor(Double moduValor) {
        this.moduValor = moduValor;
    }

    public Double getModuValorTransaccion() {
        return moduValorTransaccion;
    }

    public void setModuValorTransaccion(Double moduValorTransaccion) {
        this.moduValorTransaccion = moduValorTransaccion;
    }

    @XmlTransient
    public List<Ventana> getVentanaList() {
        return ventanaList;
    }

    public void setVentanaList(List<Ventana> ventanaList) {
        this.ventanaList = ventanaList;
    }

    @XmlTransient
    public List<AlmacenModulo> getAlmacenModuloList() {
        return almacenModuloList;
    }

    public void setAlmacenModuloList(List<AlmacenModulo> almacenModuloList) {
        this.almacenModuloList = almacenModuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduId != null ? moduId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.moduId == null && other.moduId != null) || (this.moduId != null && !this.moduId.equals(other.moduId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Modulo[ moduId=" + moduId + " ]";
    }
    
}
