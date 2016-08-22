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
    private String status = "true";
    private MBLogin mBLogin = new MBLogin();
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBViewPrivilege(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
////////////////////////////////////////////////////////////////////////////////
    public String getBtnPrivilege(String componentId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getBtnPrivilege()---------->");
        
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        
        List<ViewPrivilege> viewsPrivilegeList = getUserPrivilege(mBLogin.getLoggedUser().getUserId(), viewId);
      
        for(ViewPrivilege vp: viewsPrivilegeList){
            if(vp.getViewPrivilegePK().getViewId().equals(viewId)){
                                
                String add = vp.getViewPrivilegePK().getPrivilege().substring(0, 1);
                String delete = vp.getViewPrivilegePK().getPrivilege().substring(1, 2);

                if (componentId.equals("btnSave")) {
                    if (add.equals("0")) {
                        status = "true";
                    } else {
                        status = "false";
                    }

                    System.out.println("viewId = " + componentId + ", add = " + add + ", disable = " + status);
                } else if (componentId.equals("btnDelete")) {
                    if (delete.equals("0")) {
                        status = "true";
                    } else {
                        status = "false";
                    }

                    System.out.println("viewId = " + componentId + ", delete = " + add + ", disable = " + status);
                }
            }   
        }
        return status;
    }   
    
////////////////////////////////////////////////////////////////////////////////
    public List<ViewPrivilege> getUserPrivilege(String userId, String viewId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getUserPrivilege()---------->");
        
        emf.getCache().evictAll();
        TypedQuery<ViewPrivilege> query = em.createQuery("SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.userId = ?1 AND v.viewPrivilegePK.viewId = ?2", ViewPrivilege.class)
                .setParameter(1, userId)
                .setParameter(2, viewId);
        
        
        List viewsPrivilegeList = query.getResultList();
        
        return viewsPrivilegeList;
    }
        
    
////////////////////////////////////////////////////////////////////////////////
}
