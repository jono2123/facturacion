/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Cliente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 *
 * @author javila
 */
@Named(value = "listaClientesController")
@SessionScoped
public class ListaClientesController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ListaClientesController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.ClienteFacade clienteFacade;
    public ListaClientesController() {
    }
    public static ListaClientesController getInstance() {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ex = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
                createValueExpression(context, "#{listaClientesController}", ListaClientesController.class);
        return (ListaClientesController) ex.getValue(context);
    }
    List<Cliente> clientes;
    Cliente cliente;
    List<Cliente> clientesFiltrados;

    public List<Cliente> getClientes() {
        if(clientes==null)
            clientes=clienteFacade.findClienteByAlmaIdAndClieEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }
    
    
    
}
