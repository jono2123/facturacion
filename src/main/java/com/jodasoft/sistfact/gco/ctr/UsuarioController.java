/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import com.jodasoft.sistfact.gco.util.exp.UsuarioValidadorException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
//@Named(value = "usuarioController")
//@SessionScoped
@ManagedBean(name = "usuarioController")
@ViewScoped
public class UsuarioController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of UsuarioController
     */
    private String nomUsuario;
    private String contrasenia;
    private int rol;
    private Permiso permiso;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private List<Rol> roles;

    @EJB
    private com.jodasoft.sistfact.gco.dao.UsuarioFacade usuarioFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.RolFacade rolFacade;
    private int activeIndex;

    public UsuarioController() {
        activeIndex = 0;

    }

    public void onRowSelect(SelectEvent event) {
        setNomUsuario(usuario.getUsuaNombre());
        setRol(usuario.getRolId().getRolId());
    }

    public void saveUsuario() {
        try {
            usuario = new Usuario();
            usuario.setUsuaClave(contrasenia);
            usuario.setUsuaNombre(nomUsuario);
            Rol objRol = new Rol();
            objRol.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            objRol.setRolId(rol);
            usuario.setRolId(objRol);
            usuario.setUsuaEstado(true);
            usuarioFacade.save(usuario);
            closeDialog();
            displayInfoMessageToUser("Usuario creado correctamente");
            usuarios.add(usuario);
            limpiaTodo();

        } catch (UsuarioValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        } catch (javax.ejb.EJBException ex) {
            keepDialogOpen();
            if (ex.getCausedByException().getCause().getLocalizedMessage().contains("llave duplicada viola restricción de unicidad")) {
                displayErrorMessageToUser("Nombre de Usuario no disponible");
            }
        }

    }

    public void updateUsuario(Usuario usuario) {

        try {
            activeIndex = 1;
            this.usuario = usuario;
            usuario.setUsuaClave(contrasenia);
            Rol objRol = new Rol();
            objRol.setAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
            objRol.setRolId(rol);
            usuario.setRolId(objRol);
            usuario.setUsuaEstado(true);
            usuarioFacade.update(usuario);
            closeDialog();
            displayInfoMessageToUser("Usuario modificado correctamente");
            limpiaTodo();
        } catch (UsuarioValidadorException ex) {
            keepDialogOpen();
            displayErrorMessageToUser(ex.getMessage());
        } catch (javax.ejb.EJBException ex) {
            keepDialogOpen();
            if (ex.getCausedByException().getCause().getLocalizedMessage().contains("llave duplicada viola restricción de unicidad")) {
                displayErrorMessageToUser("Nombre de Usuario no disponible");
            }
        }
    }

    public void deleteUsuario(Usuario usuario) {

        if (usuario.getUsuaId().equals(LoginController.getInstance().getUsuario().getUsuaId())) {
            displayErrorMessageToUser("No se puede eliminar el usuario con el que ha accedido al sistema");
            return;
        }
        this.usuario = usuario;
        usuarioFacade.delete(this.usuario);
        displayInfoMessageToUser("Usuario eliminado correctamente");
        usuarios.remove(this.usuario);
        limpiaTodo();

    }

    public void limpiaTodo() {
        this.usuario = new Usuario();
       // setContrasenia("");
        setNomUsuario("");
        setRol(1);
        //contrasenia = "";
        nomUsuario = "";
        rol = 1;

    }

    ///////////////////////////////gets y sets///////////////////////////
    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = usuarioFacade.findUsersByAlmaID(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Rol> getRoles() {
        if (roles == null) {
            roles = rolFacade.findByAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        }
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Permiso getPermiso() {
        if (permiso == null) {
            permiso = LoginController.getInstance().getPermiso("Usuarios");
        }
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public void nuevoUsuario() {
        activeIndex = 1;
        limpiaTodo();
    }

    public void guardar() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        if (usuario.getUsuaId() == null) {
            if (permiso.getPermCrear()) {
                saveUsuario();
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        } else {
            if (permiso.getPermModificar()) {
                updateUsuario(usuario);
            } else {
                displayErrorMessageToUser("No tiene permiso para realizar esta acción");
                return;
            }

        }
        activeIndex = 0;
    }

    public void editarUsuario(Usuario usuario) {

        activeIndex = 1;
        this.usuario = usuario;

        setNomUsuario(this.usuario.getUsuaNombre());
        setRol(this.usuario.getRolId().getRolId());
        setContrasenia(this.usuario.getUsuaClave());

    }

}
