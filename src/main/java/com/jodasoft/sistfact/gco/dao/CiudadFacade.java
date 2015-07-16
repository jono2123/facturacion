/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Ciudad;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.CiudadValidadorException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class CiudadFacade extends AbstractFacade<Ciudad> {

    public CiudadFacade() {
        super(Ciudad.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void saveCiudad(Ciudad ciudad) throws CiudadValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Provincia", ciudad.getProvId());
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", ciudad.getCiudNombre());
            create(ciudad);
            
        } catch (AtributoInvalidoException ex) {
            throw new CiudadValidadorException(ex);
        }
        
    }

}
