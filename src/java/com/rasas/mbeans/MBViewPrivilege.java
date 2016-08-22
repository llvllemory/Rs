package com.rasas.mbeans;

import com.rasas.entities.ViewPrivilege;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBViewPrivilege {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBViewPrivilege(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<ViewPrivilege> getUserPrivilege(String userId, String viewId){
        System.out.println("com.rasas.mbeans.MBViewPrivilege.getUserPrivilege()---------->");
        
        TypedQuery<ViewPrivilege> query = em.createQuery("SELECT v FROM ViewPrivilege v WHERE v.userId = ?1 AND v.viewId = ?2", ViewPrivilege.class)
                .setParameter(1, userId)
                .setParameter(2, viewId);
        
        
        List<ViewPrivilege> viewsPrivilege = query.getResultList();
        
        for(ViewPrivilege vp: viewsPrivilege){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa getViewId = " + vp.getViewId());
        }
        
        return viewsPrivilege;
    }
}
