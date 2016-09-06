package com.rasas.mbeans;

import com.rasas.entities.SubCenters;
import com.rasas.entities.SubCentersPK;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBSubCenters {
    
    private MBLogin mBLogin = new MBLogin();
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBSubCenters(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getSubCenterByCenterNoAndSubCenterNo(String centerNo, String subCenterNo){
        System.out.println("com.rasas.mbeans.MBSubCenters.getSubCenterBySubCenterNo()");
        
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
    
////////////////////////////////////////////////////////////////////////////////
    public List<SubCenters> getSubCentersByCenterNo(){
        System.out.println("com.rasas.mbeans.MBRsMain.getSubCentersByCenterNo()");
        
        TypedQuery<SubCenters> query = em.createQuery("SELECT s FROM SubCenters s WHERE s.subCentersPK.centerNo = ?1 AND s.subCenterName IS NOT NULL ORDER BY s.subCentersPK.subCenterNo DESC", SubCenters.class)
                .setParameter(1, mBLogin.getLoggedUser().getUserCenter());
        
        List<SubCenters> subCentersList = query.getResultList();
        
        if(subCentersList.size() > 0){
            return subCentersList;
        }else{
            return null;
        }
    }  
}
