/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Modulo;
import com.jodasoft.sistfact.gco.mdl.Ventana;
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
public class VentanaFacade extends AbstractFacade<Ventana>{

    public VentanaFacade() {
        super(Ventana.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Ventana> findByModuId(Modulo moduId){
        List<Ventana> ventanas;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("moduId", moduId);
        ventanas = findAllResults("Ventana.findByModuId", parameters);
        return ventanas;
    }
    
    
}
