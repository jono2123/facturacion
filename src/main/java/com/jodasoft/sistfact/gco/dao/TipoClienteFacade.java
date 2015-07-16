/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.TipoClienteValidadorException;
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
 * @author johnny
 */
@Stateless
public class TipoClienteFacade extends AbstractFacade<TipoCliente>{

    public TipoClienteFacade() {
        super(TipoCliente.class);
    }
    
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //TipoCliente.findByAlmaIdAndTiclEstado
    public List<TipoCliente> findByAlmaIdAndEstado(Almacen almaId, boolean estado){
        List<TipoCliente> tipos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("ticlEstado", estado);
        tipos=findAllResults("TipoCliente.findByAlmaIdAndTiclEstado", parameters);
        return tipos;
    }
    
    public void save(TipoCliente tipo) throws TipoClienteValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Tipo de Cliente", tipo);
            ValidarAtributoUtil.validarStringNuloVacio("Descripción", tipo.getTiclDescripcion());
            ValidarAtributoUtil.validarObjetoNulo("Almacen", tipo.getAlmaId());
            create(tipo);
        } catch (AtributoInvalidoException ex) {
            throw new TipoClienteValidadorException(ex);
        }
    }
    
    public void update(TipoCliente tipo) throws TipoClienteValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Tipo de Cliente", tipo);
            ValidarAtributoUtil.validarObjetoNulo("Almacen", tipo.getAlmaId());
            ValidarAtributoUtil.validarStringNuloVacio("Descripción", tipo.getTiclDescripcion());
            edit(tipo);
        } catch (AtributoInvalidoException ex) {
            throw new TipoClienteValidadorException(ex);
        }
    }
    
    public void delete(TipoCliente tipo){
        tipo.setTiclEstado(false);
        edit(tipo);
    }
}
