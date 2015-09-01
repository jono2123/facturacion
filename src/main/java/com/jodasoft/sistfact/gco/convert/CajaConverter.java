/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.convert;

import com.jodasoft.sistfact.gco.ctr.LoginController;
import com.jodasoft.sistfact.gco.mdl.Articulo;
import com.jodasoft.sistfact.gco.mdl.Caja;
import static com.jodasoft.sistfact.gco.mdl.ItemVendido_.codigo;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Freddy Sumba
 *
 */
@FacesConverter("cajaConverter")
public class CajaConverter implements Converter {

    @EJB
    private com.jodasoft.sistfact.gco.dao.CajaFacade cajaFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {

                Caja caja = cajaFacade.findByIdCaja(Integer.parseInt(value.trim()));
                return caja;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                String aux = String.valueOf((String) object).trim();
                return aux;
            } catch (java.lang.ClassCastException e) {
                String aux = String.valueOf(((Caja) object).getCajaId());
                return aux;
            }
        } else {
        }

        return null;
    }

}
