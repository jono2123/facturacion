/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Cardex;
import com.jodasoft.sistfact.gco.mdl.IngresoMercaderia;
import com.jodasoft.sistfact.gco.mdl.IngresoMercaderiaDetalle;
import com.jodasoft.sistfact.gco.mdl.Lote;
import com.jodasoft.sistfact.gco.util.exp.IngresoMercaderiaValidadorException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class IngresoMercaderiaFacade extends AbstractFacade<IngresoMercaderia> {
    
    public IngresoMercaderiaFacade() {
        super(IngresoMercaderia.class);
    }
    
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @EJB    
    LoteFacade loteFacade;
    
    @EJB
    CardexFacade cardexFacade;
    
    public void save(IngresoMercaderia ingreso) throws IngresoMercaderiaValidadorException {
        if (ingreso.getIngresoMercaderiaDetalleList() == null || ingreso.getIngresoMercaderiaDetalleList().isEmpty()) {
            throw new IngresoMercaderiaValidadorException("No se ha seleccionado ningun item para el ingreso");            
        }
        for (IngresoMercaderiaDetalle detalle : ingreso.getIngresoMercaderiaDetalleList()) {
            Lote lote = new Lote();
            lote.setArtiId(detalle.getArtiId());
            lote.setLoteCantidadInicial(detalle.getImdeCantidad());
            lote.setLoteCantidadActual(detalle.getImdeCantidad());
            lote.setLoteCosto(detalle.getImdeCostoUnitario() + detalle.getImdeIva());
            lote = loteFacade.creaLote(lote);
            lote.setLoteCodigo(detalle.getLoteCodigo());
            detalle.setLoteId(lote);
        }
        create(ingreso);        
    }
}
