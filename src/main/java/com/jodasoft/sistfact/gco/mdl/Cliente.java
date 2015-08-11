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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author javila
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "pers_id")
@Table(catalog = "dbfacturacion", schema = "public")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByAlmaId", query = "SELECT c FROM Cliente c WHERE c.almaId = :almaId"),
    @NamedQuery(name = "Cliente.findByClieEstado", query = "SELECT c FROM Cliente c WHERE c.clieEstado = :clieEstado"),
    @NamedQuery(name = "Cliente.findByClieCedulaAndClieAlmacenAndEstado", query = "SELECT c FROM Cliente c WHERE c.persCedula = :clieCedula and c.almaId = :almaId and c.clieEstado = :clieEstado"),
    @NamedQuery(name = "Cliente.findByAlmaIdAndClieEstado", query = "SELECT c FROM Cliente c WHERE c.almaId = :almaId and c.clieEstado = :clieEstado order by c.persApellidos")})
@XmlRootElement
public class Cliente extends  Persona implements Serializable  {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "clie_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date clieFechaRegistro;
    @Column(name = "clie_credito")
    private Boolean clieCredito;
    @Column(name = "clie_credito_dias")
    private Integer clieCreditoDias;
    @Column(name = "clie_estado")
    private Boolean clieEstado;
    @JoinColumn(name = "ticl_id", referencedColumnName = "ticl_id")
    @ManyToOne
    private TipoCliente ticlId;
    

    public Cliente() {
        super();
    }

  

    public Date getClieFechaRegistro() {
        return clieFechaRegistro;
    }

    public void setClieFechaRegistro(Date clieFechaRegistro) {
        this.clieFechaRegistro = clieFechaRegistro;
    }

    public Boolean getClieCredito() {
        return clieCredito;
    }

    public void setClieCredito(Boolean clieCredito) {
        this.clieCredito = clieCredito;
    }

    public Integer getClieCreditoDias() {
        return clieCreditoDias;
    }

    public void setClieCreditoDias(Integer clieCreditoDias) {
        this.clieCreditoDias = clieCreditoDias;
    }

    public Boolean getClieEstado() {
        return clieEstado;
    }

    public void setClieEstado(Boolean clieEstado) {
        this.clieEstado = clieEstado;
    }

    public TipoCliente getTiclId() {
        return ticlId;
    }

    public void setTiclId(TipoCliente ticlId) {
        this.ticlId = ticlId;
    }
    
    
 
}
