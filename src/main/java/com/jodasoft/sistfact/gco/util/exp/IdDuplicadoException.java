/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.util.exp;

/**
 *
 * @author javila
 */
public class IdDuplicadoException extends Exception{

    public IdDuplicadoException() {
    }

    public IdDuplicadoException(String message) {
        super(message);
    }

    public IdDuplicadoException(Throwable cause) {
        super(cause);
    }

    public IdDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
