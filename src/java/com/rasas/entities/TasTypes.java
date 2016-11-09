/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TAS_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TasTypes.findAll", query = "SELECT t FROM TasTypes t"),
    @NamedQuery(name = "TasTypes.findByTasType", query = "SELECT t FROM TasTypes t WHERE t.tasType = :tasType"),
    @NamedQuery(name = "TasTypes.findByTasDesc", query = "SELECT t FROM TasTypes t WHERE t.tasDesc = :tasDesc")})
public class TasTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAS_TYPE")
    private BigDecimal tasType;
    @Size(max = 50)
    @Column(name = "TAS_DESC")
    private String tasDesc;

    public TasTypes() {
    }

    public TasTypes(BigDecimal tasType) {
        this.tasType = tasType;
    }

    public BigDecimal getTasType() {
        return tasType;
    }

    public void setTasType(BigDecimal tasType) {
        this.tasType = tasType;
    }

    public String getTasDesc() {
        return tasDesc;
    }

    public void setTasDesc(String tasDesc) {
        this.tasDesc = tasDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tasType != null ? tasType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TasTypes)) {
            return false;
        }
        TasTypes other = (TasTypes) object;
        if ((this.tasType == null && other.tasType != null) || (this.tasType != null && !this.tasType.equals(other.tasType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.TasTypes[ tasType=" + tasType + " ]";
    }
    
}
