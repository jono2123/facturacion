/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author javila
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController extends AbstractMB implements Serializable {

    /**
     * Creates a new instance of UsuarioController
     */
    private String nomUsuario;
    private String contrasenia;
    private int rol;

    private Usuario usuario;
    private List<Usuario> usuarios;
    private List<Rol> roles;

    @EJB
    private com.jodasoft.sistfact.gco.dao.UsuarioFacade usuarioFacade;

    @EJB
    private com.jodasoft.sistfact.gco.dao.RolFacade rolFacade;
    
    
    public UsuarioController() {
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

    public void updateUsuario() {
        try {
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

    public void deleteUsuario() {
        if (usuario.getUsuaId().equals(LoginController.getInstance().getUsuario().getUsuaId())) {
            displayErrorMessageToUser("No se puede eliminar el usuario con el que ha accedido al sistema");
            return ;
        }
        usuarioFacade.delete(usuario);
        displayInfoMessageToUser("Usuario eliminado correctamente");
        usuarios.remove(usuario);
        limpiaTodo();
        
    }

    public void limpiaTodo() {
        setContrasenia("");
        setNomUsuario("");
        setRol(1);
        usuario = new Usuario();
    }

    ///////////////////////////////gets y sets///////////////////////////
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
        if(roles==null)
            roles = rolFacade.findByAlmaId(LoginController.getInstance().getUsuario().getRolId().getAlmaId());
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

}
