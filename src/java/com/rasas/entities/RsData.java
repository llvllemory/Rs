/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "RsData.findByRsSubCenter", query = "SELECT r FROM RsData r WHERE r.rsDataPK.rsSubCenter = :rsSubCenter"),
    @NamedQuery(name = "RsData.findByEntryDate", query = "SELECT r FROM RsData r WHERE r.entryDate = :entryDate"),
    @NamedQuery(name = "RsData.findByUserId", query = "SELECT r FROM RsData r WHERE r.userId = :userId"),
    @NamedQuery(name = "RsData.findByRsTasDate", query = "SELECT r FROM RsData r WHERE r.rsTasDate = :rsTasDate"),
    @NamedQuery(name = "RsData.findByRsTasUserId", query = "SELECT r FROM RsData r WHERE r.rsTasUserId = :rsTasUserId"),
    @NamedQuery(name = "RsData.findByRsTasType", query = "SELECT r FROM RsData r WHERE r.rsTasType = :rsTasType"),
    @NamedQuery(name = "RsData.findByCarNo", query = "SELECT r FROM RsData r WHERE r.carNo = :carNo"),
    @NamedQuery(name = "RsData.findByCarNat", query = "SELECT r FROM RsData r WHERE r.carNat = :carNat"),
    @NamedQuery(name = "RsData.findByCtnNo", query = "SELECT r FROM RsData r WHERE r.ctnNo = :ctnNo")})
public class RsData implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RsDataPK rsDataPK;
    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;
    @Size(max = 10)
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "RS_TAS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rsTasDate;
    @Size(max = 10)
    @Column(name = "RS_TAS_USER_ID")
    private String rsTasUserId;
    @Size(max = 1)
    @Column(name = "RS_TAS_TYPE")
    private String rsTasType;
    @Size(max = 10)
    @Column(name = "CAR_NO")
    private String carNo;
    @Size(max = 3)
    @Column(name = "CAR_NAT")
    private String carNat;
    @Size(max = 11)
    @Column(name = "CTN_NO")
    private String ctnNo;

    public RsData() {
    }

    public RsData(RsDataPK rsDataPK) {
        this.rsDataPK = rsDataPK;
    }

    public RsData(Integer rsNo, String rsYear, String rsSubCenter) {
        this.rsDataPK = new RsDataPK(rsNo, rsYear, rsSubCenter);
    }

    public RsDataPK getRsDataPK() {
        return rsDataPK;
    }

    public void setRsDataPK(RsDataPK rsDataPK) {
        this.rsDataPK = rsDataPK;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRsTasType() {
        return rsTasType;
    }

    public void setRsTasType(String rsTasType) {
        this.rsTasType = rsTasType;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarNat() {
        return carNat;
    }

    public void setCarNat(String carNat) {
        this.carNat = carNat;
    }

    public String getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(String ctnNo) {
        this.ctnNo = ctnNo;
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
