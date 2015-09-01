/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Caja;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Freddy Sumba
 */
@Stateless
public class CajaFacade extends AbstractFacade<Caja> {

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CajaFacade() {
        super(Caja.class);
    }

    public void save(Caja caja) {

        create(caja);
    }

    public List<Caja> findByAlmacen(Almacen almaId) {
        List<Caja> cajas = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        cajas = findAllResults("Caja.findByCajaAlmacen", parameters);
        return cajas;
    }

    public Caja findByIdCaja(int id) {

        Caja caja = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("cajaId", id);
        caja = findOneResult("Caja.findByCajaId", parameters);
        return caja;
    }

}
