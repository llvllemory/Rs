/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Naser
 */
@Embeddable
public class RsDataPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "RS_NO")
    private Integer rsNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "RS_YEAR")
    private String rsYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RS_SUB_CENTER")
    private String rsSubCenter;

    public RsDataPK() {
    }

    public RsDataPK(Integer rsNo, String rsYear, String rsSubCenter) {
        this.rsNo = rsNo;
        this.rsYear = rsYear;
        this.rsSubCenter = rsSubCenter;
    }

    public Integer getRsNo() {
        return rsNo;
    }

    public void setRsNo(Integer rsNo) {
        this.rsNo = rsNo;
    }

    public String getRsYear() {
        return rsYear;
    }

    public void setRsYear(String rsYear) {
        this.rsYear = rsYear;
    }

    public String getRsSubCenter() {
        return rsSubCenter;
    }

    public void setRsSubCenter(String rsSubCenter) {
        this.rsSubCenter = rsSubCenter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rsNo != null ? rsNo.hashCode() : 0);
        hash += (rsYear != null ? rsYear.hashCode() : 0);
        hash += (rsSubCenter != null ? rsSubCenter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsDataPK)) {
            return false;
        }
        RsDataPK other = (RsDataPK) object;
        if ((this.rsNo == null && other.rsNo != null) || (this.rsNo != null && !this.rsNo.equals(other.rsNo))) {
            return false;
        }
        if ((this.rsYear == null && other.rsYear != null) || (this.rsYear != null && !this.rsYear.equals(other.rsYear))) {
            return false;
        }
        if ((this.rsSubCenter == null && other.rsSubCenter != null) || (this.rsSubCenter != null && !this.rsSubCenter.equals(other.rsSubCenter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.RsDataPK[ rsNo=" + rsNo + ", rsYear=" + rsYear + ", rsSubCenter=" + rsSubCenter + " ]";
    }
    
}
