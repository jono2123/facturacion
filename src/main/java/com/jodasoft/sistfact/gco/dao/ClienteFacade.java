/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

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
    
    public List<Cliente> findClienteByAlmaIdAndClieEstado(int almaId, boolean estado){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("almaId", almaId);
        parameters.put("clieEstado", estado);
        return super.findAllResults("Cliente.findByAlmaIdAndClieEstado", parameters);
    }
    
    public void save(Cliente cliente) throws ClienteValidadorException
    {
        try {
             ValidarAtributoUtil.validarStringNuloVacio("Cedula/Ruc", cliente.getClientePK().getClieCedula());
             ValidarAtributoUtil.validarStringNuloVacio("Nombres", cliente.getClieNombres());
             ValidarAtributoUtil.validarStringNuloVacio("Apellidos", cliente.getClieApellidos());
             ValidarAtributoUtil.validarStringNuloVacio("Dirección", cliente.getClieDireccion());
             ValidarAtributoUtil.validarStringNuloVacio("Telefono", cliente.getClieTelefono());
             Validacion.validadarCed_RUC(cliente.getClientePK().getClieCedula());
             create(cliente);
             /*try {
                 clienteDAO.commitAndCloseTransaction();
             } catch (IdDuplicadoException ex) {
                 throw new ClienteValidadorException("Ya existe un cliente con esta cédula");
             }*/
         } catch (AtributoInvalidoException ex) {
             throw new ClienteValidadorException(ex.getMessage());
         } catch (CedulaInvalidaException ex) {
             throw new ClienteValidadorException(ex.getMessage());
         }
    }
}
