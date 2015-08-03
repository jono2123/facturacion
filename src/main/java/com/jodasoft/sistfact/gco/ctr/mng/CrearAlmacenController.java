/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr.mng;

import com.jodasoft.sistfact.gco.ctr.AbstractMB;
import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
import com.jodasoft.sistfact.gco.mdl.Modulo;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author johnny
 */
@ManagedBean(name = "crearAlmacenController")
@ViewScoped
public class CrearAlmacenController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of CrearAlmacenController
     */
    private Almacen almacen;
    private AlmacenModulo almo;
    private String nombreRolAdministrador;
    private boolean facturacion;
    private boolean inventario;
    private String nombreUsuarioAdministrador;
    private String passwordUsuarioAdministrador;
    private String passwordUsuarioAdministradorConfirm;

    @EJB
    private com.jodasoft.sistfact.gco.dao.AlmacenFacade almacenFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.ModuloFacade moduloFacade;

    public CrearAlmacenController() {
        almacen = new Almacen();
        almacen.setAlmaComa(false);
        almacen.setAlmaDesglozar(false);
        almacen.setAlmaDiferenciarPrecios(false);
        almacen.setAlmaFacturaAutonumerada(false);
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public boolean getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(boolean facturacion) {
        this.facturacion = facturacion;
    }

    public boolean getInventario() {
        return inventario;
    }

    public void setInventario(boolean inventario) {
        this.inventario = inventario;
    }

    public String getNombreRolAdministrador() {
        return nombreRolAdministrador;
    }

    public void setNombreRolAdministrador(String nombreRolAdministrador) {
        this.nombreRolAdministrador = nombreRolAdministrador;
    }

    public String getNombreUsuarioAdministrador() {
        return nombreUsuarioAdministrador;
    }

    public void setNombreUsuarioAdministrador(String nombreUsuarioAdministrador) {
        this.nombreUsuarioAdministrador = nombreUsuarioAdministrador;
    }

    public String getPasswordUsuarioAdministrador() {
        return passwordUsuarioAdministrador;
    }

    public void setPasswordUsuarioAdministrador(String passwordUsuarioAdministrador) {
        this.passwordUsuarioAdministrador = passwordUsuarioAdministrador;
    }

    public String getPasswordUsuarioAdministradorConfirm() {
        return passwordUsuarioAdministradorConfirm;
    }

    public void setPasswordUsuarioAdministradorConfirm(String passwordUsuarioAdministradorConfirm) {
        this.passwordUsuarioAdministradorConfirm = passwordUsuarioAdministradorConfirm;
    }

    public String obtenMetodoDePago(int metodo) {
        switch (metodo) {
            case 0:
                return "Mensual";
            case 1:
                return "Por Transacción";
            case 2:
                return "Compra";
        }
        return "";
    }

    public void limpiaTodo() {
        almacen = new Almacen();
        almacen.setAlmaComa(false);
        almacen.setAlmaDesglozar(false);
        almacen.setAlmaDiferenciarPrecios(false);
        almacen.setAlmaFacturaAutonumerada(false);
        facturacion = false;
        inventario = false;
        nombreRolAdministrador = "";
        passwordUsuarioAdministrador = "";
        passwordUsuarioAdministradorConfirm = "";
        nombreUsuarioAdministrador="";
        
    }

    public void crear() {
        almacen.setAlmaFechaCreacion(new Date());
        almacen.setAlmaEstado(0);
        almacen.setAlmaDiaCorte(new Date().getDate());
        List<Modulo> modulos = new ArrayList<Modulo>();
        if (facturacion) {
            Modulo modFacturacion = moduloFacade.find(1);
            Modulo modInventario = moduloFacade.find(2);
            Modulo modAdministracion = moduloFacade.find(3);
            modulos.add(modFacturacion);
            modulos.add(modInventario);
            modulos.add(modAdministracion);
        }
        Rol rol = new Rol();
        rol.setAlmaId(almacen);
        rol.setRolEstado(true);
        rol.setRolNombre(nombreRolAdministrador);
        Usuario usuario = new Usuario();
        usuario.setRolId(rol);
        usuario.setUsuaClave(passwordUsuarioAdministrador);
        usuario.setUsuaEstado(true);
        usuario.setUsuaNombre(nombreUsuarioAdministrador);
        try {
            almacenFacade.save(almacen, modulos, rol, usuario);
            displayInfoMessageToUser("Almacen creado correctamente");
            limpiaTodo();
        } catch (Exception ex) {
            displayErrorMessageToUser(ex.getMessage());
        }

    }

}
