/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.mdl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author javila
 */
@Embeddable
public class ClientePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "alma_id", nullable = false)
    private int almaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "clie_cedula", nullable = false, length = 13)
    private String clieCedula;

    public ClientePK() {
    }

    public ClientePK(int almaId, String clieCedula) {
        this.almaId = almaId;
        this.clieCedula = clieCedula;
    }

    public int getAlmaId() {
        return almaId;
    }

    public void setAlmaId(int almaId) {
        this.almaId = almaId;
    }

    public String getClieCedula() {
        return clieCedula;
    }

    public void setClieCedula(String clieCedula) {
        this.clieCedula = clieCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) almaId;
        hash += (clieCedula != null ? clieCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientePK)) {
            return false;
        }
        ClientePK other = (ClientePK) object;
        if (this.almaId != other.almaId) {
            return false;
        }
        if ((this.clieCedula == null && other.clieCedula != null) || (this.clieCedula != null && !this.clieCedula.equals(other.clieCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.ClientePK[ almaId=" + almaId + ", clieCedula=" + clieCedula + " ]";
    }
    
}
