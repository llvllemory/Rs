package com.rasas.advancedObjects;

import java.io.Serializable;

public class ViewPrivilegesAdvancedObject implements Serializable{
    
    private String groupId;
    private String viewId;
    private PrivilegesAdvancedObject privilege;
    
    
////////////////////////////////////////////////////////////////////////////////
  
    
    

///////////////// Getters and Setters //////////////////////////////////////////

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public PrivilegesAdvancedObject getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegesAdvancedObject privilege) {
        this.privilege = privilege;
    }

    

    

   
    
    

    
}

