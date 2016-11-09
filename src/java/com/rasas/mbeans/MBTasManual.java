package com.rasas.mbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
    private String tasNote;
    
    
    public MBTasManual(){
        
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String checkRsToClose(){
        System.out.println("com.rasas.mbeans.MBTasManual.checkRsToClose()");
        
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
        
        
        
        System.out.println("------------------------------------------------------------- " );
        return "";
    }
////////////////////////////////////////////////////////////////////////////////


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

    public String getTasNote() {
        return tasNote;
    }

    public void setTasNote(String tasNote) {
        this.tasNote = tasNote;
    }

    
    
    
    
    
    
}
