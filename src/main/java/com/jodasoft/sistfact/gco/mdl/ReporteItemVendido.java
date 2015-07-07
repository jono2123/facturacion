/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Cacheable(false)
@Table(name = "reporte_item_vendido", catalog = "dbfacturacion", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReporteItemVendido.findAll", query = "SELECT r FROM ReporteItemVendido r"),
    @NamedQuery(name = "ReporteItemVendido.findByAlmaId", query = "SELECT r FROM ReporteItemVendido r WHERE r.almaId = :almaId"),
    @NamedQuery(name = "ReporteItemVendido.findByArtiId", query = "SELECT r FROM ReporteItemVendido r WHERE r.artiId = :artiId"),
    @NamedQuery(name = "ReporteItemVendido.findByArtiCantidadVendida", query = "SELECT r FROM ReporteItemVendido r WHERE r.artiCantidadVendida = :artiCantidadVendida")})
public class ReporteItemVendido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "repo_id")
    private BigInteger repoId;
    @Column(name = "alma_id")
    private Integer almaId;
    @Column(name = "arti_id")
    private Integer artiId;
    @Size(max = 30)
    @Column(name = "arti_codigo", length = 30)
    private String artiCodigo;
    @Size(max = 200)
    @Column(name = "arti_descripcion", length = 200)
    private String artiDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "arti_precio_compra", precision = 17, scale = 17)
    private Double artiPrecioCompra;
    @Column(name = "arti_precio_venta_promedio", precision = 17, scale = 17)
    private Double artiPrecioVentaPromedio;
    @Column(name = "arti_cantidad_vendida", precision = 17, scale = 17)
    private Double artiCantidadVendida;

    public ReporteItemVendido() {
    }

    public BigInteger getRepoId() {
        return repoId;
    }

    public void setRepoId(BigInteger repoId) {
        this.repoId = repoId;
    }

    public Integer getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Integer almaId) {
        this.almaId = almaId;
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

    public String getArtiDescripcion() {
        return artiDescripcion;
    }

    public void setArtiDescripcion(String artiDescripcion) {
        this.artiDescripcion = artiDescripcion;
    }

    public Double getArtiPrecioCompra() {
        return artiPrecioCompra;
    }

    public void setArtiPrecioCompra(Double artiPrecioCompra) {
        this.artiPrecioCompra = artiPrecioCompra;
    }

    public Double getArtiPrecioVentaPromedio() {
        return artiPrecioVentaPromedio;
    }

    public void setArtiPrecioVentaPromedio(Double artiPrecioVentaPromedio) {
        this.artiPrecioVentaPromedio = artiPrecioVentaPromedio;
    }

    public Double getArtiCantidadVendida() {
        return artiCantidadVendida;
    }

    public void setArtiCantidadVendida(Double artiCantidadVendida) {
        this.artiCantidadVendida = artiCantidadVendida;
    }
    
}
