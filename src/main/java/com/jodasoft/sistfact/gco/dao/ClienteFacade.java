/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.util.Validacion;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.CedulaInvalidaException;
import com.jodasoft.sistfact.gco.util.exp.ClienteValidadorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author javila
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    public ClienteFacade() {
        super(Cliente.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Cliente> findClienteByAlmaIdAndClieEstado(Almacen almaId, boolean estado){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("clieEstado", estado);
        return super.findAllResults("Cliente.findByAlmaIdAndClieEstado", parameters);
    }
    
    public void save(Cliente cliente) throws ClienteValidadorException
    {
        try {
             ValidarAtributoUtil.validarStringNuloVacio("Cedula/Ruc", cliente.getPersCedula());
             ValidarAtributoUtil.validarStringNuloVacio("Nombres", cliente.getPersNombres());
             ValidarAtributoUtil.validarStringNuloVacio("Apellidos", cliente.getPersApellidos());
             ValidarAtributoUtil.validarStringNuloVacio("Dirección", cliente.getPersDireccion());
             ValidarAtributoUtil.validarStringNuloVacio("Telefono", cliente.getPersTelefono());
             Validacion.validadarCed_RUC(cliente.getPersCedula());
             create(cliente);
         } catch (AtributoInvalidoException ex) {
             throw new ClienteValidadorException(ex.getMessage());
         } catch (CedulaInvalidaException ex) {
             throw new ClienteValidadorException(ex.getMessage());
         }
    }
    
    public void update(Cliente cliente) throws ClienteValidadorException{
        try {
              ValidarAtributoUtil.validarStringNuloVacio("Nombres", cliente.getPersNombres());
             ValidarAtributoUtil.validarStringNuloVacio("Apellidos", cliente.getPersApellidos());
             ValidarAtributoUtil.validarStringNuloVacio("Dirección", cliente.getPersDireccion());
             ValidarAtributoUtil.validarStringNuloVacio("Telefono", cliente.getPersTelefono());
             edit(cliente);
         } catch (AtributoInvalidoException ex) {
             throw new ClienteValidadorException(ex.getMessage());
         }
    }
    
    public void delete(Cliente cliente) throws ClienteValidadorException
    {
        cliente.setClieEstado(false);
        edit(cliente);
    }
    public Cliente findClienteByCedula(String cedula, Almacen almaId)
    {
        Cliente cliente;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("clieCedula", cedula);
        parameters.put("almaId", almaId);
        parameters.put("clieEstado", true);
        cliente = super.findOneResult("Cliente.findByClieCedulaAndClieAlmacenAndEstado", parameters);
        return cliente;
    }
}
