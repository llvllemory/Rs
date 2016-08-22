package com.rasas.mbeans;

import com.rasas.entities.RsMain;
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
    private String centerNo;
    private String subCenterNo;
    private int rsFound;
    
    private List<RsMain> rsList = new ArrayList<>();
    private RsMain rasas;
    
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
        
        rsList = getRasasCenterByCenterAndYear(centerNo, MBCommon.getCurrentYear());
        
        if(rsList.size() > 0){
            
            rsFound = 0;
            for(RsMain rs: rsList){
                for(int i = rsFrom; i <= rsTo; i++){
                    if(rs.getRsNo() == i){
                        rsFound++;
                    }
                }
            }
            
            if (rsFound == ((rsTo - rsFrom) + 1)) {
                MBCommon.getErrorMessage("", "هذا الرصاص مصروف مسبقا للمركز, الرجاء التأكد أولا!");
            } else if (rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)) {
                MBCommon.getWarnMessage("", "هنالك رصاص مصروف مسبقا من هذا الرصاص للمركز, الرجاء التأكد أولا!");
            } else if (rsFound == 0) {

                int x = saveRasasCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), centerNo, new java.util.Date(), "33476");

                if (x == 0) {
                    MBCommon.getErrorMessage("", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
                } else if (x == ((rsTo - rsFrom) + 1)) {
                    MBCommon.getInfoMessage("", "تم صرف الرصاص للمركز ينجاح.");
                } else {
                    MBCommon.getWarnMessage("", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
                }
            }
            
        }else{
            
            int x = saveRasasCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), centerNo, new java.util.Date(), "33476");

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

        rsList = getRasasCenterByCenterAndYear("220", MBCommon.getCurrentYear());

        if (rsList.size() > 0) {
            rsFound = 0;
            for (RsMain rs : rsList) {
                for (int i = rsFrom; i <= rsTo; i++) {
                    if (rs.getRsNo() == i) {
                        rsFound++;
                    }
                }
            }

            if (rsFound == 0) {
                MBCommon.getErrorMessage("", "الرصاص غير موجود في ملف المركز. الرجاء إستلام الرصاص من اللوازم أولا!");
            } else if (rsFound > 0 && rsFound > ((rsTo - rsFrom) + 1)) {
                MBCommon.getWarnMessage("", "هنالك رصاص غير موجود في ملف الرصاص, الرجاء إستلام الرصاص من اللوازم أولا!");
            } else if (rsFound == ((rsTo - rsFrom) + 1)) {

                rsList = new ArrayList<>();
                rsList = getNullRasasCenterByCenterAndYear("220", MBCommon.getCurrentYear());

                if (rsList.size() > 0) {

                    rsFound = 0;

                    for (RsMain rs : rsList) {
                        for (int i = rsFrom; i <= rsTo; i++) {
                            if (rs.getRsNo() == i) {
                                rsFound++;
                            }
                        }
                    }

                    if (rsFound == 0) {
                        MBCommon.getErrorMessage("", "هذا الرصاص مصروف ومسدد مسبقا, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)) {
                        MBCommon.getWarnMessage("", "هنالك رصاص مصروف ومسدد مسبقا, الرجاء التأكد والمحاولة مرة اخرى!");
                    } else if (rsFound == ((rsTo - rsFrom) + 1)) {

                        int x = saveRasasSubCenter(rsFrom, rsTo, MBCommon.getCurrentYear(), "220", subCenterNo, new java.util.Date(), "33476");

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
    public List<RsMain> getRasasCenterByCenterAndYear(String rsCenterNo, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasCenterByCenterAndYear()---------->");

        TypedQuery<RsMain> query = em.createQuery("SELECT r FROM RsMain r WHERE r.rsCenter = ?1 AND r.rsYear = ?2", RsMain.class)
                .setParameter(1, rsCenterNo)
                .setParameter(2, rsYear);

        List rasasList = query.getResultList();

        return rasasList;
    } 
////////////////////////////////////////////////////////////////////////////////
    public List<RsMain> getNullRasasCenterByCenterAndYear(String rsCenterNo, String rsYear){
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasCenterByCenterAndYear()---------->");

        TypedQuery<RsMain> query = em.createQuery("SELECT r FROM RsMain r WHERE r.rsSubCenter IS NULL AND r.rsCenter = ?1 AND r.rsYear = ?2", RsMain.class)
                .setParameter(1, rsCenterNo)
                .setParameter(2, rsYear);

        List rasasList = query.getResultList();

        return rasasList;
    } 
        
////////////////////////////////////////////////////////////////////////////////    
    public int saveRasasCenter(int rsFrom, int RsTo, String rsYear, String rsCenter,Date rsCenterDate, String rsCenterUserId){
        System.out.println("com.rasas.mbeans.MBRsMain.saveRasasCenter()---------->");
        
        int rows = 0;
        
        for(int i = rsFrom; i <= rsTo; i++){
            rasas = new RsMain();
            
            rasas.setRsNo(Long.valueOf(i));
            rasas.setRsYear(rsYear);
            rasas.setRsCenter(rsCenter);
            rasas.setRsCenterDate(rsCenterDate);
            rasas.setRsCenterUserId(rsCenterUserId);
            
            em.persist(rasas);
            rows++;
            
            if ((i % 10) == 0) {
                em.flush();
                em.clear();
            }
        }
        em.getTransaction().commit();
        
        System.out.println("----------------- saved rows -----------> " + rows);
        return rows;
    }
////////////////////////////////////////////////////////////////////////////////
    public int saveRasasSubCenter(int rsFrom, int RsTo, String rsYear, String rsCenter, String rsSubCenter, Date rsSubCenterDate, String rsSubCenterUserId){
        System.out.println("com.rasas.mbeans.MBRsMain.saveRasasSubCenter()---------->");
        
        int rows = 0;
        
        for(int i = rsFrom; i <= rsTo; i++){
            rows += em.createQuery("UPDATE RsMain SET rsSubCenter = ?1, rsSubCenterDate = ?2, rsSubCenterUserId = ?3 "
                        + "WHERE rsNo = ?4 AND rsYear = ?5 AND rsCenter = ?6")
                        .setParameter(1, rsSubCenter)
                        .setParameter(2, new java.util.Date())
                        .setParameter(3, rsSubCenterUserId)
                        .setParameter(4, i)
                        .setParameter(5, rsYear)
                        .setParameter(6, rsCenter).executeUpdate();
            
        }
        System.out.println("----------------- updated rows -----------> " + rows);
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

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getSubCenterNo() {
        return subCenterNo;
    }

    public void setSubCenterNo(String subCenterNo) {
        this.subCenterNo = subCenterNo;
    } 
}
