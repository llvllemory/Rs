package com.rasas.mbeans;

import com.rasas.entities.Centers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBCenters implements Serializable{

    private MBLogin mBLogin = new MBLogin();
    private List<Centers> centers;
    private MBGroupMembers mBGroupMembers;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    
    public MBCenters(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getCenterNameByCenterNo(String centerNo){
        System.out.println("com.rasas.mbeans.MBCenters.getCenterNameByCenterNo()");
        
        Centers center = em.find(Centers.class, centerNo);
        
        if(center.getCenterName() != ""){
            return center.getCenterName();
        }else{
            return "";
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<Centers> getCenters(){
        System.out.println("com.rasas.mbeans.MBCenters.getCenters()");
        
        centers = new ArrayList<>();
        
        mBGroupMembers = new MBGroupMembers();
        String groupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
         
        if(groupId.equals("ADMIN") || groupId.equals("CENTERS")){
            
            TypedQuery<Centers> query = em.createQuery("SELECT c FROM Centers c ORDER BY c.centerNo ASC", Centers.class);
            centers = query.getResultList();
        
        }else{
            
            TypedQuery<Centers> query = em.createQuery("SELECT c FROM Centers c WHERE c.centerNo = ?1 ORDER BY c.centerNo ASC", Centers.class)
                .setParameter(1, mBLogin.getLoggedUser().getUserCenter());
        
            centers = query.getResultList();
        }
    
        return centers;   
    }        
}
