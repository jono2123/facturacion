/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.PaisValidadorException;
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
public class PaisFacade extends AbstractFacade<Pais>{

    public PaisFacade() {
        super(Pais.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Pais pais) throws PaisValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Pais", pais);
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", pais.getPaisNombre());
            create(pais);
        } catch (AtributoInvalidoException ex) {
            throw new PaisValidadorException(ex);
        }
    }
    
    
}
