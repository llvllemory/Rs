/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "CENTERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centers.findAll", query = "SELECT c FROM Centers c"),
    @NamedQuery(name = "Centers.findByCenterNo", query = "SELECT c FROM Centers c WHERE c.centerNo = :centerNo"),
    @NamedQuery(name = "Centers.findByCenterName", query = "SELECT c FROM Centers c WHERE c.centerName = :centerName")})
public class Centers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CENTER_NO")
    private String centerNo;
    @Size(max = 100)
    @Column(name = "CENTER_NAME")
    private String centerName;
    
    @OneToMany(mappedBy = "centerNo")
    private Collection<SubCenters> subCentersCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "centers1")
    private Centers centers;
    @JoinColumn(name = "CENTER_NO", referencedColumnName = "CENTER_NO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Centers centers1;
    @OneToMany(mappedBy = "userCenter")
    private Collection<Users> usersCollection;

    public Centers() {
    }

    public Centers(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    @XmlTransient
    public Collection<SubCenters> getSubCentersCollection() {
        return subCentersCollection;
    }

    public void setSubCentersCollection(Collection<SubCenters> subCentersCollection) {
        this.subCentersCollection = subCentersCollection;
    }

    public Centers getCenters() {
        return centers;
    }

    public void setCenters(Centers centers) {
        this.centers = centers;
    }

    public Centers getCenters1() {
        return centers1;
    }

    public void setCenters1(Centers centers1) {
        this.centers1 = centers1;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (centerNo != null ? centerNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centers)) {
            return false;
        }
        Centers other = (Centers) object;
        if ((this.centerNo == null && other.centerNo != null) || (this.centerNo != null && !this.centerNo.equals(other.centerNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.Centers[ centerNo=" + centerNo + " ]";
    }
    
}
