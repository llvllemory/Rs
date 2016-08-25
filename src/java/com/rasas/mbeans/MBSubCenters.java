package com.rasas.mbeans;

import com.rasas.entities.SubCenters;
import com.rasas.entities.SubCentersPK;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped

public class MBSubCenters {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBSubCenters(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getSubCenterByCenterNoAndSubCenterNo(String centerNo, String subCenterNo){
        System.out.println("com.rasas.mbeans.MBSubCenters.getSubCenterBySubCenterNo() ----------> " + MBCommon.getCurrentDateTime());
        
        SubCentersPK subCentersPK = new SubCentersPK();
        subCentersPK.setCenterNo(centerNo);
        subCentersPK.setSubCenterNo(subCenterNo);
        
        SubCenters subCenterName = em.find(SubCenters.class, subCentersPK);
        
        if(subCenterName.getSubCenterName() != ""){
            return subCenterName.getSubCenterName();
        }else{
            return "";
        }
    }
    
}
