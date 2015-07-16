/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.PrecioVenta;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johnny
 */
@Stateless
public class PrecioVentaFacade extends AbstractFacade<PrecioVenta> {

    public PrecioVentaFacade() {
        super(PrecioVenta.class);
    }
    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void deletePrecio(PrecioVenta precio) {
        remove(precio);
    }

    //PrecioVenta.findByArtiId

    public List<PrecioVenta> findByArtiId(Articulo artiId) {
        List<PrecioVenta> precios;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("artiId", artiId);
        precios = findAllResults("PrecioVenta.findByArtiId", parameters);
        return precios;
    }

    public PrecioVenta findByArtIdAndTiclId(Articulo artiId, TipoCliente ticlId) {
        PrecioVenta precio;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("artiId", artiId);
        parameters.put("ticlId", ticlId);
        precio = findOneResult("PrecioVenta.findByArtiIdAndTiclId", parameters);
        return precio;
    }

}
