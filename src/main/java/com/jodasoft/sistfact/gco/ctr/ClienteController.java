/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Ciudad;
import com.jodasoft.sistfact.gco.mdl.Cliente;
import com.jodasoft.sistfact.gco.mdl.Pais;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Provincia;
import com.jodasoft.sistfact.gco.mdl.TipoCliente;
import com.jodasoft.sistfact.gco.mdl.Zona;
import com.jodasoft.sistfact.gco.util.exp.ClienteValidadorException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
//@Named(value = "clienteController")
//@SessionScoped
@ManagedBean(name = "clienteController")
@ViewScoped
public class ClienteController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of ClienteController
     */
    @EJB
    private com.jodasoft.sistfact.gco.dao.ClienteFacade clienteFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.TipoClienteFacade tipoClienteFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.PaisFacade paisFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ProvinciaFacade provinciaFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.CiudadFacade ciudadFacade;
    @EJB
    private com.jodasoft.sistfact.gco.dao.ZonaFacade zonaFacade;

    private String cedula;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String mail;
    private Date fechaNacimiento;
    private Permiso permiso;
    private int tipo;
    private int idPais;
    private int idProvincia;
    private int idCiudad;
    private int idZona;
    private int activeIndex;

    Cliente cliente;
    List<Cliente> clientes;
    List<Cliente> clientesFiltrados;
    List<TipoCliente> tipos;
    List<Pais> paises;
    Pais pais;
    List<Provincia> provincias;
    Provincia provincia;
    List<Ciudad> ciudades;
    Ciudad ciudad;
    List<Zona> zonas;
    Zona zona;

    ///constructor/////////////////////////
    public ClienteController() {
        activeIndex = 0;
    }

    //funciones///////////////////////////
    private void reiniciaCliente() {
        cliente = new Cliente();
    }

    public void guardar() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        if (cliente.getPersId() == null) {
            if (permiso.getPermCrear()) {
                saveCliente();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        } else {
            if(permiso.getPermModificar()){
                updateCliente();
            }else{
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }
            
        }
        activeIndex = 0;
    }

    public void nuevoCliente() {
        activeIndex = 1;
        vaciaTextos();
    }

    public void vaciaTextos() {
        cedula = "";
        nombres = "";
        apellidos = "";
        direccion = "";
        telefono = "";
        mail = "";
        fechaNacimiento = new Date();
        tipo = 0;
        idPais = 0;
        provincias = null;
        ciudades = null;
        zonas = null;
        idProvincia = 0;
        idCiudad = 0;
        idZona = 0;
        cliente = new Cliente();

    }

    public void editarCliente(Cliente cliente) {

        activeIndex = 1;
        this.cliente = cliente;
        setCedula(cliente.getPersCedula());
        setNombres(cliente.getPersNombres());
        setApellidos(cliente.getPersApellidos());
        setDireccion(cliente.getPersDireccion());
        setTelefono(cliente.getPersTelefono());
        setMail(cliente.getPersMail());
        setFechaNacimiento(cliente.getPersFechaNacimiento());
        setTipo(cliente.getTiclId().getTiclId());
        if (cliente.getZonaId() != null) {
            zona = cliente.getZonaId();
            paises = paisFacade.findAll();
            pais = cliente.getZonaId().getCiudId().getProvId().getPaisId();
            idPais = pais.getPaisId();
            provincias = pais.getProvinciaList();
            provincia = zona.getCiudId().getProvId();
            idProvincia = provincia.getProvId();
            ciudades = provincia.getCiudadList();
            ciudad = zona.getCiudId();
            idCiudad = ciudad.getCiudId();
            zonas = ciudad.getZonaList();
            idZona = zona.getZonaId();
        } else {
            idPais = 0;
            provincias = null;
            ciudades = null;
            zonas = null;
            idProvincia = 0;
            idCiudad = 0;
            idZona = 0;
        }
    }

    public void refrescaPais() {
        pais = null;
        paises = null;
        provincias = null;
        ciudades = null;
        zonas = null;
        idPais = 0;
        idProvincia = 0;
        idCiudad = 0;
        idZona = 0;
    }

    public String formateaFecha(Date fecha) {
        if (fecha != null) {
            return fecha.toLocaleString().substring(0, fecha.toLocaleString().indexOf(" "));
        } else {
            return "";
        }
    }

    public void saveCliente() {
        try {
            reiniciaCliente();
            Almacen almacen = LoginController.getInstance().getUsuario().getRolId().getAlmaId();
            TipoCliente tipocl = tipoClienteFacade.find(tipo);

            cliente.setPersNombres(nombres);
            cliente.setPersApellidos(apellidos);
            cliente.setPersDireccion(direccion);
            cliente.setPersTelefono(telefono);
            cliente.setPersMail(mail);
            cliente.setPersFechaNacimiento(fechaNacimiento);
            cliente.setClieEstado(true);
            cliente.setTiclId(tipocl);
            cliente.setPersCedula(cedula);
            cliente.setAlmaId(almacen);
            cliente.setPersEstado(true);
            cliente.setClieFechaRegistro(new Date());
            if (idZona != 0) {
                zona = zonaFacade.find(idZona);
                cliente.setZonaId(zona);
            } else {
                cliente.setZonaId(null);
            }
            clienteFacade.save(cliente);
            closeDialog();
            displayInfoMessageToUser("Cliente Guardado Correctamente");
            clientes.add(cliente);
            reiniciaCliente();
            vaciaTextos();
        } catch (javax.ejb.EJBException ex) {
            keepDialogOpen();
            if (ex.getCausedByException().getCause().getLocalizedMessage().contains("llave duplicada viola restricción de unicidad")) {
                displayErrorMessageToUser("Ya existe un cliente con esta cédula");
            }
        } catch (ClienteValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public void updateCliente() {
        try {
            TipoCliente tipocl = tipoClienteFacade.find(tipo);
            cliente.setPersNombres(nombres);
            cliente.setPersApellidos(apellidos);
            cliente.setPersDireccion(direccion);
            cliente.setPersTelefono(telefono);
            cliente.setPersMail(mail);
            cliente.setPersFechaNacimiento(fechaNacimiento);
            cliente.setClieEstado(true);
            cliente.setTiclId(tipocl);
            if (idZona != 0) {
                zona = zonaFacade.find(idZona);
                cliente.setZonaId(zona);
            } else {
                cliente.setZonaId(null);
            }
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

    public void deleteCliente(Cliente cliente) {
        try {
            this.cliente = cliente;
            this.cliente.setClieEstado(false);
            clienteFacade.delete(this.cliente);
            closeDialog();
            displayInfoMessageToUser("Cliente Eliminado Correctamente");
            clientes.remove(this.cliente);
            reiniciaCliente();
            vaciaTextos();
        } catch (Exception ex) {
            displayErrorMessageToUser(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        setCedula(cliente.getPersCedula());
        setNombres(cliente.getPersNombres());
        setApellidos(cliente.getPersApellidos());
        setDireccion(cliente.getPersDireccion());
        setTelefono(cliente.getPersTelefono());
        setMail(cliente.getPersMail());
        setFechaNacimiento(cliente.getPersFechaNacimiento());
        setTipo(cliente.getTiclId().getTiclId());
        if (cliente.getZonaId() != null) {
            zona = cliente.getZonaId();
            paises = paisFacade.findAll();
            pais = cliente.getZonaId().getCiudId().getProvId().getPaisId();
            idPais = pais.getPaisId();
            provincias = pais.getProvinciaList();
            provincia = zona.getCiudId().getProvId();
            idProvincia = provincia.getProvId();
            ciudades = provincia.getCiudadList();
            ciudad = zona.getCiudId();
            idCiudad = ciudad.getCiudId();
            zonas = ciudad.getZonaList();
            idZona = zona.getZonaId();
        } else {
            idPais = 0;
            provincias = null;
            ciudades = null;
            zonas = null;
            idProvincia = 0;
            idCiudad = 0;
            idZona = 0;
        }
    }

    //////////////////////listeners para los combos/////////////////////////////////
    ////////////////////pais/////////////////////////////////////////////////////////
    public void onPaisChange() {
        if (idPais != 0) {
            pais = paisFacade.find(idPais);
            idProvincia = 0;
            idCiudad = 0;
            idZona = 0;
            provincias = pais.getProvinciaList();
            ciudades = null;
            zonas = null;

        } else {
            provincias = null;
            ciudades = null;
            zonas = null;
            idProvincia = 0;
            idCiudad = 0;
            idZona = 0;

        }
    }

    public void onProvinciaChange() {
        if (idProvincia != 0) {
            provincia = provinciaFacade.find(idProvincia);
            ciudades = provincia.getCiudadList();
            idCiudad = 0;
            idZona = 0;
            zonas = null;
        } else {
            ciudades = null;
            zonas = null;
            idCiudad = 0;
            idZona = 0;
        }
    }

    public void onCiudadChange() {
        if (idCiudad != 0) {
            ciudad = ciudadFacade.find(idCiudad);
            zonas = ciudad.getZonaList();
            idZona = 0;
        } else {
            zonas = null;
            idZona = 0;
        }
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
        if (clientes == null) {
            clientes = clienteFacade.findClienteByAlmaIdAndClieEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        }
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

    public Permiso getPermiso() {
        if (permiso == null) {
            permiso = LoginController.getInstance().getPermiso("Clientes");
        }
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public List<TipoCliente> getTipos() {
        if (tipos == null) {
            tipos = tipoClienteFacade.findByAlmaIdAndEstado(LoginController.getInstance().getUsuario().getRolId().getAlmaId(), true);
        }
        return tipos;
    }

    public void setTipos(List<TipoCliente> tipos) {
        this.tipos = tipos;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<Pais> getPaises() {
        if (paises == null) {
            paises = paisFacade.findAll();
        }
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Provincia> getProvincias() {
        if (provincias == null) {
            provincias = new ArrayList<Provincia>();
        }
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Ciudad> getCiudades() {
        if (ciudades == null) {
            ciudades = new ArrayList<Ciudad>();
        }
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Zona> getZonas() {
        if (zonas == null) {
            zonas = new ArrayList<Zona>();
        }
        return zonas;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

}
