/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.UnidadDeMedida;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.UnidadDeMedidaValidadorException;
import java.util.ArrayList;
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
public class UnidadDeMedidaFacade extends AbstractFacade<UnidadDeMedida>{

     @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UnidadDeMedidaFacade() {
        super(UnidadDeMedida.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<UnidadDeMedida> listar(int almaId){
        List<UnidadDeMedida> unidades = new ArrayList<UnidadDeMedida>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("estado", true);
        unidades=findAllResults("UnidadDeMedida.findByAlmaIdAndUmedEstado", parameters);
        return unidades;
    }
    
    public void save(UnidadDeMedida unidad) throws UnidadDeMedidaValidadorException{
         try {
             ValidarAtributoUtil.validarStringNuloVacio("Nombre", unidad.getUmedNombre());
             create(unidad);
         } catch (AtributoInvalidoException ex) {
             throw new UnidadDeMedidaValidadorException(ex);
         }
    }
    
    public void update(UnidadDeMedida unidad) throws UnidadDeMedidaValidadorException
    {
        try {
             ValidarAtributoUtil.validarStringNuloVacio("Nombre", unidad.getUmedNombre());
             edit(unidad);
         } catch (AtributoInvalidoException ex) {
             throw new UnidadDeMedidaValidadorException(ex);
         }
    }
    
    public void delete(UnidadDeMedida unidad) 
    {
        unidad.setUmedEstado(false);
        edit(unidad);
    }
    
}
