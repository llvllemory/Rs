package com.rasas.mbeans;

import com.rasas.entities.SubCenters;
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

public class MBSubCenters implements Serializable{
    
    private List<SubCenters> subCentersList;
    private MBLogin mBLogin = new MBLogin();
    private MBGroupMembers mBGroupMembers;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBSubCenters(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getSubCenterByCenterNoAndSubCenterNo(String subCenterNo){
        System.out.println("com.rasas.mbeans.MBSubCenters.getSubCenterBySubCenterNo()");
        
        SubCenters subCenterName = em.find(SubCenters.class, subCenterNo);
        
        if(subCenterName.getSubCenterName() != ""){
            return subCenterName.getSubCenterName();
        }else{
            return "";
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<SubCenters> getSubCentersByCenterNo(){
        System.out.println("com.rasas.mbeans.MBRsMain.getSubCentersByCenterNo()");
        
        subCentersList = new ArrayList<>();
        
        mBGroupMembers = new MBGroupMembers();
        String groupId = mBGroupMembers.getGroupIdByUserId(mBLogin.getLoggedUser().getUserId());
        
        if(groupId.equals("ADMIN")){
            
            TypedQuery<SubCenters> query = em.createQuery("SELECT s FROM SubCenters s WHERE s.subCenterName IS NOT NULL ORDER BY s.centerNo, s.subCenterNo DESC", SubCenters.class);

            subCentersList = query.getResultList();
            
        }else{
            
            TypedQuery<SubCenters> query = em.createQuery("SELECT s FROM SubCenters s WHERE s.centerNo = ?1 AND s.subCenterName IS NOT NULL ORDER BY s.subCenterNo DESC", SubCenters.class)
                    .setParameter(1, mBLogin.getLoggedUser().getUserCenter());

            subCentersList = query.getResultList();
        }
        
        return subCentersList;
    }
}
