/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "RS_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RsData.findAll", query = "SELECT r FROM RsData r"),
    @NamedQuery(name = "RsData.findByRsNo", query = "SELECT r FROM RsData r WHERE r.rsDataPK.rsNo = :rsNo"),
    @NamedQuery(name = "RsData.findByRsYear", query = "SELECT r FROM RsData r WHERE r.rsDataPK.rsYear = :rsYear"),
    @NamedQuery(name = "RsData.findByRsCenter", query = "SELECT r FROM RsData r WHERE r.rsDataPK.rsCenter = :rsCenter"),
    @NamedQuery(name = "RsData.findByRsSubCenter", query = "SELECT r FROM RsData r WHERE r.rsSubCenter = :rsSubCenter"),
    @NamedQuery(name = "RsData.findByRsEntryDate", query = "SELECT r FROM RsData r WHERE r.rsEntryDate = :rsEntryDate"),
    @NamedQuery(name = "RsData.findByRsUserId", query = "SELECT r FROM RsData r WHERE r.rsUserId = :rsUserId"),
    @NamedQuery(name = "RsData.findByRsTasDocNo", query = "SELECT r FROM RsData r WHERE r.rsTasDocNo = :rsTasDocNo"),
    @NamedQuery(name = "RsData.findByRsTasCodYear", query = "SELECT r FROM RsData r WHERE r.rsTasCodYear = :rsTasCodYear"),
    @NamedQuery(name = "RsData.findByRsTasCodType", query = "SELECT r FROM RsData r WHERE r.rsTasCodType = :rsTasCodType"),
    @NamedQuery(name = "RsData.findByRsTasDate", query = "SELECT r FROM RsData r WHERE r.rsTasDate = :rsTasDate"),
    @NamedQuery(name = "RsData.findByRsTasUserId", query = "SELECT r FROM RsData r WHERE r.rsTasUserId = :rsTasUserId"),
    @NamedQuery(name = "RsData.findByRsCarNo", query = "SELECT r FROM RsData r WHERE r.rsCarNo = :rsCarNo"),
    @NamedQuery(name = "RsData.findByRsCarNat", query = "SELECT r FROM RsData r WHERE r.rsCarNat = :rsCarNat"),
    @NamedQuery(name = "RsData.findByRsCarWeight", query = "SELECT r FROM RsData r WHERE r.rsCarWeight = :rsCarWeight"),
    @NamedQuery(name = "RsData.findByRsCtnNo", query = "SELECT r FROM RsData r WHERE r.rsCtnNo = :rsCtnNo"),
    @NamedQuery(name = "RsData.findByRsCtnWeight", query = "SELECT r FROM RsData r WHERE r.rsCtnWeight = :rsCtnWeight"),
    @NamedQuery(name = "RsData.findByRsGrossWeight", query = "SELECT r FROM RsData r WHERE r.rsGrossWeight = :rsGrossWeight")})
public class RsData implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RsDataPK rsDataPK;
    @Size(max = 3)
    @Column(name = "RS_SUB_CENTER")
    private String rsSubCenter;
    @Column(name = "RS_ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rsEntryDate;
    @Size(max = 10)
    @Column(name = "RS_USER_ID")
    private String rsUserId;
    @Column(name = "RS_TAS_DOC_NO")
    private Integer rsTasDocNo;
    @Size(max = 4)
    @Column(name = "RS_TAS_COD_YEAR")
    private String rsTasCodYear;
    @Column(name = "RS_TAS_COD_TYPE")
    private Integer rsTasCodType;
    @Column(name = "RS_TAS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rsTasDate;
    @Size(max = 10)
    @Column(name = "RS_TAS_USER_ID")
    private String rsTasUserId;
    @Size(max = 10)
    @Column(name = "RS_CAR_NO")
    private String rsCarNo;
    @Size(max = 3)
    @Column(name = "RS_CAR_NAT")
    private String rsCarNat;
    @Column(name = "RS_CAR_WEIGHT")
    private Integer rsCarWeight;
    @Size(max = 11)
    @Column(name = "RS_CTN_NO")
    private String rsCtnNo;
    @Column(name = "RS_CTN_WEIGHT")
    private Integer rsCtnWeight;
    @Column(name = "RS_GROSS_WEIGHT")
    private Integer rsGrossWeight;

    public RsData() {
    }

    public RsData(RsDataPK rsDataPK) {
        this.rsDataPK = rsDataPK;
    }

    public RsData(Integer rsNo, String rsYear, String rsCenter) {
        this.rsDataPK = new RsDataPK(rsNo, rsYear, rsCenter);
    }

    public RsDataPK getRsDataPK() {
        return rsDataPK;
    }

    public void setRsDataPK(RsDataPK rsDataPK) {
        this.rsDataPK = rsDataPK;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    }

    public Date getRsEntryDate() {
        return rsEntryDate;
    }

    public void setRsEntryDate(Date rsEntryDate) {
        this.rsEntryDate = rsEntryDate;
    }

    public String getRsUserId() {
        return rsUserId;
    }

    public void setRsUserId(String rsUserId) {
        this.rsUserId = rsUserId;
    }

    public Integer getRsTasDocNo() {
        return rsTasDocNo;
    }

    public void setRsTasDocNo(Integer rsTasDocNo) {
        this.rsTasDocNo = rsTasDocNo;
    }

    public String getRsTasCodYear() {
        return rsTasCodYear;
    }

    public void setRsTasCodYear(String rsTasCodYear) {
        this.rsTasCodYear = rsTasCodYear;
    }

    public Integer getRsTasCodType() {
        return rsTasCodType;
    }

    public void setRsTasCodType(Integer rsTasCodType) {
        this.rsTasCodType = rsTasCodType;
    }

    public Date getRsTasDate() {
        return rsTasDate;
    }

    public void setRsTasDate(Date rsTasDate) {
        this.rsTasDate = rsTasDate;
    }

    public String getRsTasUserId() {
        return rsTasUserId;
    }

    public void setRsTasUserId(String rsTasUserId) {
        this.rsTasUserId = rsTasUserId;
    }

    public String getRsCarNo() {
        return rsCarNo;
    }

    public void setRsCarNo(String rsCarNo) {
        this.rsCarNo = rsCarNo;
    }

    public String getRsCarNat() {
        return rsCarNat;
    }

    public void setRsCarNat(String rsCarNat) {
        this.rsCarNat = rsCarNat;
    }

    public Integer getRsCarWeight() {
        return rsCarWeight;
    }

    public void setRsCarWeight(Integer rsCarWeight) {
        this.rsCarWeight = rsCarWeight;
    }

    public String getRsCtnNo() {
        return rsCtnNo;
    }

    public void setRsCtnNo(String rsCtnNo) {
        this.rsCtnNo = rsCtnNo;
    }

    public Integer getRsCtnWeight() {
        return rsCtnWeight;
    }

    public void setRsCtnWeight(Integer rsCtnWeight) {
        this.rsCtnWeight = rsCtnWeight;
    }

    public Integer getRsGrossWeight() {
        return rsGrossWeight;
    }

    public void setRsGrossWeight(Integer rsGrossWeight) {
        this.rsGrossWeight = rsGrossWeight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rsDataPK != null ? rsDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsData)) {
            return false;
        }
        RsData other = (RsData) object;
        if ((this.rsDataPK == null && other.rsDataPK != null) || (this.rsDataPK != null && !this.rsDataPK.equals(other.rsDataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.RsData[ rsDataPK=" + rsDataPK + " ]";
    }
    
}
