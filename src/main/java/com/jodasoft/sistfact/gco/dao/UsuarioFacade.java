/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Usuario;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author javila
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Usuario findByUserAndPass(String user, String pass){
        Usuario usuario;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("usuaNombre", user);
        parameters.put("usuaClave", pass);
        parameters.put("usuaEstado", true);
        usuario=findOneResult("Usuario.findByUsuaNombreAndUsuaClaveAndEstado", parameters);
        return usuario;
    }

}
