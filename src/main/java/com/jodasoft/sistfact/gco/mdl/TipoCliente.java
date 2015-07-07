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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johnny
 */
@Entity
@Table(name = "tipo_cliente", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCliente.findAll", query = "SELECT t FROM TipoCliente t"),
    @NamedQuery(name = "TipoCliente.findByTiclId", query = "SELECT t FROM TipoCliente t WHERE t.ticlId = :ticlId"),
    @NamedQuery(name = "TipoCliente.findByAlmaIdAndTiclEstado", query = "SELECT t FROM TipoCliente t WHERE t.almaId = :almaId and t.ticlEstado = :ticlEstado"),
    @NamedQuery(name = "TipoCliente.findByTiclDescripcion", query = "SELECT t FROM TipoCliente t WHERE t.ticlDescripcion = :ticlDescripcion")})
public class TipoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticl_id", nullable = false)
    private Integer ticlId;
    @Size(max = 25)
    @Column(name = "ticl_descripcion", length = 25)
    private String ticlDescripcion;
    @Column(name = "ticl_estado")
    private Boolean ticlEstado;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id")
    @ManyToOne
    private Almacen almaId;

    public TipoCliente() {
    }

    public TipoCliente(Integer ticlId) {
        this.ticlId = ticlId;
    }

    public Integer getTiclId() {
        return ticlId;
    }

    public void setTiclId(Integer ticlId) {
        this.ticlId = ticlId;
    }

    public String getTiclDescripcion() {
        return ticlDescripcion;
    }

    public void setTiclDescripcion(String ticlDescripcion) {
        this.ticlDescripcion = ticlDescripcion;
    }

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    public Boolean getTiclEstado() {
        return ticlEstado;
    }

    public void setTiclEstado(Boolean ticlEstado) {
        this.ticlEstado = ticlEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticlId != null ? ticlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCliente)) {
            return false;
        }
        TipoCliente other = (TipoCliente) object;
        if ((this.ticlId == null && other.ticlId != null) || (this.ticlId != null && !this.ticlId.equals(other.ticlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.TipoCliente[ ticlId=" + ticlId + " ]";
    }
    
}
