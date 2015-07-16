/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Zona;
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
public class ZonaFacade extends AbstractFacade<Zona>{

    public ZonaFacade() {
        super(Zona.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Zona zona) throws CiudadValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Ciudad", zona.getCiudId());
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", zona.getZonaNombre());
            create(zona);
        } catch (AtributoInvalidoException ex) {
            throw new CiudadValidadorException(ex);
        }
    }
    
}
