/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.ArticuloValidadorException;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
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
public class ArticuloFacade extends AbstractFacade<Articulo>{

    public ArticuloFacade() {
        super(Articulo.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Articulo> listar(Almacen almaId, boolean estado)
    {
        List <Articulo> articulos = new ArrayList<Articulo>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("estado", estado);
        articulos=findAllResults("Articulo.findByAlmaIdAndArtiEstado", parameters);
        return articulos;
    }
    
    public void save(Articulo articulo) throws ArticuloValidadorException{
        try {
            ValidarAtributoUtil.validarDoubleNegativo("Precio Venta",articulo.getArtiPrecioVenta());
            ValidarAtributoUtil.validarObjetoNulo("Artículo",articulo);
            ValidarAtributoUtil.validarStringNuloVacio("Descripción", articulo.getArtiDescripcion());
            ValidarAtributoUtil.validarStringNuloVacio("Código", articulo.getArtiCodigo());
            create(articulo);
        } catch (AtributoInvalidoException ex) {
            throw new ArticuloValidadorException(ex);
        }
    }
    
    public void update(Articulo articulo) throws ArticuloValidadorException{
        try {
            ValidarAtributoUtil.validarDoubleNegativo("Precio Venta",articulo.getArtiPrecioVenta());
            ValidarAtributoUtil.validarObjetoNulo("Artículo",articulo);
            ValidarAtributoUtil.validarStringNuloVacio("Descripción", articulo.getArtiDescripcion());
            ValidarAtributoUtil.validarStringNuloVacio("Código", articulo.getArtiCodigo());
            edit(articulo);
        } catch (AtributoInvalidoException ex) {
            throw new ArticuloValidadorException(ex);
        }
    }
    
    public void delete(Articulo articulo)
    {
        articulo.setArtEstado(false);
        edit(articulo);
    }
}
