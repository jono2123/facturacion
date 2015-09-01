/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.dao.AbstractFacade;
import com.jodasoft.sistfact.gco.mdl.CajaTransaccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author f35
 */
@Stateless
public class CajaTransaccionFacade extends AbstractFacade<CajaTransaccion> {
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CajaTransaccionFacade() {
        super(CajaTransaccion.class);
    }
    
}
