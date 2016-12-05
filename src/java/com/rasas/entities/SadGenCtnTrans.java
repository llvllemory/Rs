/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "SAD_GEN_CTN_TRANS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SadGenCtnTrans.findAll", query = "SELECT s FROM SadGenCtnTrans s"),
    @NamedQuery(name = "SadGenCtnTrans.findByKeyDec", query = "SELECT s FROM SadGenCtnTrans s WHERE s.keyDec = :keyDec"),
    @NamedQuery(name = "SadGenCtnTrans.findBySadRegNber", query = "SELECT s FROM SadGenCtnTrans s WHERE s.sadRegNber = :sadRegNber"),
    @NamedQuery(name = "SadGenCtnTrans.findBySadRegSerial", query = "SELECT s FROM SadGenCtnTrans s WHERE s.sadRegSerial = :sadRegSerial"),
    @NamedQuery(name = "SadGenCtnTrans.findByKeyCuo", query = "SELECT s FROM SadGenCtnTrans s WHERE s.keyCuo = :keyCuo"),
    @NamedQuery(name = "SadGenCtnTrans.findBySadRegYear", query = "SELECT s FROM SadGenCtnTrans s WHERE s.sadRegYear = :sadRegYear"),
    @NamedQuery(name = "SadGenCtnTrans.findByDecNo", query = "SELECT s FROM SadGenCtnTrans s WHERE s.decNo = :decNo"),
    @NamedQuery(name = "SadGenCtnTrans.findBySadRegDate", query = "SELECT s FROM SadGenCtnTrans s WHERE s.sadRegDate = :sadRegDate"),
    @NamedQuery(name = "SadGenCtnTrans.findByTransNo", query = "SELECT s FROM SadGenCtnTrans s WHERE s.transNo = :transNo"),
    @NamedQuery(name = "SadGenCtnTrans.findByPermFollowNo", query = "SELECT s FROM SadGenCtnTrans s WHERE s.permFollowNo = :permFollowNo"),
    @NamedQuery(name = "SadGenCtnTrans.findByTransYear", query = "SELECT s FROM SadGenCtnTrans s WHERE s.transYear = :transYear"),
    @NamedQuery(name = "SadGenCtnTrans.findByTransId", query = "SELECT s FROM SadGenCtnTrans s WHERE s.transId = :transId"),
    @NamedQuery(name = "SadGenCtnTrans.findByGoods", query = "SELECT s FROM SadGenCtnTrans s WHERE s.goods = :goods"),
    @NamedQuery(name = "SadGenCtnTrans.findByClosedFlag", query = "SELECT s FROM SadGenCtnTrans s WHERE s.closedFlag = :closedFlag"),
    @NamedQuery(name = "SadGenCtnTrans.findByCarCtnIdent", query = "SELECT s FROM SadGenCtnTrans s WHERE s.carCtnIdent = :carCtnIdent"),
    @NamedQuery(name = "SadGenCtnTrans.findByTards", query = "SELECT s FROM SadGenCtnTrans s WHERE s.tards = :tards")})
public class SadGenCtnTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Size(max = 51)
    @Column(name = "KEY_DEC")
    private String keyDec;
    @Column(name = "SAD_REG_NBER")
    private Integer sadRegNber;
    @Size(max = 3)
    @Column(name = "SAD_REG_SERIAL")
    private String sadRegSerial;
    @Size(max = 15)
    @Column(name = "KEY_CUO")
    private String keyCuo;
    @Size(max = 12)
    @Column(name = "SAD_REG_YEAR")
    private String sadRegYear;
    @Size(max = 159)
    @Column(name = "DEC_NO")
    private String decNo;
    @Column(name = "SAD_REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sadRegDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANS_NO")
    private Integer transNo;
    @Column(name = "PERM_FOLLOW_NO")
    private Integer permFollowNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANS_YEAR")
    private Integer transYear;
    @Column(name = "TRANS_ID")
    private Integer transId;
    @Size(max = 402)
    @Column(name = "GOODS")
    private String goods;
    @Column(name = "CLOSED_FLAG")
    private Short closedFlag;
    @Size(max = 51)
    @Column(name = "CAR_CTN_IDENT")
    private String carCtnIdent;
    @Column(name = "TARDS")
    private Integer tards;

    public SadGenCtnTrans() {
    }

    public String getKeyDec() {
        return keyDec;
    }

    public void setKeyDec(String keyDec) {
        this.keyDec = keyDec;
    }

    public Integer getSadRegNber() {
        return sadRegNber;
    }

    public void setSadRegNber(Integer sadRegNber) {
        this.sadRegNber = sadRegNber;
    }

    public String getSadRegSerial() {
        return sadRegSerial;
    }

    public void setSadRegSerial(String sadRegSerial) {
        this.sadRegSerial = sadRegSerial;
    }

    public String getKeyCuo() {
        return keyCuo;
    }

    public void setKeyCuo(String keyCuo) {
        this.keyCuo = keyCuo;
    }

    public String getSadRegYear() {
        return sadRegYear;
    }

    public void setSadRegYear(String sadRegYear) {
        this.sadRegYear = sadRegYear;
    }

    public String getDecNo() {
        return decNo;
    }

    public void setDecNo(String decNo) {
        this.decNo = decNo;
    }

    public Date getSadRegDate() {
        return sadRegDate;
    }

    public void setSadRegDate(Date sadRegDate) {
        this.sadRegDate = sadRegDate;
    }

    public Integer getTransNo() {
        return transNo;
    }

    public void setTransNo(Integer transNo) {
        this.transNo = transNo;
    }

    public Integer getPermFollowNo() {
        return permFollowNo;
    }

    public void setPermFollowNo(Integer permFollowNo) {
        this.permFollowNo = permFollowNo;
    }

    public Integer getTransYear() {
        return transYear;
    }

    public void setTransYear(Integer transYear) {
        this.transYear = transYear;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Short getClosedFlag() {
        return closedFlag;
    }

    public void setClosedFlag(Short closedFlag) {
        this.closedFlag = closedFlag;
    }

    public String getCarCtnIdent() {
        return carCtnIdent;
    }

    public void setCarCtnIdent(String carCtnIdent) {
        this.carCtnIdent = carCtnIdent;
    }

    public Integer getTards() {
        return tards;
    }

    public void setTards(Integer tards) {
        this.tards = tards;
    }
    
}
