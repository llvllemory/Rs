package com.rasas.mbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped

public class MBCommon {

    public MBCommon(){
        
    }
    
    public static void getErrorMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageLabel, message));
   }
   
   public static void getInfoMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageLabel, message));
   }
   
   public static void getFatalMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, messageLabel, message));
   }
   
   public static void getWarnMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, messageLabel, message));
   }
   
   public static String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
   }
   
   public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
   }
   
   public static String getCurrentYear(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
   }
}
