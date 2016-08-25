package com.rasas.mbeans;

import com.rasas.entities.Centers;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped

public class MBCenters {

    EntityManager em;
    EntityManagerFactory emf;
    
    public MBCenters(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getCenterNameByCenterNo(String centerNo){
        System.out.println("com.rasas.mbeans.MBCenters.getCenterNameByCenterNo()----------> " + MBCommon.getCurrentDateTime());
        
        Centers center = em.find(Centers.class, centerNo);
        
        if(center.getCenterName() != ""){
            return center.getCenterName();
        }else{
            return "";
        }
    }
}
