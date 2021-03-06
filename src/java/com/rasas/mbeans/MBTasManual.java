package com.rasas.mbeans;

import com.rasas.entities.RsData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped

public class MBTasManual implements Serializable{
    
    private int rsFrom;
    private int rsTo;
    private String rsYear;
    private int tasDocType;
    private int tasDocNo;
    private String tasDocYear;
    private String tasCarNo;
    private String tasCarNat;
    private int carWeight;
    private int ctnWeight;
    private int grossWeight;
    private String ctnNo;
    private int inspDoc;
    private String tasNote;
    
    private int rsFound;
    private int rsOpen;
    private int rows;
    
    private MBLogin mBLogin = new MBLogin();
    private MBRsMain mBRsMain = new MBRsMain();
    private MBRsData mBRsData = new MBRsData();
    private List<RsData> rsDataList;
    private List<RsData> rsDataListDataTable;
        
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBTasManual(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public String checkRsToClose(){
        System.out.println("com.rasas.mbeans.MBTasManual.checkRsToClose()");
        
        if(tasDocType == 0){
            MBCommon.getWarnMessage("", "يجب إختيار نوع التسديد!");
            return "";
        }
        
        if(rsFrom == 0 || rsFrom < 0){
            MBCommon.getWarnMessage("", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo == 0 || rsTo < 0){
            MBCommon.getWarnMessage("", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo < rsFrom){
            MBCommon.getWarnMessage("", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        if(rsYear == null){
            MBCommon.getWarnMessage("", "يحب إختيار سنة الرصاص!");
            return "";
        }
      
        
        if(tasNote.equals("")){
            MBCommon.getWarnMessage("", "بجب إدخال الملاحظات!");
            return "";
        }
        
        rsDataList = new ArrayList<>();
        rsDataList = mBRsData.getRsDataByRsYearRsCenterRsSubCenterRsFromRsTo(rsYear, mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter(), rsFrom, rsTo);
        
        if(rsDataList.isEmpty()){
            MBCommon.getWarnMessage("", "الرصاص غير موجود او مصروف للمركز في ملف الرصاص. الرجاء التأكد من ارقام الرصاص!");
            return "";
            
        }else if(rsDataList.size() != ((rsTo - rsFrom) + 1)){
           MBCommon.getWarnMessage("", "هنالك رصاص غير موجود او مصروف للمركز من هذا الرصاص, الرجاء التأكد من ارقام الرصاص!");
           return ""; 
            
        }else if(rsDataList.size() == ((rsTo - rsFrom) + 1)){
           
            rsFound = 0;
            rsOpen = 0;
            rows = 0;
            
            for(RsData r: rsDataList){

                for(int rd = rsFrom; rd <= rsTo; rd++){
                    if (r.getRsDataPK().getRsNo() == rd) {
                        rsFound++;
                    }  
                }
                
                if (r.getRsTasUserId() == null && r.getRsTasDate() == null) {
                    rsOpen++;
                }
            }

            if (rsOpen == 0) {
                MBCommon.getWarnMessage("", "الرصاص مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } else if (rsOpen > 0 && rsOpen < rsFound) {
                MBCommon.getWarnMessage("", "هنالك رصاص مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } else if (rsOpen == rsFound) {

                rows = mBRsData.rsDataClose(rsFrom, rsTo, rsYear, mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter(),
                        tasDocNo, tasDocYear, tasDocType, new java.util.Date(), mBLogin.getLoggedUser().getUserId(), tasNote, tasCarNo, tasCarNat, carWeight, ctnNo, ctnWeight, grossWeight, inspDoc, "rsManual");

                if (rows == 0) {
                    MBCommon.getErrorMessage("", "حدث خطأ في عملية التخزين, الرجاء التأكد والمحاولة مرة اخرى!");

                } else if (rows > 0 && rows < ((rsTo - rsFrom) + 1)) {
                    MBCommon.getErrorMessage("", "هنالك رصاص لم يتم تسديده, الرجاء التأكد والمحاولة مرة اخرى!");

                } else if (rows == ((rsTo - rsFrom) + 1)) {
                    MBCommon.getInfoMessage("", "تم تسديد الرصاص بنجاح!");
                    return "rs_manual_page.xhtml?faces-redirect=true";
                }
                return null;
            }
        }
        return "";
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String checkRsToOpen(){
        System.out.println("com.rasas.mbeans.MBTasManual.checkRsToOpen()");
        
        if(rsFrom == 0 || rsFrom < 0){
            MBCommon.getWarnMessage("", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo == 0 || rsTo < 0){
            MBCommon.getWarnMessage("", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo < rsFrom){
            MBCommon.getWarnMessage("", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        if(rsYear == null){
            MBCommon.getWarnMessage("", "يحب إختيار سنة الرصاص!");
            return "";
        }
        
        if(tasNote.equals("")){
            MBCommon.getWarnMessage("", "بجب إدخال الملاحظات!");
            return "";
        }
        
        rsDataList = new ArrayList<>();
        rsDataList = mBRsData.getRsDataByRsYearRsCenterRsSubCenterRsFromRsTo(rsYear, mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter(), rsFrom, rsTo);
        
        if(rsDataList.isEmpty()){
            MBCommon.getWarnMessage("", "الرصاص غير موجود او مصروف للمركز في ملف الرصاص. الرجاء التأكد من ارقام الرصاص!");
            return "";
            
        }else if(rsDataList.size() != ((rsTo - rsFrom) + 1)){
           MBCommon.getWarnMessage("", "هنالك رصاص غير موجود او مصروف للمركز من هذا الرصاص, الرجاء التأكد من ارقام الرصاص!");
           return ""; 
            
        }else if(rsDataList.size() == ((rsTo - rsFrom) + 1)){
           
            rsFound = 0;
            rsOpen = 0;
            rows = 0;
            
            for(RsData r: rsDataList){

                for(int rd = rsFrom; rd <= rsTo; rd++){
                    if (r.getRsDataPK().getRsNo() == rd) {
                        rsFound++;
                    }  
                }
                
                if (r.getRsTasUserId() == null && r.getRsTasDate() == null) {
                    rsOpen++;
                }
            }
            
            if (rsOpen == rsFound) {
                MBCommon.getWarnMessage("", "الرصاص غير مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } else if (rsOpen > 0 && rsOpen < rsFound) {
                MBCommon.getWarnMessage("", "هنالك رصاص غير مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } else if (rsOpen == 0) {

                rows = mBRsData.rsDataOpen(rsFrom, rsTo, rsYear, mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter());

                if (rows == 0) {
                    MBCommon.getErrorMessage("", "حدث خطأ في عملية إلغاء التسديد, الرجاء التأكد والمحاولة مرة اخرى!");

                } else if (rows > 0 && rows < ((rsTo - rsFrom) + 1)) {
                    MBCommon.getErrorMessage("", "هنالك رصاص لم يتم إلغاء تسديده, الرجاء التأكد والمحاولة مرة اخرى!");

                } else if (rows == ((rsTo - rsFrom) + 1)) {
                    MBCommon.getInfoMessage("", "تم إلغاء تسديد الرصاص بنجاح!");
                    return "rs_manual_page.xhtml?faces-redirect=true";
                }
                return "";
            }
        }
        return "";
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkToLoadRsDataForDataTable(){
        System.out.println("com.rasas.mbeans.MBTasManual.checkToLoadRsDataForDataTable()");
              
        if(rsFrom == 0 || rsFrom < 0){
            MBCommon.getWarnMessage("", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo == 0 || rsTo < 0){
            MBCommon.getWarnMessage("", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo < rsFrom){
            MBCommon.getWarnMessage("", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        if(rsYear == null){
            MBCommon.getWarnMessage("", "يحب إختيار سنة الرصاص!");
            return "";
        }
        
        
        rsDataListDataTable = new ArrayList<>();
        rsDataListDataTable = mBRsData.getRsDataByRsYearRsCenterRsSubCenterRsFromRsTo(rsYear, mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter(), rsFrom, rsTo);
        
        if(rsDataListDataTable.isEmpty()){
            MBCommon.getWarnMessage("", "الرصاص غير موجود او مصروف للمركز في ملف الرصاص. الرجاء التأكد من ارقام الرصاص!");
            return "";
            
        }else if(rsDataListDataTable.size() != ((rsTo - rsFrom) + 1)){
           MBCommon.getWarnMessage("", "هنالك رصاص غير موجود او مصروف للمركز من هذا الرصاص, الرجاء التأكد من ارقام الرصاص!");
           return ""; 
            
        }else if(rsDataListDataTable.size() == ((rsTo - rsFrom) + 1)){
           
            rsFound = 0;
            rsOpen = 0;
            rows = 0;
            
            for(RsData r: rsDataListDataTable){

                for(int rd = rsFrom; rd <= rsTo; rd++){
                    if (r.getRsDataPK().getRsNo() == rd) {
                        rsFound++;
                    }  
                }
                
                if (r.getRsTasUserId() == null && r.getRsTasDate() == null) {
                    rsOpen++;
                }
            }

            if (rsOpen == 0) {
                MBCommon.getWarnMessage("", "الرصاص مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } else if (rsOpen > 0 && rsOpen < rsFound) {
                MBCommon.getWarnMessage("", "هنالك رصاص مسدد مسبقا, الرجاء التأكد من ارقام الرصاص والمحاولة مرة اخرى!");
                return "";

            } 
        }
        
        return "";
    }

////////////////////// Getters and Setters /////////////////////////////////////    

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

    public String getRsYear() {
        return rsYear;
    }

    public void setRsYear(String rsYear) {
        this.rsYear = rsYear;
    }

    public int getTasDocType() {
        return tasDocType;
    }

    public void setTasDocType(int tasDocType) {
        this.tasDocType = tasDocType;
    }

    public int getTasDocNo() {
        return tasDocNo;
    }

    public void setTasDocNo(int tasDocNo) {
        this.tasDocNo = tasDocNo;
    }

    public String getTasDocYear() {
        return tasDocYear;
    }

    public void setTasDocYear(String tasDocYear) {
        this.tasDocYear = tasDocYear;
    }

    public String getTasCarNo() {
        return tasCarNo;
    }

    public void setTasCarNo(String tasCarNo) {
        this.tasCarNo = tasCarNo;
    }

    public String getTasCarNat() {
        return tasCarNat;
    }

    public void setTasCarNat(String tasCarNat) {
        this.tasCarNat = tasCarNat;
    }

    public int getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(int carWeight) {
        this.carWeight = carWeight;
    }

    public int getCtnWeight() {
        return ctnWeight;
    }

    public void setCtnWeight(int ctnWeight) {
        this.ctnWeight = ctnWeight;
    }

    public int getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(int grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(String ctnNo) {
        this.ctnNo = ctnNo;
    }

    public int getInspDoc() {
        return inspDoc;
    }

    public void setInspDoc(int inspDoc) {
        this.inspDoc = inspDoc;
    }

    public String getTasNote() {
        return tasNote;
    }

    public void setTasNote(String tasNote) {
        this.tasNote = tasNote;
    }

    public List<RsData> getRsDataListDataTable() {
        return rsDataListDataTable;
    }

    public void setRsDataListDataTable(List<RsData> rsDataListDataTable) {
        this.rsDataListDataTable = rsDataListDataTable;
    }
    
    

}
