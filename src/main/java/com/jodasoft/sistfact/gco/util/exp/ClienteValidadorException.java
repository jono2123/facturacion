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
public class ClienteValidadorException extends Exception {

    public ClienteValidadorException() {
    }

    public ClienteValidadorException(String message) {
        super(message);
    }

    public ClienteValidadorException(Throwable cause) {
        super(cause);
    }

    public ClienteValidadorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
