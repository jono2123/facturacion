/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.mdl.Almacen;
import com.jodasoft.sistfact.gco.mdl.AlmacenModulo;
import com.jodasoft.sistfact.gco.mdl.Modulo;
import com.jodasoft.sistfact.gco.mdl.Permiso;
import com.jodasoft.sistfact.gco.mdl.Rol;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import com.jodasoft.sistfact.gco.mdl.Ventana;
import com.jodasoft.sistfact.gco.util.ValidarAtributoUtil;
import com.jodasoft.sistfact.gco.util.exp.AlmacenValidadorException;
import com.jodasoft.sistfact.gco.util.exp.AtributoInvalidoException;
import com.jodasoft.sistfact.gco.util.exp.UsuarioValidadorException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author javila
 */
@Stateless
public class AlmacenFacade extends AbstractFacade<Almacen> {

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    AlmacenModuloFacade almoFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    PermisoFacade permisoFacade;

    @EJB
    VentanaFacade ventanaFacade;

    public AlmacenFacade() {
        super(Almacen.class);
    }

    public void update(Almacen almacen) throws AlmacenValidadorException {
        try {
            ValidarAtributoUtil.validarStringNuloVacio("Nombre", almacen.getAlmaNombre());
            ValidarAtributoUtil.validarStringNuloVacio("RUC", almacen.getAlmaRuc());
            ValidarAtributoUtil.validarDoubleNegativo("IVA", almacen.getAlmaIva().doubleValue());
            ValidarAtributoUtil.validarDoubleCero("IVA", almacen.getAlmaIva().doubleValue());

            edit(almacen);
        } catch (AtributoInvalidoException ex) {
            throw new AlmacenValidadorException(ex);
        }

    }

    public void save(Almacen almacen, List<Modulo> modulos, Rol rol, Usuario usuario) throws UsuarioValidadorException {
        create(almacen);
        List<Ventana> ventanas = new ArrayList<Ventana>();
        for (Modulo modulo : modulos) {
            AlmacenModulo almo = new AlmacenModulo();
            almo.setAlmaId(almacen);
            almo.setModuId(modulo);
            almoFacade.create(almo);
            ventanas.addAll(modulo.getVentanaList());
        }
        rol.setAlmaId(almacen);
        rolFacade.create(rol);

        usuario.setRolId(rol);
        usuarioFacade.save(usuario);
        for (Ventana ventana : ventanas) {
            Permiso permiso = new Permiso();
            permiso.setPermConsultar(true);
            permiso.setPermCrear(true);
            permiso.setPermEliminar(true);
            permiso.setPermModificar(true);
            permiso.setRolId(rol);
            permiso.setVentId(ventana);
            permisoFacade.create(permiso);
        }
    }

}
