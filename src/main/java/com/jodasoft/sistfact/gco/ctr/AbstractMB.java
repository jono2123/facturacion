/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr;

/**
 *
 * @author javila
 */
import com.jodasoft.sistfact.gco.ctr.util.JSFMessageUtil;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import org.primefaces.context.RequestContext;

public class AbstractMB {

    private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

    public AbstractMB() {
        super();
    }

    protected void displayErrorMessageToUser(String message) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendErrorMessageToUser(message);
    }

    protected void displayInfoMessageToUser(String message) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendInfoMessageToUser(message);
    }

    protected void closeDialog() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
    }

    protected void keepDialogOpen() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
    }

    protected RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    private DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");

    public double redondear(double num) {
        num *= 100.0D;
        num = Math.round(num);
        int numr = (int) num;
        return numr / 100.0D;
    }

    public String Formato(double num) {
        DecimalFormatSymbols symbols = df2.getDecimalFormatSymbols();
        if (LoginController.getInstance().getUsuario().getRolId().getAlmaId().getAlmaComa()) {
            symbols.setDecimalSeparator(',');
        }else{
            symbols.setDecimalSeparator('.');
        }
        df2.setDecimalFormatSymbols(symbols);
        return df2.format(num);
    }

    public boolean filterByValor(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

    public String formateaFecha(Date fec) {
        return fec.toLocaleString().substring(0, fec.toLocaleString().indexOf(" "));
    }
}
