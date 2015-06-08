/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.util.exp;


/**
 *
 * @author Valex
 */

public class AtributoInvalidoException extends Exception {
     private String atributo;
    private Object valor;

    public AtributoInvalidoException(String atributo, Object valor, String string) {
        super(string);
        this.atributo = atributo;
        this.valor = valor;
    }

    public AtributoInvalidoException(String atributo, Object valor, String string, Throwable thrwbl) {
        super(string, thrwbl);
        this.atributo = atributo;
        this.valor = valor;
    }

    public AtributoInvalidoException(String atributo, Object valor, Throwable thrwbl) {
        super(thrwbl);
        this.atributo = atributo;
        this.valor = valor;
    }   
}
