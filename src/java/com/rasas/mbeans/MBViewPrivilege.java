package com.rasas.mbeans;

import com.rasas.advancedObjects.PrivilegesAdvancedObject;
import com.rasas.advancedObjects.ViewPrivilegesAdvancedObject;
import com.rasas.entities.ViewPrivilege;
import com.rasas.entities.ViewPrivilegePK;
import com.rasas.entities.Views;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    
    private String groupId;
    private String privilege;
    
    private String viewId = "";
    private MBLogin mBLogin = new MBLogin();
    private MBGroupMembers mBGroupMembers = new MBGroupMembers();
    
    private PrivilegesAdvancedObject privilegesAdvancedObject;
    private ViewPrivilegesAdvancedObject viewPrivilegesAdvancedObject;
    private List<ViewPrivilegesAdvancedObject> viewPrivilegesAdvancedObjectList = new ArrayList<>();
    
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
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getBtnPrivilege()");
        
        String status = "true";
        
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        if(userGroupId.equals("ADMIN")){
            status = "false";
        }else{
            
            List<ViewPrivilege> viewsPrivilegeList = getGroupPrivilege(userGroupId, viewId.substring(7, viewId.length() - 6));

            for (ViewPrivilege vp : viewsPrivilegeList) {
                if (vp.getViewPrivilegePK().getViewId().equals(viewId.substring(7, viewId.length() - 6))) {

                    String view = vp.getPrivilege().substring(0, 1);
                    String add = vp.getPrivilege().substring(1, 2);
                    String delete = vp.getPrivilege().substring(2, 3);
                    String update = vp.getPrivilege().substring(3, 4);

                    if (view.equals("1")) {
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
        }
       
        System.out.println("viewId = " + viewId + ", componentId = " + componentId + ", disabled = " + status);
        return status;
    }   

////////////////////////////////////////////////////////////////////////////////
    public String getMenuPrivilege(String componentId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getMenuPrivilege()");
        
        String status = "true";
        
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        if (userGroupId.equals("ADMIN")) {
            status = "false";
        } else {

            List<ViewPrivilege> viewsPrivilegeList = getGroupPrivilege(userGroupId, componentId);

            for (ViewPrivilege vp : viewsPrivilegeList) {
                if (vp.getViewPrivilegePK().getViewId().equals(componentId)) {
                    String view = vp.getPrivilege().substring(0, 1);

                    if (view.equals("1")) {
                        status = "false";
                    }
                }
            }
        }
        System.out.println("componentId = " + componentId + ", disabled = " + status);
        return status;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<ViewPrivilege> getGroupPrivilege(String userId, String viewId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getGroupPrivilege()");
        
        emf.getCache().evictAll();
        TypedQuery<ViewPrivilege> query = em.createQuery("SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.groupId = ?1 AND v.viewPrivilegePK.viewId = ?2", ViewPrivilege.class)
                .setParameter(1, userId)
                .setParameter(2, viewId);
        
        List viewsPrivilegeList = query.getResultList();
        
        return viewsPrivilegeList;
    }

////////////////////////////////////////////////////////////////////////////////
    public void loadViewsPrivilegeListByGroupId(){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.loadViewsPrivilegeListByGroupId()");
        
        TypedQuery<Views> vQuery = em.createQuery("SELECT v FROM Views v", Views.class); 
        List<Views> allViewsList = vQuery.getResultList();
        
        TypedQuery<ViewPrivilege> query = em.createQuery("SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.groupId = ?1", ViewPrivilege.class)
                .setParameter(1, groupId); 
        List<ViewPrivilege> viewsPrivilegeList = query.getResultList();
        
        
        for(Views v: allViewsList){
            ViewPrivilege tempViewPrivilege = new ViewPrivilege();
            ViewPrivilegePK tempViewPrivilegePK = new ViewPrivilegePK();
            
            tempViewPrivilegePK.setGroupId(groupId);
            tempViewPrivilegePK.setViewId(v.getViewId());
            
            tempViewPrivilege.setViewPrivilegePK(tempViewPrivilegePK);
            tempViewPrivilege.setPrivilege("000000");
            
            if(!viewsPrivilegeList.contains(tempViewPrivilege)){
                viewsPrivilegeList.add(tempViewPrivilege);
            }
        }

        
        for(ViewPrivilege v: viewsPrivilegeList){
    
            privilegesAdvancedObject = new PrivilegesAdvancedObject();
            viewPrivilegesAdvancedObject = new ViewPrivilegesAdvancedObject();
            
            viewPrivilegesAdvancedObject.setGroupId(v.getViewPrivilegePK().getGroupId());
            viewPrivilegesAdvancedObject.setViewId(v.getViewPrivilegePK().getViewId());
            
            System.out.println(v.getViewPrivilegePK().getViewId() + " : " + v.getPrivilege().substring(0, 1) + " -- " + v.getPrivilege().substring(1, 2) + " -- " + 
                    v.getPrivilege().substring(2, 3) + " -- " + v.getPrivilege().substring(3, 4) + " -- " + v.getPrivilege().substring(4, 5) + " -- " + v.getPrivilege().substring(5, 6));
            

            if(v.getPrivilege().substring(0, 1).equals("1")){
                privilegesAdvancedObject.setView(true);
            }else{
                privilegesAdvancedObject.setView(false);
            }
            
            if(v.getPrivilege().substring(1, 2).equals("1")){
                privilegesAdvancedObject.setAdd(true);
            }else{
                privilegesAdvancedObject.setAdd(false);
            }
            
            if(v.getPrivilege().substring(2, 3).equals("1")){
                privilegesAdvancedObject.setDelete(true);
            }else{
                privilegesAdvancedObject.setDelete(false);
            }
            
            if(v.getPrivilege().substring(3, 4).equals("1")){
                privilegesAdvancedObject.setUpdate(true);
            }else{
                privilegesAdvancedObject.setUpdate(false);
            }
            
            if(v.getPrivilege().substring(4, 5).equals("1")){
                privilegesAdvancedObject.setNull1(true);
            }else{
                privilegesAdvancedObject.setNull1(false);
            }
            
            if(v.getPrivilege().substring(5, 6).equals("1")){
                privilegesAdvancedObject.setNull2(true);
            }else{
                privilegesAdvancedObject.setNull2(false);
            }
            
            viewPrivilegesAdvancedObject.setPrivilege(privilegesAdvancedObject);
            viewPrivilegesAdvancedObjectList.add(viewPrivilegesAdvancedObject);
            
            
            //// Sorting the List by viewId
            Collections.sort(viewPrivilegesAdvancedObjectList, new Comparator<ViewPrivilegesAdvancedObject>() {

                @Override
                public int compare(ViewPrivilegesAdvancedObject object_1, ViewPrivilegesAdvancedObject object_2) {
                    return object_2.getViewId().compareTo(object_1.getViewId());
                }
            });
        }
    }   
    
////////////////////////////////////////////////////////////////////////////////
    public void saveViewsPrivileges(){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.saveViewsPrivileges()");
        
        System.out.println("------------------------------- " + viewPrivilegesAdvancedObjectList.size());
        for(ViewPrivilegesAdvancedObject v: viewPrivilegesAdvancedObjectList){
            System.out.println(v.getGroupId() + "" + v.getViewId() + "" + v.getPrivilege().isView() + "" + v.getPrivilege().isAdd() + "" + v.getPrivilege().isDelete()
                               + "" + v.getPrivilege().isUpdate());
        }
        
        
    }
    
/////////////////////////////////Getters and Setters ///////////////////////////

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }    

    public ViewPrivilegesAdvancedObject getViewPrivilegesAdvancedObject() {
        return viewPrivilegesAdvancedObject;
    }

    public void setViewPrivilegesAdvancedObject(ViewPrivilegesAdvancedObject viewPrivilegesAdvancedObject) {
        this.viewPrivilegesAdvancedObject = viewPrivilegesAdvancedObject;
    }
    
    public List<ViewPrivilegesAdvancedObject> getViewPrivilegesAdvancedObjectList() {
        return viewPrivilegesAdvancedObjectList;
    }

    public void setViewPrivilegesAdvancedObjectList(List<ViewPrivilegesAdvancedObject> viewPrivilegesAdvancedObjectList) {
        this.viewPrivilegesAdvancedObjectList = viewPrivilegesAdvancedObjectList;
    }
}
