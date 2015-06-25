/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
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
public class PermisoFacade extends AbstractFacade<Permiso>{

    public PermisoFacade() {
        super(Permiso.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Permiso> findByRol(Rol rolId){
        List<Permiso> permisos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("rolId", rolId);
        permisos = findAllResults("Permiso.findByRolId", parameters);
        return permisos;
    }
    
}
