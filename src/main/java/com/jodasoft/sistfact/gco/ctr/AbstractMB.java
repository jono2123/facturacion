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

 protected void closeDialog(){
  getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
 }

 protected void keepDialogOpen(){
  getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
 }

 protected RequestContext getRequestContext(){
  return RequestContext.getCurrentInstance();
 }
}
