/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.AlmacenTransaccion;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AlmacenTransaccionValidadorException;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
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
public class AmacenTransaccionFacade extends AbstractFacade<AlmacenTransaccion> {

    public AmacenTransaccionFacade() {
        super(AlmacenTransaccion.class);
    }
    
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(AlmacenTransaccion transaccion) throws AlmacenTransaccionValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("fecha", transaccion.getTransFecha());
            ValidarAtributoUtil.validarStringNuloVacio("concepto", transaccion.getTransConcepto());
            ValidarAtributoUtil.validarObjetoNulo("usuiario", transaccion.getUsuaId());
            ValidarAtributoUtil.validarObjetoNulo("número documento", transaccion.getTransNumDocumento());
            create(transaccion);
        } catch (AtributoInvalidoException ex) {
            throw new AlmacenTransaccionValidadorException(ex);
        }
        
    }
    
}
