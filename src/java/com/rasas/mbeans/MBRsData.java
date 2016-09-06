package com.rasas.mbeans;

import com.rasas.entities.RsData;
import com.rasas.entities.RsDataPK;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBRsData {
    
    private int rsFrom;
    private int rsTo;
    private String rsSubCenter;
    private int rsFound;
    
    private List<RsData> rsDataList;
    private RsData rsData;
    private RsDataPK rsDataPK;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBRsData(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }

////////////////////////////////////////////////////////////////////////////////
   public List<RsData> getRsDataByRsNoAndRsCenterAndRsYearAndRsSubCenter(int rsNo, String rsCenter, String rsYear, String rsSubCenter){
       System.out.println("com.rasas.mbeans.MBRsData.getRsDataByRsNoAndRsCenterAndRsYearAndRsSubCenter()----------> " + MBCommon.getCurrentDateTime());
       
       emf.getCache().evictAll();
       TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsNo = ?1 And r.rsDataPK.rsYear = ?2 AND r.rsDataPK.rsCenter = ?3 AND r.rsSubCenter = ?4", RsData.class)
               .setParameter(1, rsNo)
               .setParameter(2, rsYear)
               .setParameter(3, rsYear)
               .setParameter(4, rsSubCenter);
       
       List rasasData = query.getResultList();

       return rasasData;
   }
    
////////////////////////////////////////////////////////////////////////////////
    public List<RsData> getRsDataByRsSubCenterAndRsYear(String rsSubCenter, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsData.getRsDataByRsSubCenterAndRsYear()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsYear = ?1 AND r.rsSubCenter = ?2", RsData.class)
                .setParameter(1, rsYear)
                .setParameter(2, rsSubCenter);
        
        List rasasData = query.getResultList();
        
        return rasasData;
    }
////////////////////////////////////////////////////////////////////////////////
    public List<RsData> getNullRsDataByRsSubCenterAndRsYear(String rsSubCenter, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsData.getNullRsDataByRsSubCenterAndRsYear()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsTasUserId IS NULL AND r.rsDataPK.rsYear = ?1 AND r.rsSubCenter = ?2", RsData.class)
                .setParameter(1, rsYear)
                .setParameter(2, rsSubCenter);
        
        List rasasData = query.getResultList();
        
        return rasasData;
    }
////////////////////////////////////////////////////////////////////////////////
    public int saveRsData(List<RsData> rsDataList){
        System.out.println("com.rasas.mbeans.MBRsData.saveRsData()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        int rows = 0;
        
        for(RsData rs: rsDataList){
            em.persist(rs);
            rows++;
        }
        
        em.getTransaction().commit();

        System.out.println("---------- RsData saved rows ------------------> " + rows + " >> " + MBCommon.getCurrentDateTime());
        return rows;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int updateRsData(RsData rsData){
        System.out.println("com.rasas.mbeans.MBRsData.updateRsData()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        int rows = 0;
        
        
        
        System.out.println("---------- RsData updated rows ----------------> " + rows + " >> " + MBCommon.getCurrentDateTime());
        return rows;  
    }

////////////////////////////////////////////////////////////////////////////////
    public int removeRsDataByRsCenterAndRsYear(int rsFrom, int rsTo, String rsCenter, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsData.removeRsDataByRsCenterAndRsYear()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        int rows = 0;

        for(int i = rsFrom; i <= rsTo; i++){
            
            rsDataPK = new RsDataPK();
            
            rsDataPK.setRsNo(i);
            rsDataPK.setRsYear(rsYear);
            rsDataPK.setRsCenter(rsCenter);
            
            rsData = new RsData();
            rsData = em.find(RsData.class, rsDataPK);
            
            em.remove(rsData);
            em.getTransaction().commit();
            rows++;
            em.getTransaction().begin();
            
            
//            Query query = em.createQuery("DELETE FROM RsData r WHERE r.rsDataPK.rsNo = ?1 AND r.rsDataPK.rsYear = ?2 AND r.rsSubCenter = ?3")
//                    .setParameter(1, i)
//                    .setParameter(2, rsYear)
//                    .setParameter(3, rsSubCenter);
//            
//            rows += query.executeUpdate();
        }
        
        System.out.println("---------- RsData removed rows ----------------> " + rows + " >> " + MBCommon.getCurrentDateTime());
        return rows;
    }
//////////////////// Getters and Setters ///////////////////////////////////////

    public int getRsFrom() {
        return rsFrom;
    }

    public void setRsFrom(int rsFrom) {
        this.rsFrom = rsFrom;
    }

    public int getRsTo() {
        return rsTo;
    }

    public void setRsTo(int rsTo) {
        this.rsTo = rsTo;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    }
    
}
