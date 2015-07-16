/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.DetalleFactura;
import com.jodasoft.sistfact.gco.mdl.Factura;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.FacturaValidadorException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    
    @EJB
    DetalleFacturaFacade detalleFacade;
    
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
    
    public List<Factura> findByAlmaIdAndFactEstado(Almacen almaId, Integer factEstado){
        List<Factura> facturas;
         Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("factEstado", factEstado);
        facturas = findAllResults("Factura.findByAlmaIdAndFactEstado", parameters);
        return facturas;
    }
    
    //Factura.findByfactFechaRange
    public List<Factura> findByFactFechaRange(Almacen almaId, Integer factEstado, Date desde, Date hasta ){
        List<Factura> facturas;
         Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("factEstado", factEstado);
        parameters.put("fechaIni",desde);
        parameters.put("fechaFin", hasta);
        facturas = findAllResults("Factura.findByfactFechaRangeAndAlmaIdAndFactEstado", parameters);
        return facturas;
    }
    //@NamedQuery(name = "Factura.findBypersCedulaAndAlmaIdAndEstado", query = "SELECT f FROM Factura f WHERE f.clieId.persCedula = :persCedula and f.factEstado = :factEstado and f.almaId = :almaId"),
    public List<Factura> findByPersCedulaAndAlmaIdandFactEstado(Almacen almaId, Integer factEstado, String cedula ){
        List<Factura> facturas;
         Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("factEstado", factEstado);
        parameters.put("persCedula", cedula);
        facturas = findAllResults("Factura.findBypersCedulaAndAlmaIdAndEstado", parameters);
        return facturas;
    }
    
    public void update(Factura factura, List<DetalleFactura> nuevoDetalle){
        for(DetalleFactura detalle:factura.getDetalleFacturaList()){
            detalleFacade.remove(detalle);
        }
        for(DetalleFactura detalle: nuevoDetalle)
        {
            detalle.setFactId(factura);
        }
        factura.setDetalleFacturaList(nuevoDetalle);
        edit(factura);
    }
    public void anularFactura(Factura factura){
        factura.setFactEstado(2);
        edit(factura);
    }
}
