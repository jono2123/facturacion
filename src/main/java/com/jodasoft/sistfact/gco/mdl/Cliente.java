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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"clie_cedula", "alma_id"}),
    @UniqueConstraint(columnNames = {"alma_id", "clie_codigo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByAlmaId", query = "SELECT c FROM Cliente c WHERE c.almaId = :almaId"),
    @NamedQuery(name = "Cliente.findByClieEstado", query = "SELECT c FROM Cliente c WHERE c.clieEstado = :clieEstado"),
    @NamedQuery(name = "Cliente.findByClieCedulaAndClieAlmacenAndEstado", query = "SELECT c FROM Cliente c WHERE c.clieCedula = :clieCedula and c.almaId = :almaId and c.clieEstado = :clieEstado"),
    @NamedQuery(name = "Cliente.findByAlmaIdAndClieEstado", query = "SELECT c FROM Cliente c WHERE c.almaId = :almaId and c.clieEstado = :clieEstado")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "clie_cedula", nullable = false, length = 13)
    private String clieCedula;
    @Size(max = 50)
    @Column(name = "clie_nombres", length = 50)
    private String clieNombres;
    @Size(max = 50)
    @Column(name = "clie_direccion", length = 50)
    private String clieDireccion;
    @Size(max = 15)
    @Column(name = "clie_telefono", length = 15)
    private String clieTelefono;
    @Size(max = 60)
    @Column(name = "clie_mail", length = 60)
    private String clieMail;
    @Column(name = "clie_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date clieFechaNacimiento;
    @Column(name = "clie_estado")
    private Boolean clieEstado;
    @Size(max = 50)
    @Column(name = "clie_apellidos", length = 50)
    private String clieApellidos;
    @Size(max = 15)
    @Column(name = "clie_codigo", length = 15)
    private String clieCodigo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clie_id", nullable = false)
    private Integer clieId;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id", nullable = false)
    @ManyToOne(optional = false)
    private Almacen almaId;
   

    public Cliente() {
    }

    public Cliente(Integer clieId) {
        this.clieId = clieId;
    }

    public Cliente(Integer clieId, String clieCedula) {
        this.clieId = clieId;
        this.clieCedula = clieCedula;
    }

    public String getClieCedula() {
        return clieCedula;
    }

    public void setClieCedula(String clieCedula) {
        this.clieCedula = clieCedula;
    }

    public String getClieNombres() {
        return clieNombres;
    }

    public void setClieNombres(String clieNombres) {
        this.clieNombres = clieNombres;
    }

    public String getClieDireccion() {
        return clieDireccion;
    }

    public void setClieDireccion(String clieDireccion) {
        this.clieDireccion = clieDireccion;
    }

    public String getClieTelefono() {
        return clieTelefono;
    }

    public void setClieTelefono(String clieTelefono) {
        this.clieTelefono = clieTelefono;
    }

    public String getClieMail() {
        return clieMail;
    }

    public void setClieMail(String clieMail) {
        this.clieMail = clieMail;
    }

    public Date getClieFechaNacimiento() {
        return clieFechaNacimiento;
    }

    public void setClieFechaNacimiento(Date clieFechaNacimiento) {
        this.clieFechaNacimiento = clieFechaNacimiento;
    }

    public Boolean getClieEstado() {
        return clieEstado;
    }

    public void setClieEstado(Boolean clieEstado) {
        this.clieEstado = clieEstado;
    }

    public String getClieApellidos() {
        return clieApellidos;
    }

    public void setClieApellidos(String clieApellidos) {
        this.clieApellidos = clieApellidos;
    }

    public String getClieCodigo() {
        return clieCodigo;
    }

    public void setClieCodigo(String clieCodigo) {
        this.clieCodigo = clieCodigo;
    }

    public Integer getClieId() {
        return clieId;
    }

    public void setClieId(Integer clieId) {
        this.clieId = clieId;
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
        hash += (clieId != null ? clieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clieId == null && other.clieId != null) || (this.clieId != null && !this.clieId.equals(other.clieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Cliente[ clieId=" + clieId + " ]";
    }
    
}
