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
@Table(name = "VIEW_PRIVILEGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewPrivilege.findAll", query = "SELECT v FROM ViewPrivilege v"),
    @NamedQuery(name = "ViewPrivilege.findByUserId", query = "SELECT v FROM ViewPrivilege v WHERE v.userId = :userId"),
    @NamedQuery(name = "ViewPrivilege.findByViewId", query = "SELECT v FROM ViewPrivilege v WHERE v.viewId = :viewId"),
    @NamedQuery(name = "ViewPrivilege.findByPrivilege", query = "SELECT v FROM ViewPrivilege v WHERE v.privilege = :privilege")})
public class ViewPrivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USER_ID")
    private String userId;
    @Size(max = 30)
    @Column(name = "VIEW_ID")
    private String viewId;
    @Size(max = 6)
    @Column(name = "PRIVILEGE")
    private String privilege;

    public ViewPrivilege() {
    }

    public ViewPrivilege(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewPrivilege)) {
            return false;
        }
        ViewPrivilege other = (ViewPrivilege) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.ViewPrivilege[ userId=" + userId + " ]";
    }
    
}
