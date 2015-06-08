/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@Table(catalog = "dbfacturacion", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"alma_id", "clie_codigo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByAlmaId", query = "SELECT c FROM Cliente c WHERE c.clientePK.almaId = :almaId"),
    @NamedQuery(name = "Cliente.findByClieEstado", query = "SELECT c FROM Cliente c WHERE c.clieEstado = :clieEstado"),
    @NamedQuery(name = "Cliente.findByAlmaIdAndClieEstado", query = "SELECT c FROM Cliente c WHERE c.clientePK.almaId = :almaId and c.clieEstado = :clieEstado")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientePK clientePK;
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
    @JoinColumn(name = "alma_id", referencedColumnName = "alma_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Almacen almacen;

    public Cliente() {
    }

    public Cliente(ClientePK clientePK) {
        this.clientePK = clientePK;
    }

    public Cliente(int almaId, String clieCedula) {
        this.clientePK = new ClientePK(almaId, clieCedula);
    }

    public ClientePK getClientePK() {
        return clientePK;
    }

    public void setClientePK(ClientePK clientePK) {
        this.clientePK = clientePK;
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

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientePK != null ? clientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clientePK == null && other.clientePK != null) || (this.clientePK != null && !this.clientePK.equals(other.clientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.Cliente[ clientePK=" + clientePK + " ]";
    }
    
}
