package com.rasas.mbeans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped
public class MBTasPerm implements Serializable{
    
    private int barcode;
    private int permNo;
    private String permYear;
    private int sadRegNber;
    private int sadRegSerial;
    private String sadRegYear;
    private Date sadRegDate;
    private int keyCuo;
    private String keyDec;
    private String keyDecName;
    private String ctnNo;
    private int packNo;
    private String decGoods;
    
    private int rsDocType;
    private int rsFrom;
    private int rsTo;
    private String rsYear;
    private String rsCarNo;
    private int rsCarNat;
    private int rsCarWeight;
    private int rsCtnWeight;
    private int rsGrossWeight;
    private int rsInspDoc;
    private String rsNote;

    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBTasPerm(){
        
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
////////////////////////////////////////////////////////////////////////////////    
    
    
    
////////////////////////////////////////////////////////////////////////////////    

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getPermNo() {
        return permNo;
    }

    public void setPermNo(int permNo) {
        this.permNo = permNo;
    }

    public String getPermYear() {
        return permYear;
    }

    public void setPermYear(String permYear) {
        this.permYear = permYear;
    }

    public int getSadRegNber() {
        return sadRegNber;
    }

    public void setSadRegNber(int sadRegNber) {
        this.sadRegNber = sadRegNber;
    }

    public int getSadRegSerial() {
        return sadRegSerial;
    }

    public void setSadRegSerial(int sadRegSerial) {
        this.sadRegSerial = sadRegSerial;
    }

    public String getSadRegYear() {
        return sadRegYear;
    }

    public void setSadRegYear(String sadRegYear) {
        this.sadRegYear = sadRegYear;
    }

    public Date getSadRegDate() {
        return sadRegDate;
    }

    public void setSadRegDate(Date sadRegDate) {
        this.sadRegDate = sadRegDate;
    }

    public int getKeyCuo() {
        return keyCuo;
    }

    public void setKeyCuo(int keyCuo) {
        this.keyCuo = keyCuo;
    }

    public String getKeyDec() {
        return keyDec;
    }

    public void setKeyDec(String keyDec) {
        this.keyDec = keyDec;
    }

    public String getKeyDecName() {
        return keyDecName;
    }

    public void setKeyDecName(String keyDecName) {
        this.keyDecName = keyDecName;
    }

    public String getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(String ctnNo) {
        this.ctnNo = ctnNo;
    }

    public int getPackNo() {
        return packNo;
    }

    public void setPackNo(int packNo) {
        this.packNo = packNo;
    }

    public String getDecGoods() {
        return decGoods;
    }

    public void setDecGoods(String decGoods) {
        this.decGoods = decGoods;
    }

    public int getRsDocType() {
        return rsDocType;
    }

    public void setRsDocType(int rsDocType) {
        this.rsDocType = rsDocType;
    }

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

    public String getRsCarNo() {
        return rsCarNo;
    }

    public void setRsCarNo(String rsCarNo) {
        this.rsCarNo = rsCarNo;
    }

    public int getRsCarNat() {
        return rsCarNat;
    }

    public void setRsCarNat(int rsCarNat) {
        this.rsCarNat = rsCarNat;
    }

    public int getRsCarWeight() {
        return rsCarWeight;
    }

    public void setRsCarWeight(int rsCarWeight) {
        this.rsCarWeight = rsCarWeight;
    }

    public int getRsCtnWeight() {
        return rsCtnWeight;
    }

    public void setRsCtnWeight(int rsCtnWeight) {
        this.rsCtnWeight = rsCtnWeight;
    }

    public int getRsGrossWeight() {
        return rsGrossWeight;
    }

    public void setRsGrossWeight(int rsGrossWeight) {
        this.rsGrossWeight = rsGrossWeight;
    }

    public int getRsInspDoc() {
        return rsInspDoc;
    }

    public void setRsInspDoc(int rsInspDoc) {
        this.rsInspDoc = rsInspDoc;
    }

    public String getRsNote() {
        return rsNote;
    }

    public void setRsNote(String rsNote) {
        this.rsNote = rsNote;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
}
