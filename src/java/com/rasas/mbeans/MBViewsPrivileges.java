package com.rasas.mbeans;

import com.rasas.entities.Views;
import com.rasas.entities.ViewsPrivileges;
import com.rasas.entities.ViewsPrivilegesPK;
import com.rasas.helpers.AdvancedViewsPrivileges;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@ViewScoped

public class MBViewsPrivileges implements Serializable{
    
    
    private String groupId;
    private String privilege;
    
    private String viewId = "";
    private MBLogin mBLogin = new MBLogin();
    private MBGroupMembers mBGroupMembers = new MBGroupMembers();
    private MBViews mBViews = new MBViews();
    
    private List<ViewsPrivileges> groupViewsPrivilegesList;
    
    private AdvancedViewsPrivileges advancedViewsPrivileges;
    private List<AdvancedViewsPrivileges> advancedViewsPrivilegesList;
    
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
        
        groupViewsPrivilegesList = new ArrayList<>(); 
        advancedViewsPrivilegesList = new ArrayList<>();
        
        emf.getCache().evictAll();
        
        TypedQuery<Views> vQuery = em.createQuery("SELECT v FROM Views v", Views.class); 
        List<Views> allViewsList = vQuery.getResultList();
        
        TypedQuery<ViewsPrivileges> query = em.createQuery("SELECT v FROM ViewsPrivileges v WHERE v.viewsPrivilegesPK.groupId = ?1", ViewsPrivileges.class)
                .setParameter(1, groupId); 
        groupViewsPrivilegesList = query.getResultList();
        
        for(Views v: allViewsList){
            
            advancedViewsPrivileges = new AdvancedViewsPrivileges();
            
            advancedViewsPrivileges.setGroupId(groupId);
            advancedViewsPrivileges.setViewId(v.getViewId());
            advancedViewsPrivileges.setViewName(v.getViewName());
            
            advancedViewsPrivileges.setCanView(false);
            advancedViewsPrivileges.setCanSave(false);
            advancedViewsPrivileges.setCanDelete(false);
            advancedViewsPrivileges.setCanUpdate(false);
            advancedViewsPrivileges.setCanPrint(false);
            
            advancedViewsPrivilegesList.add(advancedViewsPrivileges);
            
        }
        
        for(ViewsPrivileges v: groupViewsPrivilegesList){
            
            for(AdvancedViewsPrivileges av: advancedViewsPrivilegesList){
                
                if(av.getViewId().equals(v.getViewsPrivilegesPK().getViewId())){
                    av.setCanView(v.getCanView().equals("true")? true: false);
                    av.setCanSave(v.getCanSave().equals("true")? true: false);
                    av.setCanDelete(v.getCanDelete().equals("true")? true: false);
                    av.setCanUpdate(v.getCanUpdate().equals("true")? true: false);
                    av.setCanPrint(v.getCanPrint().equals("true")? true: false);                   
                }
            }
        }
        
        Collections.sort(advancedViewsPrivilegesList, new Comparator<AdvancedViewsPrivileges>() {

                @Override
                public int compare(AdvancedViewsPrivileges object_1, AdvancedViewsPrivileges object_2) {
                    return object_2.getViewId().compareTo(object_1.getViewId());
                }
        });
 
    }   
        
////////////////////////////////////////////////////////////////////////////////    
    public void saveViewsPrivileges(){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.saveViewsPrivileges()");
        
        ViewsPrivileges viewsPrivileges;
        ViewsPrivilegesPK viewsPrivilegesPK;
        groupViewsPrivilegesList = new ArrayList<>(); 
        
        for(AdvancedViewsPrivileges av: advancedViewsPrivilegesList){
            
            viewsPrivileges = new ViewsPrivileges();
            viewsPrivilegesPK = new ViewsPrivilegesPK();

            viewsPrivilegesPK.setGroupId(groupId);
            viewsPrivilegesPK.setViewId(av.getViewId());
            viewsPrivileges.setViewsPrivilegesPK(viewsPrivilegesPK);

            viewsPrivileges.setCanView(av.getCanView() == true ? "true" : "false");
            viewsPrivileges.setCanSave(av.getCanSave() == true ? "true" : "false");
            viewsPrivileges.setCanDelete(av.getCanDelete() == true ? "true" : "false");
            viewsPrivileges.setCanUpdate(av.getCanUpdate() == true ? "true" : "false");
            viewsPrivileges.setCanPrint(av.getCanPrint() == true ? "true" : "false");

            groupViewsPrivilegesList.add(viewsPrivileges);
            
        }

        for (ViewsPrivileges vx : groupViewsPrivilegesList) {

            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            
            em.merge(vx);
            em.flush();
            em.clear();
            
            
            
            System.out.println(vx.getViewsPrivilegesPK().getGroupId() + ", " + vx.getViewsPrivilegesPK().getViewId() + ", " + vx.getCanView() + ", " + vx.getCanSave() + ", "
                    + vx.getCanDelete() + ", " + vx.getCanUpdate() + ", " + vx.getCanPrint());
        }
        
        em.getTransaction().commit();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getTabViewPrivilege(String tabId){
        System.out.println("com.rasas.mbeans.MBViewsPrivileges.getTabViewPrivilege()");
        
        String status = "true";
        String userGroupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        if(userGroupId.equals("ADMIN")){
            status = "false";
        }else{
            
            if(tabId.equals("tabUser")){
                status = "false";
            }else if(tabId.equals("tabGroups")){
                status = "false";
            }else if(tabId.equals("tabViews")){
                status = "true";
            }else if(tabId.equals("tabPrivileges")){
                status = "true";
            }
        }
        
        return status;
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

    public List<AdvancedViewsPrivileges> getAdvancedViewsPrivilegesList() {
        return advancedViewsPrivilegesList;
    }

    public void setAdvancedViewsPrivilegesList(List<AdvancedViewsPrivileges> advancedViewsPrivilegesList) {
        this.advancedViewsPrivilegesList = advancedViewsPrivilegesList;
    }
    
    
    
}
