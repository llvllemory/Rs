package com.rasas.mbeans;

import com.rasas.entities.Views;
import com.rasas.entities.ViewsPrivileges;
import com.rasas.entities.ViewsPrivilegesPK;
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

public class MBViewsPrivileges {
    
    
    private String groupId;
    private String privilege;
    
    private String viewId = "";
    private MBLogin mBLogin = new MBLogin();
    private MBGroupMembers mBGroupMembers = new MBGroupMembers();
    private MBViews mBViews = new MBViews();
    private List<ViewsPrivileges> groupViewsPrivilegesList;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBViewsPrivileges(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }

////////////////////////////////////////////////////////////////////////////////
    public String getBtnPrivilege(String componentId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getBtnPrivilege()");
        
        String status = "true";
        
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        if(userGroupId.equals("ADMIN")){
            status = "false";
        }else{
            
            List<ViewsPrivileges> viewsPrivilegesesList = getGroupPrivilege(userGroupId, viewId.substring(7, viewId.length() - 6));

            for (ViewsPrivileges vp : viewsPrivilegesesList) {
                if (vp.getViewsPrivilegesPK().getViewId().equals(viewId.substring(7, viewId.length() - 6))) {

                    if (vp.getViewsPrivilegesPK().getViewId().equals(viewId.substring(7, viewId.length() - 6))) {
                        if (componentId.equals("btnSave")) {
                            if (vp.getCanSave().equals("false")) {
                                status = "true";
                            } else {
                                status = "false";
                            }

                        } else if (componentId.equals("btnDelete")) {
                            if (vp.getCanDelete().equals("false")) {
                                status = "true";
                            } else {
                                status = "false";
                            }
                        } else if (componentId.equals("btnUpdate")) {
                            if (vp.getCanUpdate().equals("false")) {
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

            List<ViewsPrivileges> viewsPrivilegesesList = getGroupPrivilege(userGroupId, componentId);

            for (ViewsPrivileges vp : viewsPrivilegesesList) {
                if (vp.getViewsPrivilegesPK().getViewId().equals(componentId)) {
                    
                    if (vp.getCanView().equals("true")) {
                        status = "false";
                    }
                }
            }
        }
        
        return status;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<ViewsPrivileges> getGroupPrivilege(String userId, String viewId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getGroupPrivilege()");
        
        emf.getCache().evictAll();
        TypedQuery<ViewsPrivileges> query = em.createQuery("SELECT v FROM ViewsPrivileges v WHERE v.viewsPrivilegesPK.groupId = ?1 AND v.viewsPrivilegesPK.viewId = ?2", ViewsPrivileges.class)
                .setParameter(1, userId)
                .setParameter(2, viewId);
        
        List<ViewsPrivileges> viewsPrivilegeList = query.getResultList();
        
        return viewsPrivilegeList;
    }

////////////////////////////////////////////////////////////////////////////////
    public void loadViewsPrivilegeListByGroupId(){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.loadViewsPrivilegeListByGroupId()");
        
        emf.getCache().evictAll();
        TypedQuery<Views> vQuery = em.createQuery("SELECT v FROM Views v", Views.class); 
        List<Views> allViewsList = vQuery.getResultList();
        
        TypedQuery<ViewsPrivileges> query = em.createQuery("SELECT v FROM ViewsPrivileges v WHERE v.viewsPrivilegesPK.groupId = ?1", ViewsPrivileges.class)
                .setParameter(1, groupId); 
        
        groupViewsPrivilegesList = query.getResultList();
        
        
        for(Views v: allViewsList){
            
            ViewsPrivileges tempViewPrivilege = new ViewsPrivileges();
            ViewsPrivilegesPK tempViewPrivilegePK = new ViewsPrivilegesPK();
            
            tempViewPrivilegePK.setGroupId(groupId);
            tempViewPrivilegePK.setViewId(v.getViewId());
                        
            tempViewPrivilege.setViewsPrivilegesPK(tempViewPrivilegePK);
            
            tempViewPrivilege.setViews(v);
            tempViewPrivilege.setCanView("false");
            tempViewPrivilege.setCanSave("false");
            tempViewPrivilege.setCanDelete("false");
            tempViewPrivilege.setCanUpdate("false");
            tempViewPrivilege.setCanPrint("false");
            
            if(!groupViewsPrivilegesList.contains(tempViewPrivilege)){
                groupViewsPrivilegesList.add(tempViewPrivilege);
            }
        }

        Collections.sort(groupViewsPrivilegesList, new Comparator<ViewsPrivileges>() {

                @Override
                public int compare(ViewsPrivileges object_1, ViewsPrivileges object_2) {
                    return object_2.getViewsPrivilegesPK().getViewId().compareTo(object_1.getViewsPrivilegesPK().getViewId());
                }
        });
    }   
    
////////////////////////////////////////////////////////////////////////////////
    public void saveViewsPrivileges(){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.saveViewsPrivileges()");
        
        System.out.println("---------------------------------------------------------------------------- " + groupViewsPrivilegesList);
        
        
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

    public List<ViewsPrivileges> getGroupViewsPrivilegesList() {
        return groupViewsPrivilegesList;
    }

    public void setGroupViewsPrivilegesList(List<ViewsPrivileges> groupViewsPrivilegesList) {
        this.groupViewsPrivilegesList = groupViewsPrivilegesList;
    }
    
    
    
}
