/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
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
public class AlmacenModuloFacade extends AbstractFacade<AlmacenModulo> {

    public AlmacenModuloFacade() {
        super(AlmacenModulo.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<AlmacenModulo> findByAlmaID(Almacen almaId) {
        List<AlmacenModulo> modulos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        modulos = findAllResults("AlmacenModulo.findByAlmaId", parameters);
        return modulos;
    }
}
