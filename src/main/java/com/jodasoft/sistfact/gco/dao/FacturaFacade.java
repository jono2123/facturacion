/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.FacturaValidadorException;
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
public class FacturaFacade extends AbstractFacade<Factura> {

    public FacturaFacade() {
        super(Factura.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Factura factura) throws FacturaValidadorException{
        try {
            ValidarAtributoUtil.validarObjetoNulo("Factura", factura);
            ValidarAtributoUtil.validarDoubleNegativo("Subtotal con IVA", factura.getFactSubtotalConIva());
            ValidarAtributoUtil.validarDoubleNegativo("Subtotal sin IVA", factura.getFactSubtotalSinIva());
            ValidarAtributoUtil.validarDoubleNegativo("Total", factura.getFactTotal());
            ValidarAtributoUtil.validarDoubleNegativo("Iva", factura.getFactIva());
            ValidarAtributoUtil.validarObjetoNulo("Detalle", factura.getDetalleFacturaList());
            ValidarAtributoUtil.validarObjetoNulo("Cliente", factura.getClieId().getPersCedula());
            if(factura.getDetalleFacturaList().size()==0)
            {
                throw new FacturaValidadorException("No se ha agregado ningun Ítem al detalle de la factura");
            }
            create(factura);
        } catch (AtributoInvalidoException ex) {
            throw new FacturaValidadorException(ex);
        }
        
    }
    
}
