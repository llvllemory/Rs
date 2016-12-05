package com.rasas.mbeans;

import com.rasas.entities.RsData;
import com.rasas.entities.RsDataPK;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBRsData implements Serializable{
    
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
    public List<RsData> getRsDataByRsYearRsCenterRsSubCenterRsFromRsTo(String rsYear, String rsCenter, String rsSubCenter, int rsFrom, int rsTo){
        System.out.println("com.rasas.mbeans.MBRsData.getRsDataByRsYearRsCenterRsSubCenterRsFromRsTo()");
       
        emf.getCache().evictAll();
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsYear = ?1 AND r.rsDataPK.rsCenter = ?2 AND r.rsSubCenter =?3 AND r.rsDataPK.rsNo BETWEEN ?4 AND ?5", RsData.class)
                .setParameter(1, rsYear)
                .setParameter(2, rsCenter)
                .setParameter(3, rsSubCenter)
                .setParameter(4, rsFrom)
                .setParameter(5, rsTo);

        return query.getResultList();
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
    public int rsDataClose(int rsFrom, int rsTo, String rsYear, String rsCenter, String rsSubCenter, int rsTasDocNo, 
            String rsTasDocYear, int rsTasDocType, Date rsTasDate, String rsTasUserId, String rsTasNote, String rsTasCarNo, String rsTasCarNat, 
            int rsCarWeight, String rsCtnNo, int rsCtnWeight, int rsGrossWeight, int rsInspDoc, String rsTasScreen){
        System.out.println("com.rasas.mbeans.MBRsData.rsDataClose()");
        
        emf.getCache().evictAll();
        int rows = 0;
        
//        System.out.println("rsFrom : " + rsFrom + ", rsTo : " + rsTo + ", rsYear : " + rsYear +", rsCenter : " + rsCenter + ", rsSubCenter : " + rsSubCenter + ", rsTasDocNo : " + rsTasDocNo + ", rsTasDocYear : " + rsTasDocYear + ", rsTasDocType : " + rsTasDocType + 
//                ", rsTasDate : " + rsTasDate + ", rsTasUserId : " + rsTasUserId + ", rsTasNote : " + rsTasNote + ", rsTasCarNo : " + rsTasCarNo + ", rsTasCarNat : " + rsTasCarNat + ", rsCarWeight : " + rsCarWeight + ", rsCtnNo : " + rsCtnNo + ", rsCtnWeight : " + rsCtnWeight + 
//                ", rsGrossWeight : " + rsGrossWeight + ", rsInspDoc : " + rsInspDoc + ", rsTasScreen : " + rsTasScreen);
        
        Query query = em.createQuery("UPDATE RsData r SET r.rsTasDocNo = ?1, r.rsTasDocYear = ?2, r.rsTasDocType = ?3, r.rsTasDate = ?4, r.rsTasUserId = ?5, r.rsTasNote = ?6, "
                + "r.rsCarNo = ?7, r.rsCarNat = ?8, r.rsCarWeight = ?9, r.rsCtnNo = ?10, r.rsCtnWeight = ?11, r.rsGrossWeight = ?12, r.rsInspDoc = ?13, r.rsTasScreen = ?14 "
                + "WHERE r.rsDataPK.rsNo BETWEEN ?15 AND ?16 AND r.rsDataPK.rsYear = ?17 AND r.rsDataPK.rsCenter = ?18 AND r.rsSubCenter = ?19")
                .setParameter(1, rsTasDocNo)
                .setParameter(2, rsTasDocYear)
                .setParameter(3, rsTasDocType)
                .setParameter(4, rsTasDate)
                .setParameter(5, rsTasUserId)
                .setParameter(6, rsTasNote)
                .setParameter(7, rsTasCarNo)
                .setParameter(8, rsTasCarNat)
                .setParameter(9, rsCarWeight)
                .setParameter(10, rsCtnNo)
                .setParameter(11, rsCtnWeight)
                .setParameter(12, rsGrossWeight)
                .setParameter(13, rsInspDoc)
                .setParameter(14, rsTasScreen)
                .setParameter(15, rsFrom)
                .setParameter(16, rsTo)
                .setParameter(17, rsYear)
                .setParameter(18, rsCenter)
                .setParameter(19, rsSubCenter);
                
        rows = query.executeUpdate();
        
        System.out.println("---------- RsData colsed rows ------------------> " + rows + " >> " + MBCommon.getCurrentDateTime());
        return rows;
    }

////////////////////////////////////////////////////////////////////////////////
    public int rsDataOpen(int rsFrom, int rsTo, String rsYear, String rsCenter, String rsSubCenter){
        System.out.println("com.rasas.mbeans.MBRsData.rsDataOpen()");
        
        emf.getCache().evictAll();
        int rows = 0;
        
        Query query = em.createQuery("UPDATE RsData r SET r.rsTasDocNo = null, r.rsTasDocYear = null, r.rsTasDocType = null, r.rsTasDate = null, r.rsTasUserId = null, r.rsTasNote = null, "
                + "r.rsCarNo = null, r.rsCarNat = null, r.rsCarWeight = null, r.rsCtnNo = null, r.rsCtnWeight = null, r.rsGrossWeight = null, r.rsInspDoc = null, r.rsTasScreen = null "
                + "WHERE r.rsDataPK.rsNo BETWEEN ?1 AND ?2 AND r.rsDataPK.rsYear = ?3 AND r.rsDataPK.rsCenter = ?4 AND r.rsSubCenter = ?5")
                .setParameter(1, rsFrom)
                .setParameter(2, rsTo)
                .setParameter(3, rsYear)
                .setParameter(4, rsCenter)
                .setParameter(5, rsSubCenter);
                
        rows = query.executeUpdate();
        
        System.out.println("---------- RsData opened rows ------------------> " + rows + " >> " + MBCommon.getCurrentDateTime());
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

////////////////////////////////////////////////////////////////////////////////
    public List<RsData> getMinOpenRsDataByCenterAndSubCenter(String rsYear, String rsCenterNo, String rsSubCenterNo){
        System.out.println("com.rasas.mbeans.MBRsData.getMinOpenRsDataByCenterAndSubCenter()");
        
        emf.getCache().evictAll();
        
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsNo = (SELECT MIN(r.rsDataPK.rsNo) FROM RsData r WHERE r.rsDataPK.rsYear = ?1 AND r.rsDataPK.rsCenter = ?2 AND r.rsSubCenter = ?3 AND r.rsTasDate IS NULL)", RsData.class)
                .setParameter(1, rsYear)
                .setParameter(2, rsCenterNo)
                .setParameter(3, rsSubCenterNo);
        
        return query.getResultList();
        
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
