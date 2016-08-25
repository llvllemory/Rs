/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "SubCenters.findByCenterNo", query = "SELECT s FROM SubCenters s WHERE s.subCentersPK.centerNo = :centerNo"),
    @NamedQuery(name = "SubCenters.findBySubCenterNo", query = "SELECT s FROM SubCenters s WHERE s.subCentersPK.subCenterNo = :subCenterNo"),
    @NamedQuery(name = "SubCenters.findBySubCenterName", query = "SELECT s FROM SubCenters s WHERE s.subCenterName = :subCenterName")})
public class SubCenters implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SubCentersPK subCentersPK;
    @Size(max = 100)
    @Column(name = "SUB_CENTER_NAME")
    private String subCenterName;

    public SubCenters() {
    }

    public SubCenters(SubCentersPK subCentersPK) {
        this.subCentersPK = subCentersPK;
    }

    public SubCenters(String centerNo, String subCenterNo) {
        this.subCentersPK = new SubCentersPK(centerNo, subCenterNo);
    }

    public SubCentersPK getSubCentersPK() {
        return subCentersPK;
    }

    public void setSubCentersPK(SubCentersPK subCentersPK) {
        this.subCentersPK = subCentersPK;
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
        hash += (subCentersPK != null ? subCentersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCenters)) {
            return false;
        }
        SubCenters other = (SubCenters) object;
        if ((this.subCentersPK == null && other.subCentersPK != null) || (this.subCentersPK != null && !this.subCentersPK.equals(other.subCentersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.SubCenters[ subCentersPK=" + subCentersPK + " ]";
    }
    
}
