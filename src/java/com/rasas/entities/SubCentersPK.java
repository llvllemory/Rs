/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
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
public class SubCentersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CENTER_NO")
    private String centerNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SUB_CENTER_NO")
    private String subCenterNo;

    public SubCentersPK() {
    }

    public SubCentersPK(String centerNo, String subCenterNo) {
        this.centerNo = centerNo;
        this.subCenterNo = subCenterNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (centerNo != null ? centerNo.hashCode() : 0);
        hash += (subCenterNo != null ? subCenterNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCentersPK)) {
            return false;
        }
        SubCentersPK other = (SubCentersPK) object;
        if ((this.centerNo == null && other.centerNo != null) || (this.centerNo != null && !this.centerNo.equals(other.centerNo))) {
            return false;
        }
        if ((this.subCenterNo == null && other.subCenterNo != null) || (this.subCenterNo != null && !this.subCenterNo.equals(other.subCenterNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.SubCentersPK[ centerNo=" + centerNo + ", subCenterNo=" + subCenterNo + " ]";
    }
    
}
