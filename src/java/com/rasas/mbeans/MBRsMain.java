package com.rasas.mbeans;

import com.rasas.entities.RsData;
import com.rasas.entities.RsDataPK;
import com.rasas.entities.RsMain;
import com.rasas.entities.RsMainPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBRsMain implements Serializable{

    private int rsFrom;
    private int rsTo;
    private String rsCenter;
    private String rsSubCenter;
    private int rsFound;
    private int isNull;
    
    private List<RsMain> rsMainList;
    private RsMain   rsMain;
    private RsMainPK rsMainPK;
    
    private List<RsData> rsDataList;
    private RsData   rsData;
    private RsDataPK rsDataPK;
    
    private MBLogin mBLogin = new MBLogin();
    private MBRsData mBRsData = new MBRsData();
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBRsMain(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public String checkRasasCenter(){
        System.out.println("com.rasas.mbeans.MBRsMain.checkRasasCenter()---------->");
        
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
        
        rsMainList = new ArrayList<>();
        rsMainList = getRasasCenterByCenterAndYear(rsCenter, MBCommon.getCurrentYear());
        
        if(rsMainList.size() > 0){
            
            rsFound = 0;
            for(RsMain rs: rsMainList){
                for(int i = rsFrom; i <= rsTo; i++){
                    if(rs.getRsMainPK().getRsNo() == i){
                        rsFound++;
                    }
                }
            }
            
            if (rsFound == ((rsTo - rsFrom) + 1)) {
                MBCommon.getErrorMessage("", "هذا الرصاص مصروف مسبقا للمركز, الرجاء التأكد أولا!");
            } else if (rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)) {
                MBCommon.getWarnMessage("", "هنالك رصاص مصروف مسبقا من هذا الرصاص للمركز, الرجاء التأكد أولا!");
            } else if (rsFound == 0) {

                int x = saveRasasCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), rsCenter, new java.util.Date(), mBLogin.getLoggedUser().getUserId());

                if (x == 0) {
                    MBCommon.getErrorMessage("", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
                } else if (x == ((rsTo - rsFrom) + 1)) {
                    MBCommon.getInfoMessage("", "تم صرف الرصاص للمركز ينجاح.");
                } else {
                    MBCommon.getWarnMessage("", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
                }
            }
            
        }else{
            
            int x = saveRasasCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), rsCenter, new java.util.Date(), mBLogin.getLoggedUser().getUserId());

            if (x == 0) {
                MBCommon.getErrorMessage("", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
            } else if (x == ((rsTo - rsFrom) + 1)) {
                MBCommon.getInfoMessage("", "تم صرف الرصاص للمركز ينجاح.");
            } else {
                MBCommon.getWarnMessage("", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
            }
        }

        return "";
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkRasasSubCenter(){
        System.out.println("com.rasas.mbeans.MBRsMain.checkRasasSubCenter()---------->");
        
        if (rsFrom == 0) {
            MBCommon.getWarnMessage("", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        if (rsTo == 0) {
            MBCommon.getWarnMessage("", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        if (rsTo < rsFrom) {
            MBCommon.getWarnMessage("", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        rsMainList = new ArrayList<>();
        rsMainList = getRasasCenterByCenterAndYear("220", MBCommon.getCurrentYear());

        if (rsMainList.size() > 0) {
            rsFound = 0;
            for (RsMain rs : rsMainList) {
                for (int i = rsFrom; i <= rsTo; i++) {
                    if (rs.getRsMainPK().getRsNo() == i) {
                        rsFound++;
                    }
                }
            }

            if (rsFound == 0) {
                MBCommon.getErrorMessage("", "الرصاص غير موجود في ملف المركز. الرجاء إستلام الرصاص من اللوازم أولا!");
            } else if (rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)) {
                MBCommon.getWarnMessage("", "هنالك رصاص غير موجود في ملف الرصاص, الرجاء إستلام الرصاص من اللوازم أولا!");
            } else if (rsFound == ((rsTo - rsFrom) + 1)) {
            
                rsMainList = new ArrayList<>();
                rsMainList = getNullRasasCenterByCenterAndYear("220", MBCommon.getCurrentYear());

                if (rsMainList.size() > 0) {

                    rsFound = 0;

                    for (RsMain rs : rsMainList) {
                        for (int i = rsFrom; i <= rsTo; i++) {
                            if (rs.getRsMainPK().getRsNo() == i) {
                                rsFound++;
                            }
                        }
                    }
                    
                    if (rsFound == 0) {
                        MBCommon.getErrorMessage("", "هذا الرصاص مصروف ومسدد مسبقا, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)) {
                        MBCommon.getWarnMessage("", "هنالك رصاص مصروف ومسدد مسبقا, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (rsFound == ((rsTo - rsFrom) + 1)) {

                        int x = updateRasasSubCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), "220", rsSubCenter, new java.util.Date(), mBLogin.getLoggedUser().getUserId());

                        if (x == 0) {
                            MBCommon.getErrorMessage("", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
                        } else if (x == ((rsTo - rsFrom) + 1)) {
                            MBCommon.getInfoMessage("", "تم صرف الرصاص للمركز ينجاح.");
                        } else {
                            MBCommon.getWarnMessage("", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
                        }
                    }
                } else {
                    MBCommon.getErrorMessage("", "لا يوجد رصاص متاح للصرف, الرجاء التأكد والمحاولة مرة اخرى!");
                }
            }
        } else {
            MBCommon.getErrorMessage("", "لا يوجد رصاص متاح للصرف في المركز, يجب إستلام الرصاص من اللوازم أولا!");
        }
        return "";
    }
////////////////////////////////////////////////////////////////////////////////
    public String checkRasasCenterToDelete(){
        System.out.println("com.rasas.mbeans.MBRsMain.checkRasasCenterToDelete()---------->");
        
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
        
        rsMainList = new ArrayList<>();
        rsMainList = getRasasCenterByCenterAndYear(rsCenter, MBCommon.getCurrentYear());
        
        rsFound = 0;
        for(RsMain rs : rsMainList){
           for(int i = rsFrom; i <= rsTo; i++){
               if(rs.getRsMainPK().getRsNo() == i){
                   rsFound++;
               }
           } 
        }
        
        if(rsFound == 0){
            MBCommon.getErrorMessage("", "الرصاص غير موجود في ملف الرصاص الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
            return "";
        }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
            MBCommon.getWarnMessage("", "هنالك رصاص غير موجود في الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
        }else if(rsFound == ((rsTo - rsFrom) + 1)){
            
            rsMainList = new ArrayList<>(); 
            rsMainList= getNullRasasCenterByCenterAndYear(rsCenter, MBCommon.getCurrentYear());

            int rsNull = 0;

            for (RsMain rs : rsMainList) {
                for (int i = rsFrom; i <= rsTo; i++) {
                    if (rs.getRsMainPK().getRsNo() == i) {
                        rsNull++;
                    }
                }
            }

            if (rsNull == 0) {
                MBCommon.getErrorMessage("", "إنتبه, هذا الرصاص مصروف ومسدد مسبقا , الرجاء التأكد والمحاولة مرة اخرى أو الإتصال مع مدير النظام!");
                return "";
            }else if(rsNull > 0 && rsNull < ((rsTo - rsFrom) + 1)){
                MBCommon.getWarnMessage("", "إنتبه, هنالك رصاص مصروف ومسدد مسبقا من هذا الرصاص, الرجاء التأكد والمحاولة مرة اخرى أو الإتصال مع مدير النظام!");
                return "";
            }else if(rsNull == ((rsTo - rsFrom) + 1)){
                
                int x = removeRasasCenter(rsFrom, rsTo, rsCenter, MBCommon.getCurrentYear());
                
                if(x == 0){
                    MBCommon.getErrorMessage("", "لم يتم حذف الرصاص من الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
                }else if(x > 0 && x < ((rsTo - rsFrom) + 1)){
                    MBCommon.getWarnMessage("", "هنالك رصاص لم يتم حذفه من الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
                }else if( x == ((rsTo - rsFrom) + 1)){
                    MBCommon.getInfoMessage("", "تم حذف كافة الرصاص من الملف الرئيسي بنجاح.");                    
                }
                return "";
            }
        }
        return "";
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkRasasSubCenterToDelete(){
        System.out.println("com.rasas.mbeans.MBRsMain.checkRasasSubCenterToDelete()---------->");
        
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
        
        rsMainList = new ArrayList<>();
        rsMainList = getRasasSubCenterBySubCenterAndYear(rsSubCenter, MBCommon.getCurrentYear());
        
        if(rsMainList.size() > 0){
            
            rsFound = 0;
            
            for(RsMain rs: rsMainList){
                for(int i = rsFrom; i <= rsTo; i++){
                    if(rs.getRsMainPK().getRsNo() == i){
                        rsFound++;
                    }
                }
            }
            
            if(rsFound == 0){
                MBCommon.getErrorMessage("", "الرصاص غير مصروف للمركز, الرجاء التأكد والمحاولة مرة اخرى!");
            }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
                MBCommon.getWarnMessage("", "هنالك رصاص غير مصروف للمركز, الرجاء التأكد والمحاولة مرة اخرى!");
            }else if(rsFound == ((rsTo - rsFrom) + 1)){
                
                
                rsDataList = new ArrayList<>();
                rsDataList = mBRsData.getRasasDataByRsYearAndRsSubCenter(MBCommon.getCurrentYear(), rsSubCenter);
                
                if(rsDataList.size() > 0){
                    
                    rsFound = 0;
                    
                    for(RsData rs: rsDataList){
                        for(int i = rsFrom; i <= rsTo; i++){
                            if(rs.getRsDataPK().getRsNo() == i){
                                rsFound++;
                                
                                if(rs.getRsTasUserId().equals(null)){
                                    isNull++;
                                }
                            }
                        }
                    }
                    
                    if(rsFound == 0){
                        MBCommon.getErrorMessage("", "الرصاص غير مصروف للمركز في ملف معلومات الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
                    }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
                        MBCommon.getWarnMessage("", "هنالك رصاص من هذا الرصاص غير مصروف للمركز في ملف معلومات الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
                    }else if(rsFound == ((rsTo - rsFrom) + 1)){
                        
                        if(isNull == 0){
                            MBCommon.getErrorMessage("", "إنتبه, الرصاص مسدد لمعاملات جمركية, الرجاء التأكد والمحاولة مرة اخرى!");
                        }else if(isNull > 0 ){
                            MBCommon.getWarnMessage("", "إنتبه, هنالك رصاص مسدد لمعاملات جمركية, الرجاء التأكد والمحاولة مرة اخرى!");
                        }else if(isNull == ((rsTo - rsFrom) + 1)){
                            
                            
                            int x = mBRsData.removeRsDataBySubCenterAndRsYearAndRsFromRsTo(rsFrom, rsTo, MBCommon.getCurrentYear(), rsCenter);
                            
                            if (x == 0) {
                                MBCommon.getErrorMessage("", "لم يتم حذف الرصاص من ملف معلومات الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
                            } else if (x > 0 && x < ((rsTo - rsFrom) + 1)) {
                                MBCommon.getErrorMessage("", "هنالك رصاص لم يتم حذفه من ملف معلومات الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
                            } else if (x == ((rsTo - rsFrom) + 1)) {
                                
                                int y = cancelUpdateRasasSubCenter(rsFrom, rsTo, rsCenter, MBCommon.getCurrentYear());
                                
                                if (y == 0) {
                                    MBCommon.getErrorMessage("", "لم يتم حذف تسديد الرصاص من الملف الرئيسي, الرجاء التأكد من الرصاص المحذوف أو الإتصال مع مدير النظام!");
                                } else if (y > 0 && y < ((rsTo - rsFrom) + 1)) {
                                    MBCommon.getErrorMessage("", "هنالك رصاص لم يتم حذف تسديده من الملف الرئيسي, الرجاء التأكد من الرصاص المحذوف أو الإتصال مع مدير النظام!");
                                } else if (y == ((rsTo - rsFrom) + 1)) {
                                    MBCommon.getInfoMessage("", "تم حذف تسديد الرصاص من الملف الرئيسي");
                                }
                                return "";
                            }
                            return "";
                        }
                        return "";
                    }
                    return "";
                }else{
                    MBCommon.getErrorMessage("", "لا يوجد رصاص للمركز في ملف معلومات الرصاص, الرجاء التأكد والمحاولة مرة اخرى!");
                }
                return "";
            }
            return "";
        }else{
            MBCommon.getErrorMessage("", "لا يوجد رصاص لهذا المركز في الملف الرئيسي, الرجاء التأكد والمحاولة مرة اخرى!");
        }
        return "";
    }
////////////////////////////////////////////////////////////////////////////////
    public List<RsMain> getRasasCenterByCenterAndYear(String rsCenterNo, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasCenterByCenterAndYear()---------->");

        emf.getCache().evictAll();
        TypedQuery<RsMain> query = em.createQuery("SELECT r FROM RsMain r WHERE r.rsMainPK.rsCenter = ?1 AND r.rsMainPK.rsYear = ?2", RsMain.class)
                .setParameter(1, rsCenterNo)
                .setParameter(2, rsYear);

        List rasasList = query.getResultList();

        return rasasList;
    } 
////////////////////////////////////////////////////////////////////////////////
    public List<RsMain> getNullRasasCenterByCenterAndYear(String rsCenterNo, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasCenterByCenterAndYear()---------->");

        emf.getCache().evictAll();
        TypedQuery<RsMain> query = em.createQuery("SELECT r FROM RsMain r WHERE r.rsSubCenter IS NULL AND r.rsMainPK.rsCenter = ?1 AND r.rsMainPK.rsYear = ?2", RsMain.class)
                .setParameter(1, rsCenterNo)
                .setParameter(2, rsYear);

        List rasasList = query.getResultList();

        return rasasList;
    } 
////////////////////////////////////////////////////////////////////////////////
    public List<RsMain> getRasasSubCenterBySubCenterAndYear(String rsSubCenterNo, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasSubCenterByCenterAndYear()---------->");

        emf.getCache().evictAll();
        TypedQuery<RsMain> query = em.createQuery("SELECT r FROM RsMain r WHERE r.rsSubCenter = ?1 AND r.rsMainPK.rsYear = ?2", RsMain.class)
                .setParameter(1, rsSubCenterNo)
                .setParameter(2, rsYear);

        List rasasList = query.getResultList();

        return rasasList;
    } 
        
////////////////////////////////////////////////////////////////////////////////    
    public int saveRasasCenter(int rsFrom, int rsTo, String rsYear, String rsCenter,Date rsCenterDate, String loggedUser){
        System.out.println("com.rasas.mbeans.MBRsMain.saveRasasCenter()---------->");
        
        emf.getCache().evictAll();
        int rows = 0;

        for(int i = rsFrom; i <= rsTo; i++){
            rsMain = new RsMain();
            rsMainPK = new RsMainPK();
                        
            rsMainPK.setRsNo(i);
            rsMainPK.setRsYear(rsYear);
            rsMainPK.setRsCenter(rsCenter);

            rsMain.setRsMainPK(rsMainPK);
            rsMain.setRsCenterDate(rsCenterDate);
            rsMain.setRsCenterUserId(loggedUser);
            
            em.persist(rsMain);
            rows++;
        }

        em.getTransaction().commit();

        System.out.println("-----------------RsMain saved rows -----------> " + rows);
        return rows;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int updateRasasSubCenter(int rsFrom, int rsTo, String rsYear, String rsCenter, String rsSubCenter, Date rsSubCenterDate, String loggedUser){
        System.out.println("com.rasas.mbeans.MBRsMain.updateRasasSubCenter()---------->");
        
        emf.getCache().evictAll();
        int rows = 0;
        
        rsDataList = new ArrayList<>();
        
        for(int i = rsFrom; i <= rsTo; i++){
            
            rsMain = new RsMain();
            rsMainPK = new RsMainPK();
            
            rsMainPK.setRsNo(i);
            rsMainPK.setRsYear(rsYear);
            rsMainPK.setRsCenter(rsCenter);
             
            rsMain = em.find(RsMain.class, rsMainPK);
           
            rsMain.setRsMainPK(rsMainPK);
            rsMain.setRsSubCenter(rsSubCenter);
            rsMain.setRsSubCenterDate(rsSubCenterDate);
            rsMain.setRsSubCenterUserId(loggedUser);
            
            
                ////////////////////////// fill rasasData list with rasas data /
                rsData = new RsData();
                rsDataPK = new RsDataPK();
                
                rsDataPK.setRsNo(rsMain.getRsMainPK().getRsNo());
                rsDataPK.setRsYear(rsMain.getRsMainPK().getRsYear());
                rsDataPK.setRsCenter(rsCenter);
                rsData.setRsDataPK(rsDataPK);
                
                rsData.setRsSubCenter(rsMain.getRsSubCenter());
                rsData.setRsEntryDate(rsMain.getRsSubCenterDate());
                rsData.setRsUserId(rsMain.getRsSubCenterUserId());
                
                rsDataList.add(rsData);
                
                ////////////////////////////////////////////////////////////////
            
            em.persist(rsMain);
            rows++;    
        }

        em.getTransaction().commit();
        
        if(rows == ((rsTo - rsFrom) + 1)){
            int x = mBRsData.saveRsData(rsDataList);
            
            if(x != rows ){
                MBCommon.getErrorMessage("", "هنالك رصاص لم يتم تخزينه في ملف الرصاص, الرجاء التأكد من الرصاص المصروف!");
            }else if(x == 0){
                MBCommon.getErrorMessage("", "لم يتم تخزين الرصاص في ملف الرصاص, الرجاء التأكد من الرصاص المصروف!");
            }
        }
        
        
        System.out.println("----------------- RsMain updated rows ---------> " + rows);
        return rows;
    }
   
////////////////////////////////////////////////////////////////////////////////
    public int removeRasasCenter(int rsFrom, int rsTo, String rsCenter, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.removeRasasCenter()---------->");
        
        int rows = 0;
        for(int i = rsFrom; i <= rsTo; i++){
            rsMainPK = new RsMainPK();
            
            rsMainPK.setRsNo(i);
            rsMainPK.setRsYear(rsYear);
            rsMainPK.setRsCenter(rsCenter);
            
            rsMain = em.find(RsMain.class, rsMainPK);
            
            
            em.remove(rsMain);
            em.getTransaction().commit();
            rows++;
            em.getTransaction().begin();
        }
      
        System.out.println("------------- Rasas center removed ------------->" + rows);
        return rows;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int cancelUpdateRasasSubCenter(int rsFrom, int rsTo, String rsCenter, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.cancelUpdateRasasSubCenter()---------->");
        
        int rows = 0;
         
        for (int i = rsFrom; i <= rsTo; i++) {
            rsMainPK = new RsMainPK();

            rsMainPK.setRsNo(i);
            rsMainPK.setRsYear(rsYear);
            rsMainPK.setRsCenter(rsCenter);

            rsMain = em.find(RsMain.class, rsMainPK);
            rsMain.setRsSubCenter(null);
            rsMain.setRsSubCenterDate(null);
            rsMain.setRsSubCenterUserId(null);
            
            em.persist(rsMain);
            em.getTransaction().commit();
            rows++;
            em.getTransaction().begin();
        }
        
        System.out.println("---------- Rasas subCenter update canceld ------>" + rows);
        return rows;
    }
/////////////////////// Getters and Setters ////////////////////////////////////

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

    public String getRsCenter() {
        return rsCenter;
    }

    public void setRsCenter(String rsCenter) {
        this.rsCenter = rsCenter;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    } 
}
