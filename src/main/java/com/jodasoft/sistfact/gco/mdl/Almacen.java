/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findByAlmaId", query = "SELECT a FROM Almacen a WHERE a.almaId = :almaId"),
    @NamedQuery(name = "Almacen.findByAlmaNombre", query = "SELECT a FROM Almacen a WHERE a.almaNombre = :almaNombre"),
    @NamedQuery(name = "Almacen.findByAlmaDireccion", query = "SELECT a FROM Almacen a WHERE a.almaDireccion = :almaDireccion"),
    @NamedQuery(name = "Almacen.findByAlmaRuc", query = "SELECT a FROM Almacen a WHERE a.almaRuc = :almaRuc"),
    @NamedQuery(name = "Almacen.findByAlmaTelefono", query = "SELECT a FROM Almacen a WHERE a.almaTelefono = :almaTelefono"),
    @NamedQuery(name = "Almacen.findByAlmaEstado", query = "SELECT a FROM Almacen a WHERE a.almaEstado = :almaEstado"),
    @NamedQuery(name = "Almacen.findByAlmaIva", query = "SELECT a FROM Almacen a WHERE a.almaIva = :almaIva"),
    @NamedQuery(name = "Almacen.findByAlmaDireccionServidor", query = "SELECT a FROM Almacen a WHERE a.almaDireccionServidor = :almaDireccionServidor"),
    @NamedQuery(name = "Almacen.findByAlmaPropietario", query = "SELECT a FROM Almacen a WHERE a.almaPropietario = :almaPropietario"),
    @NamedQuery(name = "Almacen.findByAlmaFacturaAutonumerada", query = "SELECT a FROM Almacen a WHERE a.almaFacturaAutonumerada = :almaFacturaAutonumerada"),
    @NamedQuery(name = "Almacen.findByAlmaNumFactura", query = "SELECT a FROM Almacen a WHERE a.almaNumFactura = :almaNumFactura")})
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
    @Size(max = 30)
    @Column(name = "alma_telefono", length = 30)
    private String almaTelefono;
    @Column(name = "alma_estado")
    private Integer almaEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "alma_iva", precision = 17, scale = 17)
    private Double almaIva;
    @Size(max = 80)
    @Column(name = "alma_direccion_servidor", length = 80)
    private String almaDireccionServidor;
    @Size(max = 50)
    @Column(name = "alma_propietario", length = 50)
    private String almaPropietario;
    @Column(name = "alma_factura_autonumerada")
    private Boolean almaFacturaAutonumerada;
    @Column(name = "alma_num_factura")
    private Integer almaNumFactura;
    @Column(name="alma_coma")
    private Boolean almaComa;
    @Column(name="alma_desglozar")
    private Boolean almaDesglozar;
    @Column(name="alma_diferenciar_precios")
    private Boolean almaDiferenciarPrecios;
    @Column(name="alma_fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date almaFechaCreacion;
    @Column(name="alma_dia_corte")
    private Integer almaDiaCorte;
    @Size(max = 100)
    @Column(name = "alma_email", length = 100)
    private String almaEmail;
    @Column(name = "alma_metodo_pago")
    private Integer almaMetodoPago;
    @Column(name = "alma_meses_vencidos")
    private Integer almaMesesVencidos;
    @Column(name = "alma_mes_contado")
    private Boolean almaMesContado;
    @Column(name = "alma_num_copias")
    private Integer almaNumCopias;
    
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

    public Integer getAlmaEstado() {
        return almaEstado;
    }

    public void setAlmaEstado(Integer almaEstado) {
        this.almaEstado = almaEstado;
    }

    public Double getAlmaIva() {
        return almaIva;
    }

    public void setAlmaIva(Double almaIva) {
        this.almaIva = almaIva;
    }

    public String getAlmaDireccionServidor() {
        return almaDireccionServidor;
    }

    public void setAlmaDireccionServidor(String almaDireccionServidor) {
        this.almaDireccionServidor = almaDireccionServidor;
    }

    public String getAlmaPropietario() {
        return almaPropietario;
    }

    public void setAlmaPropietario(String almaPropietario) {
        this.almaPropietario = almaPropietario;
    }

    public Boolean getAlmaFacturaAutonumerada() {
        return almaFacturaAutonumerada;
    }

    public void setAlmaFacturaAutonumerada(Boolean almaFacturaAutonumerada) {
        this.almaFacturaAutonumerada = almaFacturaAutonumerada;
    }

    public Integer getAlmaNumFactura() {
        return almaNumFactura;
    }

    public void setAlmaNumFactura(Integer almaNumFactura) {
        this.almaNumFactura = almaNumFactura;
    }

    public Boolean getAlmaComa() {
        return almaComa;
    }

    public void setAlmaComa(Boolean almaComa) {
        this.almaComa = almaComa;
    }

    public Boolean getAlmaDesglozar() {
        return almaDesglozar;
    }

    public void setAlmaDesglozar(Boolean almaDesglozar) {
        this.almaDesglozar = almaDesglozar;
    }

    public Boolean getAlmaDiferenciarPrecios() {
        return almaDiferenciarPrecios;
    }

    public void setAlmaDiferenciarPrecios(Boolean almaDiferenciarPrecios) {
        this.almaDiferenciarPrecios = almaDiferenciarPrecios;
    }

    public Date getAlmaFechaCreacion() {
        return almaFechaCreacion;
    }

    public void setAlmaFechaCreacion(Date almaFechaCreacion) {
        this.almaFechaCreacion = almaFechaCreacion;
    }

    public Integer getAlmaDiaCorte() {
        return almaDiaCorte;
    }

    public void setAlmaDiaCorte(Integer almaDiaCorte) {
        this.almaDiaCorte = almaDiaCorte;
    }

    public String getAlmaEmail() {
        return almaEmail;
    }

    public void setAlmaEmail(String almaEmail) {
        this.almaEmail = almaEmail;
    }

    public Integer getAlmaMetodoPago() {
        return almaMetodoPago;
    }

    public void setAlmaMetodoPago(Integer almaMetodoPago) {
        this.almaMetodoPago = almaMetodoPago;
    }

    public Integer getAlmaMesesVencidos() {
        return almaMesesVencidos;
    }

    public void setAlmaMesesVencidos(Integer almaMesesVencidos) {
        this.almaMesesVencidos = almaMesesVencidos;
    }

    public Boolean getAlmaMesContado() {
        return almaMesContado;
    }

    public void setAlmaMesContado(Boolean almaMesContado) {
        this.almaMesContado = almaMesContado;
    }

    public Integer getAlmaNumCopias() {
        return almaNumCopias;
    }

    public void setAlmaNumCopias(Integer almaNumCopias) {
        this.almaNumCopias = almaNumCopias;
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
