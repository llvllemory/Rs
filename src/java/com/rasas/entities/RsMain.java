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
@Table(name = "RS_MAIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RsMain.findAll", query = "SELECT r FROM RsMain r"),
    @NamedQuery(name = "RsMain.findByRsNo", query = "SELECT r FROM RsMain r WHERE r.rsMainPK.rsNo = :rsNo"),
    @NamedQuery(name = "RsMain.findByRsYear", query = "SELECT r FROM RsMain r WHERE r.rsMainPK.rsYear = :rsYear"),
    @NamedQuery(name = "RsMain.findByRsCenter", query = "SELECT r FROM RsMain r WHERE r.rsMainPK.rsCenter = :rsCenter"),
    @NamedQuery(name = "RsMain.findByRsCenterDate", query = "SELECT r FROM RsMain r WHERE r.rsCenterDate = :rsCenterDate"),
    @NamedQuery(name = "RsMain.findByRsCenterUserId", query = "SELECT r FROM RsMain r WHERE r.rsCenterUserId = :rsCenterUserId"),
    @NamedQuery(name = "RsMain.findByRsSubCenter", query = "SELECT r FROM RsMain r WHERE r.rsSubCenter = :rsSubCenter"),
    @NamedQuery(name = "RsMain.findByRsSubCenterDate", query = "SELECT r FROM RsMain r WHERE r.rsSubCenterDate = :rsSubCenterDate"),
    @NamedQuery(name = "RsMain.findByRsSubCenterUserId", query = "SELECT r FROM RsMain r WHERE r.rsSubCenterUserId = :rsSubCenterUserId")})
public class RsMain implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RsMainPK rsMainPK;
    @Column(name = "RS_CENTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rsCenterDate;
    @Size(max = 10)
    @Column(name = "RS_CENTER_USER_ID")
    private String rsCenterUserId;
    @Size(max = 3)
    @Column(name = "RS_SUB_CENTER")
    private String rsSubCenter;
    @Column(name = "RS_SUB_CENTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rsSubCenterDate;
    @Size(max = 10)
    @Column(name = "RS_SUB_CENTER_USER_ID")
    private String rsSubCenterUserId;

    public RsMain() {
    }

    public RsMain(RsMainPK rsMainPK) {
        this.rsMainPK = rsMainPK;
    }

    public RsMain(Integer rsNo, String rsYear, String rsCenter) {
        this.rsMainPK = new RsMainPK(rsNo, rsYear, rsCenter);
    }

    public RsMainPK getRsMainPK() {
        return rsMainPK;
    }

    public void setRsMainPK(RsMainPK rsMainPK) {
        this.rsMainPK = rsMainPK;
    }

    public Date getRsCenterDate() {
        return rsCenterDate;
    }

    public void setRsCenterDate(Date rsCenterDate) {
        this.rsCenterDate = rsCenterDate;
    }

    public String getRsCenterUserId() {
        return rsCenterUserId;
    }

    public void setRsCenterUserId(String rsCenterUserId) {
        this.rsCenterUserId = rsCenterUserId;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    }

    public Date getRsSubCenterDate() {
        return rsSubCenterDate;
    }

    public void setRsSubCenterDate(Date rsSubCenterDate) {
        this.rsSubCenterDate = rsSubCenterDate;
    }

    public String getRsSubCenterUserId() {
        return rsSubCenterUserId;
    }

    public void setRsSubCenterUserId(String rsSubCenterUserId) {
        this.rsSubCenterUserId = rsSubCenterUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rsMainPK != null ? rsMainPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsMain)) {
            return false;
        }
        RsMain other = (RsMain) object;
        if ((this.rsMainPK == null && other.rsMainPK != null) || (this.rsMainPK != null && !this.rsMainPK.equals(other.rsMainPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.RsMain[ rsMainPK=" + rsMainPK + " ]";
    }
    
}
