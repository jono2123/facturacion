/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.IngresoMercaderiaDetalle;
import com.jodasoft.sistfact.gco.mdl.Lote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class LoteFacade extends AbstractFacade<Lote> {

    public LoteFacade() {
        super(Lote.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Lote creaLote(Lote lote){
        create(lote);
        return lote;
    }
    
}
