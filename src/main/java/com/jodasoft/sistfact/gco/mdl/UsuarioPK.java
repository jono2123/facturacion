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

/**
 *
 * @author javila
 */
@Embeddable
public class UsuarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "alma_id", nullable = false)
    private int almaId;
    @Basic(optional = false)
    @Column(name = "usua_id", nullable = false)
    private int usuaId;

    public UsuarioPK() {
    }

    public UsuarioPK(int almaId, int usuaId) {
        this.almaId = almaId;
        this.usuaId = usuaId;
    }

    public int getAlmaId() {
        return almaId;
    }

    public void setAlmaId(int almaId) {
        this.almaId = almaId;
    }

    public int getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(int usuaId) {
        this.usuaId = usuaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) almaId;
        hash += (int) usuaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPK)) {
            return false;
        }
        UsuarioPK other = (UsuarioPK) object;
        if (this.almaId != other.almaId) {
            return false;
        }
        if (this.usuaId != other.usuaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jodasoft.sistfact.gco.mdl.UsuarioPK[ almaId=" + almaId + ", usuaId=" + usuaId + " ]";
    }
    
}
