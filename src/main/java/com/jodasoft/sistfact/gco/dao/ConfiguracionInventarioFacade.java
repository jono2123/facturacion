/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.ConfiguracionInventario;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class ConfiguracionInventarioFacade extends AbstractFacade<ConfiguracionInventario> {

    public ConfiguracionInventarioFacade() {
        super(ConfiguracionInventario.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //ConfiguracionInventario.findByAlmaId
    public ConfiguracionInventario findByAlmaId(Integer almaId){
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("almaId", almaId);
        return findOneResult("ConfiguracionInventario.findByAlmaId", parameters);
        
    }
}
