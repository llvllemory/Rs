package com.rasas.mbeans;

import com.rasas.entities.ViewPrivilege;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBViewPrivilege {
    
    private String viewId = "";
    private MBLogin mBLogin = new MBLogin();
    private MBGroupMembers mBGroupMembers = new MBGroupMembers();
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBViewPrivilege(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    ////////////// Privileges
    ////////////// v: menu item of page 
    ////////////// a: btnAdd
    ////////////// d: btnDelete
    ////////////// u: btnUpdate
    
    
////////////////////////////////////////////////////////////////////////////////
    public String getBtnPrivilege(String componentId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getBtnPrivilege()----------> " + MBCommon.getCurrentDateTime());
        
        String status = "true";
        
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        List<ViewPrivilege> viewsPrivilegeList = getGroupPrivilege(userGroupId, viewId.substring(1, viewId.length()-6));
        
        for (ViewPrivilege vp : viewsPrivilegeList) {
            if (vp.getViewPrivilegePK().getViewId().equals(viewId.substring(1, viewId.length()-6))) {
                
                String view   = vp.getViewPrivilegePK().getPrivilege().substring(0, 1);
                String add    = vp.getViewPrivilegePK().getPrivilege().substring(1, 2);
                String delete = vp.getViewPrivilegePK().getPrivilege().substring(2, 3);
                String update = vp.getViewPrivilegePK().getPrivilege().substring(3, 4);
                
                if(view.equals("1")){
                    if (componentId.equals("btnSave")) {
                        if (add.equals("0")) {
                            status = "true";
                        } else {
                            status = "false";
                        }

                    } else if (componentId.equals("btnDelete")) {
                        if (delete.equals("0")) {
                            status = "true";
                        } else {
                            status = "false";
                        }
                    } else if (componentId.equals("btnUpdate")) {
                        if (update.equals("0")) {
                            status = "true";
                        } else {
                            status = "false";
                        }
                    }
                }
            }
        }
        
        System.out.println("viewId = " + viewId + ", componentId = " + componentId + ", disabled = " + status);
        return status;
    }   

////////////////////////////////////////////////////////////////////////////////
    public String getMenuPrivilege(String componentId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getMenuPrivilege()----------> " + MBCommon.getCurrentDateTime());
        
        String status = "true";
        
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        List<ViewPrivilege> viewsPrivilegeList = getGroupPrivilege(userGroupId, componentId);
        
        for (ViewPrivilege vp : viewsPrivilegeList) {
            if (vp.getViewPrivilegePK().getViewId().equals(componentId)) {
                String view = vp.getViewPrivilegePK().getPrivilege().substring(0, 1);

                if (view.equals("1")) {
                    status = "false";
                }
            }
        }
    
        System.out.println("componentId = " + componentId + ", disabled = " + status);
        return status;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<ViewPrivilege> getGroupPrivilege(String userId, String viewId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getGroupPrivilege()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        TypedQuery<ViewPrivilege> query = em.createQuery("SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.userId = ?1 AND v.viewPrivilegePK.viewId = ?2", ViewPrivilege.class)
                .setParameter(1, userId)
                .setParameter(2, viewId);
        
        List viewsPrivilegeList = query.getResultList();
        
        return viewsPrivilegeList;
    }
}
