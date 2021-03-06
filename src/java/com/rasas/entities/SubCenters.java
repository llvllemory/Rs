/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "SUB_CENTERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubCenters.findAll", query = "SELECT s FROM SubCenters s"),
    @NamedQuery(name = "SubCenters.findByCenterNo", query = "SELECT s FROM SubCenters s WHERE s.centerNo = :centerNo"),
    @NamedQuery(name = "SubCenters.findBySubCenterNo", query = "SELECT s FROM SubCenters s WHERE s.subCenterNo = :subCenterNo"),
    @NamedQuery(name = "SubCenters.findBySubCenterName", query = "SELECT s FROM SubCenters s WHERE s.subCenterName = :subCenterName")})
public class SubCenters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 3)
    @Column(name = "CENTER_NO")
    private String centerNo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SUB_CENTER_NO")
    private String subCenterNo;
    @Size(max = 100)
    @Column(name = "SUB_CENTER_NAME")
    private String subCenterName;

    public SubCenters() {
    }

    public SubCenters(String subCenterNo) {
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

    public String getSubCenterName() {
        return subCenterName;
    }

    public void setSubCenterName(String subCenterName) {
        this.subCenterName = subCenterName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subCenterNo != null ? subCenterNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCenters)) {
            return false;
        }
        SubCenters other = (SubCenters) object;
        if ((this.subCenterNo == null && other.subCenterNo != null) || (this.subCenterNo != null && !this.subCenterNo.equals(other.subCenterNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.SubCenters[ subCenterNo=" + subCenterNo + " ]";
    }
    
}
