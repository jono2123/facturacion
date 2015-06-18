/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.RolValidadorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RolFacade extends AbstractFacade<Rol> {

    public RolFacade() {
        super(Rol.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Rol> findByAlmaId(Almacen almaId) {
        List<Rol> roles;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("rolEstado", true);
        roles=findAllResults("Rol.findByAlmaIdAndEstado", parameters);
        return roles;
    }
    
    public void save(Rol rol) throws RolValidadorException{
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", rol.getRolNombre());
            create(rol);
        } catch (AtributoInvalidoException ex) {
            throw new RolValidadorException(ex);
        }
    }
    
    public void update(Rol rol) throws RolValidadorException
    {
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", rol.getRolNombre());
            edit(rol);
        } catch (AtributoInvalidoException ex) {
            throw new RolValidadorException(ex);
        }
    }
    
    public void delete(Rol rol)
    {
        rol.setRolEstado(false);
        edit(rol);
    }

}
