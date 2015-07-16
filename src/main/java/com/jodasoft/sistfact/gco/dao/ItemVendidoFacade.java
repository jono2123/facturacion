/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.ItemVendido;
import java.util.Date;
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
public class ItemVendidoFacade extends AbstractFacade<ItemVendido> {

    public ItemVendidoFacade() {
        super(ItemVendido.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<ItemVendido> findByDateRangeAndAlmaId(Date desde, Date hasta, int almaid, int estado){
        List<ItemVendido> items;
        Map<String, Object> parameters = new HashMap<String, Object>();   
        parameters.put("fecha_ini",desde);
        parameters.put("fecha_fin", hasta);
        parameters.put("almaid", almaid);
        parameters.put("estado", estado);
        items = findAllResultsFunction("findVentasByDateRange", parameters);
        return items;
    } 
    

}
