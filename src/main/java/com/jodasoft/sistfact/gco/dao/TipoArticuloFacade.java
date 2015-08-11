/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.TipoArticulo;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.TipoArticuloValidadorException;
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
public class TipoArticuloFacade extends AbstractFacade<TipoArticulo> {

    public TipoArticuloFacade() {
        super(TipoArticulo.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    //TipoArticulo.findAlmaIdAndNivel0
    public List<TipoArticulo> findAlmaIdAndNivel0(Almacen almaId) {
        List<TipoArticulo> tipos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("tiarEstado", true);
        tipos = findAllResults("TipoArticulo.findAlmaIdAndNivel0", parameters);
        return tipos;
    }
    //TipoArticulo.findByTiarPadre
    public List<TipoArticulo> findByTiarPadre(int tiarPadre) {
        List<TipoArticulo> tipos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tiarPadre", tiarPadre);
        parameters.put("tiarEstado", true);
        tipos = findAllResults("TipoArticulo.findByTiarPadre", parameters);
        return tipos;
    }
    //TipoArticulo.findByTiarNivel
    public List<TipoArticulo> findByTiarNivel(int nivel, Almacen almaId) {
        List<TipoArticulo> tipos;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tiarNivel", nivel);
        parameters.put("almaId", almaId);
        parameters.put("tiarEstado", true);
        tipos = findAllResults("TipoArticulo.findByTiarNivel", parameters);
        return tipos;
    }
    
    public void save(TipoArticulo tipo, TipoArticulo padre) throws TipoArticuloValidadorException {
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre del Tipo", tipo.getTiarNombre());
            ValidarAtributoUtil.validarObjetoNulo("Almacen", tipo.getAlmaId());
            create(tipo);
            if (padre != null) {
                padre.setTiarHoja(false);
                edit(padre);
            }

        } catch (AtributoInvalidoException ex) {
            throw new TipoArticuloValidadorException(ex);
        }
    }

}
