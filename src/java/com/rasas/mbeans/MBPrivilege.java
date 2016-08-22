package com.rasas.mbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped

public class MBPrivilege implements Serializable{
    
    String viewId = "";
    String privilege = "100000";
    
    
    public MBPrivilege(){
        //viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String btnDisabled(String componentId){
        System.out.println("com.rasas.mbeans.MBPrivilege.isDisabled()---------->");
        
        String status = "";
        
        String add    = privilege.substring(0, 1);
        String delete = privilege.substring(1, 2);
        
        if (componentId.equals("btnSave")){
            if (add.equals("0")) {
                status = "true";
            } else {
                status = "false";
            }
            
            System.out.println("id = " + componentId + ", add = " + add + ", status = " + status);
        }else if (componentId.equals("btnDelete")){
            if (delete.equals("0")) {
                status = "true";
            } else {
                status = "false";
            }
            
            System.out.println("id = " + componentId + ", delete = " + add + ", status = " + status);
        }
      
        return status;
    }   
}
