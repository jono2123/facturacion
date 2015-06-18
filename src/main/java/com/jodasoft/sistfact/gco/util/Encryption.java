/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jodasoft.sistfact.gco.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Marcelo
 */
public class Encryption {
    static public String sha256(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
