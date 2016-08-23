package com.rasas.mbeans;

import com.rasas.entities.RsData;
import com.rasas.entities.RsDataPK;
import java.util.ArrayList;
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

public class MBRsData {
    
    private int rsFrom;
    private int rsTo;
    private String rsSubCenter;
    private int rsFound;
    
    private List<RsData> rsDataList;
    private RsData rsDara;
    private RsDataPK rsDataPK;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBRsData(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkRasasSubSenterToDelete(){
        System.out.println("com.rasas.mbeans.MBRsData.checkRasasSubSenterToDelete()---------->");
        
        if(rsFrom == 0){
            MBCommon.getWarnMessage("", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        if(rsTo == 0){
            MBCommon.getWarnMessage("", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        if(rsTo < rsFrom){
            MBCommon.getWarnMessage("", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        rsDataList = new ArrayList<>();
        rsDataList = getRasasDataByRsYearAndRsSubCenter(MBCommon.getCurrentYear(), rsSubCenter);
        
        rsFound = 0;
        
        for(RsData rs : rsDataList){
            for(int i = rsFrom; i <= rsTo; i++){
                if(rs.getRsDataPK().getRsNo() == i){
                    rsFound++;
                }
            }
        }
        
        if(rsFound == 0){
            MBCommon.getErrorMessage("", "الرصاص غير موجود في ملف الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
        }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
            MBCommon.getWarnMessage("", "هنالك رصاص غير موجود في ملف الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
        }else if(rsFound == ((rsTo - rsFrom) + 1)){
            
            rsDataList = new ArrayList<>();
            rsDataList = getNullRasasDataByRsYearAndRsSubCenter(MBCommon.getCurrentYear(), rsSubCenter);
            
            rsFound = 0;
            
            for(RsData rs: rsDataList){
                for(int i = rsFrom; i <= rsTo; i++){
                    if(rs.getRsDataPK().getRsNo() == i){
                        rsFound++;
                    }
                }
            }
            
            if(rsFound == 0){
                MBCommon.getErrorMessage("", "انتبه, هذا الرصاص مسدد مسبقا ولا يمكن حذفه, الرجاء التأكد والمحاولة مرة اخرى!");
            }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
                MBCommon.getWarnMessage("", "إنتبه, هنالك رصاص مسدد مسبقا ولا يمكن حذفه, الرجاء التأكد والمحاولة مرة اخرى!");
            }else if(rsFound == ((rsTo - rsFrom) + 1)){
                
                int x = removeRsSubCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), rsSubCenter);
                
                if(x == 0){
                    MBCommon.getErrorMessage("", "لم يتم حذف الرصاص, الرجاء المحاولة مرة اخرى أو الأتصال مع مدير النظام!");
                }else if(x > 0 && x < ((rsTo - rsFrom) + 1)){
                    MBCommon.getWarnMessage("", "هنالك رصاص لم يتم حذفه, الرجاء التأكد من الرصاص المحذوف أو الأتصال مع مدير النظام!");
                }else if(x == ((rsTo - rsFrom) + 1)){
                    MBCommon.getInfoMessage("", "تم حذف الرصاص بنجاح.");
                    
                    MBRsMain mBRsMain = new MBRsMain();
                    int y = mBRsMain.removeRasasCenter(rsFrom, rsTo, "220", MBCommon.getCurrentYear());
                    
                    if (y == 0) {
                        MBCommon.getErrorMessage("", "لم يتم حذف الرصاص من الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (y > 0 && y < ((rsTo - rsFrom) + 1)) {
                        MBCommon.getWarnMessage("", "هنالك رصاص لم يتم حذفه من الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (y == ((rsTo - rsFrom) + 1)) {
                        MBCommon.getInfoMessage("", "تم حذف كافة الرصاص من الملف الرئيسي بنجاح.");
                    }
                    return "";
                }
                return "";
            }
            return "";
        }
        return "";
    }
////////////////////////////////////////////////////////////////////////////////
   public List<RsData> getRasasDataByRsNoAndRsYearAndRsSubCenter(int rsNo, String rsYear, String rsSubCenter){
       System.out.println("com.rasas.mbeans.MBRsData.getRasasDataByRsNoAndRsYearAndRsSubCenter()---------->");
       
       emf.getCache().evictAll();
       TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsNo = ?1 And r.rsDataPK.rsYear = ?2 AND r.rsDataPK.rsSubCenter = ?3", RsData.class)
               .setParameter(1, rsNo)
               .setParameter(2, rsYear)
               .setParameter(3, rsSubCenter);
       
       List rasasData = query.getResultList();

       return rasasData;
   }
    
////////////////////////////////////////////////////////////////////////////////
    public List<RsData> getRasasDataByRsYearAndRsSubCenter(String rsYear, String rsSubCenter){
        System.out.println("com.rasas.mbeans.MBRsData.getRasasDataByRsYearAndRsSubCenter()---------->");
        
        emf.getCache().evictAll();
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsDataPK.rsYear = ?1 AND r.rsDataPK.rsSubCenter = ?2", RsData.class)
                .setParameter(2, rsYear)
                .setParameter(1, rsSubCenter);
        
        List rasasData = query.getResultList();
        
        return rasasData;
    }
////////////////////////////////////////////////////////////////////////////////
    public List<RsData> getNullRasasDataByRsYearAndRsSubCenter(String rsYear, String rsSubCenter){
        System.out.println("com.rasas.mbeans.MBRsData.getNullRasasDataByRsYearAndRsSubCenter()---------->");
        
        emf.getCache().evictAll();
        TypedQuery<RsData> query = em.createQuery("SELECT r FROM RsData r WHERE r.rsTasUserId IS NULL AND r.rsDataPK.rsYear = ?1 AND r.rsDataPK.rsSubCenter = ?2", RsData.class)
                .setParameter(1, rsYear)
                .setParameter(2, rsSubCenter);
        
        List rasasData = query.getResultList();
        
        return rasasData;
    }
////////////////////////////////////////////////////////////////////////////////
    public int saveRsData(List<RsData> rsDataList){
        System.out.println("com.rasas.mbeans.MBRsData.saveRsData()---------->");
        
        emf.getCache().evictAll();
        int rows = 0;
        
        for(RsData rs: rsDataList){
            em.persist(rs);
            rows++;
        }
        
        em.getTransaction().commit();
        
        System.out.println("----------------- RsData saved rows -----------> " + rows);
        return rows;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int updateRsData(RsData rsData){
        System.out.println("com.rasas.mbeans.MBRsData.updateRsData()---------->");
        
        emf.getCache().evictAll();
        int rows = 0;
        
        
        
        System.out.println("----------------- RsData updated rows ---------> " + rows);
        return rows;  
    }

////////////////////////////////////////////////////////////////////////////////
    public int removeRsSubCenter(int rsFrom, int rsTo, String rsYear, String rsSubCenter){
        System.out.println("com.rasas.mbeans.MBRsData.removeRsSubCenter()---------->");
        
        emf.getCache().evictAll();
        int rows = 0;
        
        for(int i = rsFrom; i <= rsTo; i++){
            
            Query query = em.createQuery("DELETE FROM RsData r WHERE r.rsDataPK.rsNo = ?1 AND r.rsDataPK.rsYear = ?2 AND r.rsSubCenter = ?3")
                    .setParameter(1, i)
                    .setParameter(2, rsYear)
                    .setParameter(3, rsSubCenter);
            
            rows += query.executeUpdate();
        }
        
        System.out.println("----------------- RsData removed rows ---------> " + rows);
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
