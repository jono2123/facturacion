/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.convert;

import com.jodasoft.sistfact.gco.ctr.LoginController;
import com.jodasoft.sistfact.gco.mdl.Articulo;
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
 * This convertes is used when an user select an article to add Invoice items, specifically by the autocomplete input
 */
@FacesConverter("articleConverter")
public class ArticuloConverter implements Converter {
    
    @EJB
    private com.jodasoft.sistfact.gco.dao.ArticuloFacade articuloFacade;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                
                Articulo articulo = articuloFacade.findByIdExeption(value);
                return articulo;
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
            return String.valueOf(((Articulo) object).getArtiId());
        } else {
            return null;
        }
    }
    
}
