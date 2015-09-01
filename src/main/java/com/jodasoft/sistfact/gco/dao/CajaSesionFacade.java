/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.dao;

import com.jodasoft.sistfact.gco.dao.AbstractFacade;
import com.jodasoft.sistfact.gco.mdl.Caja;
import com.jodasoft.sistfact.gco.mdl.CajaSesion;
import com.jodasoft.sistfact.gco.mdl.Usuario;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author f35
 */
@Stateless
public class CajaSesionFacade extends AbstractFacade<CajaSesion> {

    @PersistenceContext(unitName = "com.jodasoft_sistFact_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CajaSesionFacade() {
        super(CajaSesion.class);
    }

    public boolean findSingIn(Caja caja) {
        CajaSesion aux = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("caseNumCaja", caja);
        parameters.put("caseEstado", "Abierta");
        aux = findOneResult("Caja.findByCajaIdAndEstado", parameters);
        if (aux == null) {
            return true;
        }
        return false;
    }

    public CajaSesion findSesion(Caja caja) {
        CajaSesion aux = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("caseNumCaja", caja);
        parameters.put("caseEstado", "Abierta");
        aux = findOneResult("Caja.findByCajaIdAndEstado", parameters);

        return aux;
    }

    public void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
            cookie.setPath(request.getContextPath());

        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public Cookie getCookie(String name) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    return cookie;
                }
            }
        }
        return null;
    }

    public CajaSesion findByUser(Usuario usuario) {
        CajaSesion aux = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("usuaId", usuario);
        parameters.put("caseEstado", "Abierta");
        List<CajaSesion> lista = findAllResults("Caja.findByCajaUsuarioAndEstado", parameters);
        if (lista.size() == 1) {
            return lista.get(0);
        } else {

            Cookie c = getCookie("sesion_jodasoft_" + usuario.getUsuaNombre());
            if (c != null) {
                if (Integer.parseInt(c.getValue()) != 0) {
                    for (CajaSesion sesion : lista) {
                        if (sesion.getCaseId() == Integer.parseInt(c.getValue())) {
                            return sesion;
                        }
                    }
                    for (CajaSesion sesion : lista) {
                        sesion.setCaseEstado("Cerrada por Sistema");
                        sesion.setCaseMontoCierre(0.0);
                        Calendar calendar = Calendar.getInstance();
                        sesion.setCaseTimeCierre(calendar.getTime());
                        edit(sesion);
                    }
                }
            }

        }

        return aux;
    }

    public List<CajaSesion> openBoxesByUser(Usuario usuario) {
        CajaSesion aux = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("usuaId", usuario);
        parameters.put("caseEstado", "Abierta");
        List<CajaSesion> lista = findAllResults("Caja.findByCajaUsuarioAndEstado", parameters);
        return lista;
    }

}
