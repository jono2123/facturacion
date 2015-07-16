/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Provincia;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.ProvinciaValidadorException;
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
public class ProvinciaFacade extends AbstractFacade<Provincia>{

    public ProvinciaFacade() {
        super(Provincia.class);
    }
    
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Provincia provincia) throws ProvinciaValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Pais", provincia.getPaisId());
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", provincia.getProvNombre());
            create(provincia);
        } catch (AtributoInvalidoException ex) {
            throw new ProvinciaValidadorException(ex);
        }
    }
    
}
