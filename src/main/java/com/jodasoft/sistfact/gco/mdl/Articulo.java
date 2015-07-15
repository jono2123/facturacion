/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import static javax.persistence.ParameterMode.IN;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javila
 */
@NamedStoredProcedureQuery(
    name = "findArticuloPrecio",
    resultClasses = Articulo.class,
    procedureName = "articulo_precios",
    parameters = {
        @StoredProcedureParameter(mode=IN, name="tipo_id", type=Integer.class),
        @StoredProcedureParameter(mode=IN, name="almaid", type=Integer.class)       
    }
)
@Entity
@Table(catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"arti_codigo", "alma_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a"),
    @NamedQuery(name = "Articulo.findByArtiId", query = "SELECT a FROM Articulo a WHERE a.artiId = :artiId"),
    @NamedQuery(name = "Articulo.findByArtiCodigo", query = "SELECT a FROM Articulo a WHERE a.artiCodigo = :artiCodigo"),
    @NamedQuery(name = "Articulo.findByArtiPrecioCompra", query = "SELECT a FROM Articulo a WHERE a.artiPrecioCompra = :artiPrecioCompra"),
    @NamedQuery(name = "Articulo.findByArtiPrecioVenta", query = "SELECT a FROM Articulo a WHERE a.artiPrecioVenta = :artiPrecioVenta"),
    @NamedQuery(name = "Articulo.findByArtiIva", query = "SELECT a FROM Articulo a WHERE a.artiIva = :artiIva"),
    @NamedQuery(name = "Articulo.findByArtiDescripcion", query = "SELECT a FROM Articulo a WHERE a.artiDescripcion = :artiDescripcion"),
    @NamedQuery(name = "Articulo.findByArtiInfoAdicional", query = "SELECT a FROM Articulo a WHERE a.artiInfoAdicional = :artiInfoAdicional"),
    @NamedQuery(name = "Articulo.findByAlmaIdAndArtiEstado", query = "SELECT a FROM Articulo a WHERE a.almaId = :almaId and a.artEstado = :estado"),
    @NamedQuery(name = "Articulo.findByArtiCodigoAlmaIdAndArtiEstado", query = "SELECT a FROM Articulo a WHERE a.artiCodigo = :artiCodigo and a.almaId = :almaId and a.artEstado = :estado"),
    @NamedQuery(name = "Articulo.findByArtEstado", query = "SELECT a FROM Articulo a WHERE a.artEstado = :artEstado")})
public class Articulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "arti_id", nullable = false)
    private Integer artiId;
    @Size(max = 30)
    @Column(name = "arti_codigo", length = 30)
    private String artiCodigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "arti_precio_compra", precision = 17, scale = 17)
    private Double artiPrecioCompra;
    @Column(name = "arti_precio_venta", precision = 17, scale = 17)
    private Double artiPrecioVenta;
    @Column(name = "arti_iva")
    private Boolean artiIva;
    @Size(max = 200)
    @Column(name = "arti_descripcion", length = 200)
    private String artiDescripcion;
    @Size(max = 200)
    @Column(name = "arti_info_adicional", length = 200)
    private String artiInfoAdicional;
    @Column(name = "art_estado")
    private Boolean artEstado;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id")
    @ManyToOne
    private Almacen almaId;
    @JoinColumn(name = "umed_id", referencedColumnName = "umed_id")
    @ManyToOne
    private UnidadDeMedida umedId;
   

    public Articulo() {
    }

    public Articulo(Integer artiId) {
        this.artiId = artiId;
    }

    public Integer getArtiId() {
        return artiId;
    }

    public void setArtiId(Integer artiId) {
        this.artiId = artiId;
    }

    public String getArtiCodigo() {
        return artiCodigo;
    }

    public void setArtiCodigo(String artiCodigo) {
        this.artiCodigo = artiCodigo;
    }

    public Double getArtiPrecioCompra() {
        return artiPrecioCompra;
    }

    public void setArtiPrecioCompra(Double artiPrecioCompra) {
        this.artiPrecioCompra = artiPrecioCompra;
    }

    public Double getArtiPrecioVenta() {
        return artiPrecioVenta;
    }

    public void setArtiPrecioVenta(Double artiPrecioVenta) {
        this.artiPrecioVenta = artiPrecioVenta;
    }

    public Boolean getArtiIva() {
        return artiIva;
    }

    public void setArtiIva(Boolean artiIva) {
        this.artiIva = artiIva;
    }

    public String getArtiDescripcion() {
        return artiDescripcion;
    }

    public void setArtiDescripcion(String artiDescripcion) {
        this.artiDescripcion = artiDescripcion;
    }

    public String getArtiInfoAdicional() {
        return artiInfoAdicional;
    }

    public void setArtiInfoAdicional(String artiInfoAdicional) {
        this.artiInfoAdicional = artiInfoAdicional;
    }

    public Boolean getArtEstado() {
        return artEstado;
    }

    public void setArtEstado(Boolean artEstado) {
        this.artEstado = artEstado;
    }

    

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    public UnidadDeMedida getUmedId() {
        return umedId;
    }

    public void setUmedId(UnidadDeMedida umedId) {
        this.umedId = umedId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artiId != null ? artiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.artiId == null && other.artiId != null) || (this.artiId != null && !this.artiId.equals(other.artiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Articulo[ artiId=" + artiId + " ]";
    }
    
}
