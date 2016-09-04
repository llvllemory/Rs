package com.rasas.mbeans;

import com.rasas.entities.Centers;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBCenters {

    private MBLogin mBLogin = new MBLogin();
    
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
    
////////////////////////////////////////////////////////////////////////////////
    public List<Centers> getCenters(){
        System.out.println("com.rasas.mbeans.MBCenters.getCenters()----------> " + MBCommon.getCurrentDateTime());
        
        TypedQuery<Centers> query = em.createQuery("SELECT c FROM Centers c WHERE c.centerNo = ?1", Centers.class)
                .setParameter(1, mBLogin.getLoggedUser().getUserCenter());
        
        List<Centers> centers = query.getResultList();
        
        if(centers.size() > 0){
            return centers;
        }else{
            return null;
        }
    }
        
}
