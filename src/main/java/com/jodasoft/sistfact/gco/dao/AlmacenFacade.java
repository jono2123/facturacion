/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AlmacenValidadorException;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author javila
 */
@Stateless
public class AlmacenFacade extends AbstractFacade<Almacen> {
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlmacenFacade() {
        super(Almacen.class);
    }
    
    public void update(Almacen almacen) throws AlmacenValidadorException{
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", almacen.getAlmaNombre());
            ValidarAtributoUtil.validarStringNuloVacio("RUC", almacen.getAlmaRuc());
            ValidarAtributoUtil.validarDoubleNegativo("IVA", almacen.getAlmaIva().doubleValue());
            ValidarAtributoUtil.validarDoubleCero("IVA", almacen.getAlmaIva().doubleValue());
            
            edit(almacen);
        } catch (AtributoInvalidoException ex) {
            throw new AlmacenValidadorException(ex);
        }
        
    }
    
}
