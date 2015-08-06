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
@Table(name = "tipo_articulo", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoArticulo.findAll", query = "SELECT t FROM TipoArticulo t"),
    @NamedQuery(name = "TipoArticulo.findByTiarId", query = "SELECT t FROM TipoArticulo t WHERE t.tiarId = :tiarId"),
    @NamedQuery(name = "TipoArticulo.findByTiarHoja", query = "SELECT t FROM TipoArticulo t WHERE t.tiarHoja = :tiarHoja"),
    @NamedQuery(name = "TipoArticulo.findByTiarNivel", query = "SELECT t FROM TipoArticulo t WHERE t.tiarNivel = :tiarNivel"),
    @NamedQuery(name = "TipoArticulo.findByTiarPadre", query = "SELECT t FROM TipoArticulo t WHERE t.tiarPadre = :tiarPadre and t.tiarEstado = :tiarEstado"),
    @NamedQuery(name = "TipoArticulo.findAlmaIdAndNivel0", query = "SELECT t FROM TipoArticulo t WHERE t.almaId = :almaId and t.tiarPadre is null and t.tiarEstado = :tiarEstado"),
    @NamedQuery(name = "TipoArticulo.findByTiarNombre", query = "SELECT t FROM TipoArticulo t WHERE t.tiarNombre = :tiarNombre")})
public class TipoArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tiar_id", nullable = false)
    private Integer tiarId;
    @Column(name = "tiar_hoja")
    private Boolean tiarHoja;
    @Column(name = "tiar_nivel")
    private Integer tiarNivel;
    @Column(name = "tiar_padre")
    private Integer tiarPadre;
    @Size(max = 50)
    @Column(name = "tiar_nombre", length = 50)
    private String tiarNombre;
    @Column(name = "tiar_estado")
    private Boolean tiarEstado;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id")
    @ManyToOne
    private Almacen almaId;

    public TipoArticulo() {
    }

    public TipoArticulo(Integer tiarId) {
        this.tiarId = tiarId;
    }

    public Integer getTiarId() {
        return tiarId;
    }

    public void setTiarId(Integer tiarId) {
        this.tiarId = tiarId;
    }

    public Boolean getTiarHoja() {
        return tiarHoja;
    }

    public void setTiarHoja(Boolean tiarHoja) {
        this.tiarHoja = tiarHoja;
    }

    public Integer getTiarNivel() {
        return tiarNivel;
    }

    public void setTiarNivel(Integer tiarNivel) {
        this.tiarNivel = tiarNivel;
    }

    public Integer getTiarPadre() {
        return tiarPadre;
    }

    public void setTiarPadre(Integer tiarPadre) {
        this.tiarPadre = tiarPadre;
    }

    public String getTiarNombre() {
        return tiarNombre;
    }

    public void setTiarNombre(String tiarNombre) {
        this.tiarNombre = tiarNombre;
    }

    public Boolean getTiarEstado() {
        return tiarEstado;
    }

    public void setTiarEstado(Boolean tiarEstado) {
        this.tiarEstado = tiarEstado;
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
        hash += (tiarId != null ? tiarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoArticulo)) {
            return false;
        }
        TipoArticulo other = (TipoArticulo) object;
        if ((this.tiarId == null && other.tiarId != null) || (this.tiarId != null && !this.tiarId.equals(other.tiarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.TipoArticulo[ tiarId=" + tiarId + " ]";
    }
    
}
