/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.ReporteItemVendido;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class ReporteItemVendidoFacade extends AbstractFacade<ReporteItemVendido> {

    public ReporteItemVendidoFacade() {
        super(ReporteItemVendido.class);
    }
     @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //ReporteItemVendido.findByAlmaId
    public List<ReporteItemVendido> findByAlmaId(int almaId){
        List<ReporteItemVendido> items;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        items=findAllResults("ReporteItemVendido.findByAlmaId", parameters);
        return items;
    }
    
    
}
