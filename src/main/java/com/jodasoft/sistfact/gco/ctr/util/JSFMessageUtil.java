/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jodasoft.sistfact.gco.ctr.util;

/**
 *
 * @author javila
 */
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class JSFMessageUtil {
 public void sendInfoMessageToUser(String message) {
  FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message);
  addMessageToJsfContext(facesMessage);
 }

 public void sendErrorMessageToUser(String message) {
  FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, message);
  addMessageToJsfContext(facesMessage);
 }

 private FacesMessage createMessage(Severity severity, String mensagemErro) {
  return new FacesMessage(severity, mensagemErro, mensagemErro);
 }

 private void addMessageToJsfContext(FacesMessage facesMessage) {
  FacesContext.getCurrentInstance().addMessage(null, facesMessage);
 }
}