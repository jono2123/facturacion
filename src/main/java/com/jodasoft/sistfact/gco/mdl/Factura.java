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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @UniqueConstraint(columnNames = {"fact_numero", "alma_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByFactNumero", query = "SELECT f FROM Factura f WHERE f.factNumero = :factNumero"),
    @NamedQuery(name = "Factura.findByFactNunAutorizacion", query = "SELECT f FROM Factura f WHERE f.factNunAutorizacion = :factNunAutorizacion"),
    @NamedQuery(name = "Factura.findByFactClaveAcceso", query = "SELECT f FROM Factura f WHERE f.factClaveAcceso = :factClaveAcceso"),
    @NamedQuery(name = "Factura.findByFactFechaHoraAutorizacion", query = "SELECT f FROM Factura f WHERE f.factFechaHoraAutorizacion = :factFechaHoraAutorizacion"),
    @NamedQuery(name = "Factura.findByFactSubtotalSinIva", query = "SELECT f FROM Factura f WHERE f.factSubtotalSinIva = :factSubtotalSinIva"),
    @NamedQuery(name = "Factura.findByFactSubtotalConIva", query = "SELECT f FROM Factura f WHERE f.factSubtotalConIva = :factSubtotalConIva"),
    @NamedQuery(name = "Factura.findByFactIva", query = "SELECT f FROM Factura f WHERE f.factIva = :factIva"),
    @NamedQuery(name = "Factura.findByFactDescuento", query = "SELECT f FROM Factura f WHERE f.factDescuento = :factDescuento"),
    @NamedQuery(name = "Factura.findByFactTotal", query = "SELECT f FROM Factura f WHERE f.factTotal = :factTotal"),
    @NamedQuery(name = "Factura.findByFactFecha", query = "SELECT f FROM Factura f WHERE f.factFecha = :factFecha"),
    @NamedQuery(name = "Factura.findByFactId", query = "SELECT f FROM Factura f WHERE f.factId = :factId")})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "fact_numero")
    private Integer factNumero;
    @Size(max = 50)
    @Column(name = "fact_nun_autorizacion", length = 50)
    private String factNunAutorizacion;
    @Size(max = 50)
    @Column(name = "fact_clave_acceso", length = 50)
    private String factClaveAcceso;
    @Size(max = 20)
    @Column(name = "fact_fecha_hora_autorizacion", length = 20)
    private String factFechaHoraAutorizacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fact_subtotal_sin_iva", precision = 17, scale = 17)
    private Double factSubtotalSinIva;
    @Column(name = "fact_subtotal_con_iva", precision = 17, scale = 17)
    private Double factSubtotalConIva;
    @Column(name = "fact_iva", precision = 17, scale = 17)
    private Double factIva;
    @Column(name = "fact_descuento", precision = 17, scale = 17)
    private Double factDescuento;
    @Column(name = "fact_total", precision = 17, scale = 17)
    private Double factTotal;
    @Column(name = "fact_fecha")
    @Temporal(TemporalType.DATE)
    private Date factFecha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fact_id", nullable = false)
    private Integer factId;
    @OneToMany(mappedBy = "factId", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFacturaList;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id")
    @ManyToOne
    private Almacen almaId;
    @JoinColumn(name = "clie_id", referencedColumnName = "pers_id")
    @ManyToOne
    private Persona clieId;
    @JoinColumn(name = "usua_id", referencedColumnName = "usua_id")
    @ManyToOne
    private Usuario usuaId;

    public Factura() {
    }

    public Factura(Integer factId) {
        this.factId = factId;
    }

    public Integer getFactNumero() {
        return factNumero;
    }

    public void setFactNumero(Integer factNumero) {
        this.factNumero = factNumero;
    }

    public String getFactNunAutorizacion() {
        return factNunAutorizacion;
    }

    public void setFactNunAutorizacion(String factNunAutorizacion) {
        this.factNunAutorizacion = factNunAutorizacion;
    }

    public String getFactClaveAcceso() {
        return factClaveAcceso;
    }

    public void setFactClaveAcceso(String factClaveAcceso) {
        this.factClaveAcceso = factClaveAcceso;
    }

    public String getFactFechaHoraAutorizacion() {
        return factFechaHoraAutorizacion;
    }

    public void setFactFechaHoraAutorizacion(String factFechaHoraAutorizacion) {
        this.factFechaHoraAutorizacion = factFechaHoraAutorizacion;
    }

    public Double getFactSubtotalSinIva() {
        return factSubtotalSinIva;
    }

    public void setFactSubtotalSinIva(Double factSubtotalSinIva) {
        this.factSubtotalSinIva = factSubtotalSinIva;
    }

    public Double getFactSubtotalConIva() {
        return factSubtotalConIva;
    }

    public void setFactSubtotalConIva(Double factSubtotalConIva) {
        this.factSubtotalConIva = factSubtotalConIva;
    }

    public Double getFactIva() {
        return factIva;
    }

    public void setFactIva(Double factIva) {
        this.factIva = factIva;
    }

    public Double getFactDescuento() {
        return factDescuento;
    }

    public void setFactDescuento(Double factDescuento) {
        this.factDescuento = factDescuento;
    }

    public Double getFactTotal() {
        return factTotal;
    }

    public void setFactTotal(Double factTotal) {
        this.factTotal = factTotal;
    }

    public Date getFactFecha() {
        return factFecha;
    }

    public void setFactFecha(Date factFecha) {
        this.factFecha = factFecha;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }

    @XmlTransient
    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    public Persona getClieId() {
        return clieId;
    }

    public void setClieId(Persona clieId) {
        this.clieId = clieId;
    }

    public Usuario getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Usuario usuaId) {
        this.usuaId = usuaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factId != null ? factId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.factId == null && other.factId != null) || (this.factId != null && !this.factId.equals(other.factId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Factura[ factId=" + factId + " ]";
    }
    
}
