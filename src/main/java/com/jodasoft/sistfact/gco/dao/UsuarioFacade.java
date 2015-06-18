/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import com.jodasoft.sistfact.gco.util.Encryption;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.UsuarioValidadorException;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void save(Usuario usuario) throws UsuarioValidadorException {
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre de Usuario", usuario.getUsuaNombre());
            ValidarAtributoUtil.validarStringNuloVacio("Contraseña", usuario.getUsuaClave());
            usuario.setUsuaClave(Encryption.sha256(usuario.getUsuaClave()));
            create(usuario);
        } catch (AtributoInvalidoException ex) {
            throw new UsuarioValidadorException(ex);
        }
    }

    public void update(Usuario usuario) throws UsuarioValidadorException {
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre de Usuario", usuario.getUsuaNombre());
            ValidarAtributoUtil.validarStringNuloVacio("Contraseña", usuario.getUsuaClave());
            usuario.setUsuaClave(Encryption.sha256(usuario.getUsuaClave()));
            edit(usuario);
        } catch (AtributoInvalidoException ex) {
            throw new UsuarioValidadorException(ex);
        }
    }

    public void delete(Usuario usuario) {
        usuario.setUsuaEstado(false);
        edit(usuario);
    }

    public Usuario findByUserAndPass(String user, String pass) {
        Usuario usuario;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("usuaNombre", user);
        parameters.put("usuaClave", Encryption.sha256(pass));
        parameters.put("usuaEstado", true);
        usuario = findOneResult("Usuario.findByUsuaNombreAndUsuaClaveAndEstado", parameters);
        return usuario;
    }

    public List<Usuario> findUsersByAlmaID(Almacen almaId) {
        List<Usuario> usuarios;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("usuaEstado", true);
        parameters.put("almaId", almaId);

        usuarios = findAllResults("Usuario.findByUsuaEstadoAndUsuaAlmaId", parameters);
        return usuarios;
    }

}
