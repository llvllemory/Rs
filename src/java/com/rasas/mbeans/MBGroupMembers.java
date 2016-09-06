package com.rasas.mbeans;

import com.rasas.entities.GroupMembers;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBGroupMembers {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBGroupMembers(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getGroupIdByUserId(String userId){
        System.out.println("com.rasas.mbeans.MBGroupMembers.getGroupIdByUserId()");
        
        TypedQuery<GroupMembers> query = em.createQuery("SELECT g FROM GroupMembers g WHERE g.groupMembersPK.userId = ?1", GroupMembers.class)
                .setParameter(1, userId);
        
        List<GroupMembers> groupIdList = query.getResultList();
        
        if(groupIdList.size() > 0){
           return groupIdList.get(0).getGroupMembersPK().getGroupId();
        }else{
            return "";
        }
    }
}
