package com.rasas.mbeans;

import com.rasas.entities.ViewPrivilege;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped

public class MBPrivilege implements Serializable{
    
    private String viewId = "";
    private String status = "true";
    private MBViewPrivilege mBViewPrivilege;
 
    
    public MBPrivilege(){
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        mBViewPrivilege = new MBViewPrivilege(); 
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String btnDisabled(String componentId){
        System.out.println("com.rasas.mbeans.MBPrivilege.btnDisabled()---------->");
        
        List<ViewPrivilege> viewsPrivilegeList = mBViewPrivilege.getUserPrivilege("33476", viewId);
        
        for(ViewPrivilege vp: viewsPrivilegeList){
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx getViewId = " + vp.getViewId() + "  ---- viewId = " + viewId);
            
            if(vp.getViewId().equals(viewId)){
                
                System.out.println("---------------->>>>>>>> getViewId = " + vp.getViewId() + "  ---- viewId = " + viewId);
                
                String add = vp.getPrivilege().substring(0, 1);
                String delete = vp.getPrivilege().substring(1, 2);

                if (componentId.equals("btnSave")) {
                    if (add.equals("0")) {
                        status = "true";
                    } else {
                        status = "false";
                    }

                    System.out.println("id = " + componentId + ", add = " + add + ", status = " + status);
                } else if (componentId.equals("btnDelete")) {
                    if (delete.equals("0")) {
                        status = "true";
                    } else {
                        status = "false";
                    }

                    System.out.println("id = " + componentId + ", delete = " + add + ", status = " + status);
                }
            }   
        }
        return status;
    }   
////////////////////////////////////////////////////////////////////////////////    
}
