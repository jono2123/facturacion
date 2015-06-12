/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.util.exp.ClienteValidadorException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
@Named(value = "clienteController")
@RequestScoped
public class ClienteController extends AbstractMB implements Serializable{

    /**
     * Creates a new instance of ClienteController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.ClienteFacade clienteFacade;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String mail;
    private Date fechaNacimiento;
    
    Cliente cliente;
    List<Cliente> clientes;
    List<Cliente> clientesFiltrados;
    
    ///constructor/////////////////////////
    public ClienteController() {
    }
    //funciones///////////////////////////
    private void reiniciaCliente(){
        cliente = new Cliente();
    }
     public void vaciaTextos()
    {
        cedula="";
        nombres="";
        apellidos="";
        direccion="";
        telefono="";
        mail="";
        fechaNacimiento=new Date();
    }
    
   public String formateaFecha(Date fecha){
       if(fecha!=null)
        return fecha.toLocaleString().substring(0, fecha.toLocaleString().indexOf(" "));
       else
           return "";
   }
    
    public void saveCliente() {
        try {
            reiniciaCliente();
            Almacen almacen = LoginController.getInstance().getUsuario().getAlmaId();
     
            cliente.setClieNombres(nombres);
            cliente.setClieApellidos(apellidos);
            cliente.setClieDireccion(direccion);
            cliente.setClieTelefono(telefono);
            cliente.setClieMail(mail);
            cliente.setClieFechaNacimiento(fechaNacimiento);
            cliente.setClieEstado(true);
            cliente.setClieCedula(cedula);
            cliente.setAlmaId(almacen);
            clienteFacade.save(cliente);
            closeDialog();
            displayInfoMessageToUser("Cliente Guardado Correctamente");
            clientes.add(cliente);
            reiniciaCliente();
            vaciaTextos();
        } catch (javax.ejb.EJBException ex) {
            keepDialogOpen();
            if(ex.getCausedByException().getCause().getLocalizedMessage().contains("llave duplicada viola restricción de unicidad"))
            displayErrorMessageToUser("Ya existe un cliente con esta cédula");
        } catch (ClienteValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    public void updateCliente() {
        try {
            cliente.setClieNombres(nombres);
            cliente.setClieApellidos(apellidos);
            cliente.setClieDireccion(direccion);
            cliente.setClieTelefono(telefono);
            cliente.setClieMail(mail);
            cliente.setClieFechaNacimiento(fechaNacimiento);
            cliente.setClieEstado(true);
            clienteFacade.update(cliente);
            closeDialog();
            displayInfoMessageToUser("Cliente Modificado Correctamente");
            reiniciaCliente();
            vaciaTextos();
        } catch (Exception ex) {
           keepDialogOpen();
           displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    public void deleteCliente(){
        try {
            cliente.setClieEstado(false);
            clienteFacade.delete(cliente);
            closeDialog();
            displayInfoMessageToUser("Cliente Eliminado Correctamente");
            clientes.remove(cliente);
            reiniciaCliente();
            vaciaTextos();
        } catch (Exception ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        setCedula(cliente.getClieCedula());
        setNombres(cliente.getClieNombres());
        setApellidos(cliente.getClieApellidos());
        setDireccion(cliente.getClieDireccion());
        setTelefono(cliente.getClieTelefono());
        setMail(cliente.getClieMail());
        setFechaNacimiento(cliente.getClieFechaNacimiento());
    }
    
    
    
    ///////////////////////////////////getter and setter////////////////////////////
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres.toUpperCase();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.toUpperCase();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail.toLowerCase();
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        if(clientes==null)
            clientes=clienteFacade.findClienteByAlmaIdAndClieEstado(LoginController.getInstance().getUsuario().getAlmaId(), true);
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }
    
    
    
    
    
}
