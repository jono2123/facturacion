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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"alma_id", "pers_codigo"}),
    @UniqueConstraint(columnNames = {"dtype","pers_cedula", "alma_id"})})
@XmlRootElement

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "pers_cedula", nullable = false, length = 13)
    private String persCedula;
    @Size(max = 50)
    @Column(name = "pers_nombres", length = 50)
    private String persNombres;
    @Size(max = 50)
    @Column(name = "pers_direccion", length = 50)
    private String persDireccion;
    @Size(max = 40)
    @Column(name = "pers_telefono", length = 40)
    private String persTelefono;
    @Size(max = 60)
    @Column(name = "pers_mail", length = 60)
    private String persMail;
    @Column(name = "pers_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date persFechaNacimiento;
    @Column(name = "pers_estado")
    private Boolean persEstado;
    @Size(max = 50)
    @Column(name = "pers_apellidos", length = 50)
    private String persApellidos;
    @Size(max = 15)
    @Column(name = "pers_codigo", length = 15)
    private String persCodigo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pers_id", nullable = false)
    private Integer persId;
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id", nullable = false)
    @ManyToOne(optional = false)
    private Almacen almaId;
    @JoinColumn(name = "zona_id", referencedColumnName = "zona_id", nullable = true)
    @ManyToOne(optional = true)
    private Zona zonaId;
   

    public Persona() {
    }

    public Persona(Integer persId) {
        this.persId = persId;
    }

    public Persona(Integer persId, String persCedula) {
        this.persId = persId;
        this.persCedula = persCedula;
    }

    public String getPersCedula() {
        return persCedula;
    }

    public void setPersCedula(String persCedula) {
        this.persCedula = persCedula;
    }

    public String getPersNombres() {
        return persNombres;
    }

    public void setPersNombres(String persNombres) {
        this.persNombres = persNombres;
    }

    public String getPersDireccion() {
        return persDireccion;
    }

    public void setPersDireccion(String persDireccion) {
        this.persDireccion = persDireccion;
    }

    public String getPersTelefono() {
        return persTelefono;
    }

    public void setPersTelefono(String persTelefono) {
        this.persTelefono = persTelefono;
    }

    public String getPersMail() {
        return persMail;
    }

    public void setPersMail(String persMail) {
        this.persMail = persMail;
    }

    public Date getPersFechaNacimiento() {
        return persFechaNacimiento;
    }

    public void setPersFechaNacimiento(Date persFechaNacimiento) {
        this.persFechaNacimiento = persFechaNacimiento;
    }

    public Boolean getPersEstado() {
        return persEstado;
    }

    public void setPersEstado(Boolean persEstado) {
        this.persEstado = persEstado;
    }

    public String getPersApellidos() {
        return persApellidos;
    }

    public void setPersApellidos(String persApellidos) {
        this.persApellidos = persApellidos;
    }

    public String getPersCodigo() {
        return persCodigo;
    }

    public void setPersCodigo(String persCodigo) {
        this.persCodigo = persCodigo;
    }

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public Almacen getAlmaId() {
        return almaId;
    }

    public void setAlmaId(Almacen almaId) {
        this.almaId = almaId;
    }

    public Zona getZonaId() {
        return zonaId;
    }

    public void setZonaId(Zona zonaId) {
        this.zonaId = zonaId;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (persId != null ? persId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.persId == null && other.persId != null) || (this.persId != null && !this.persId.equals(other.persId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Persona[ persId=" + persId + " ]";
    }
    
}
