/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "ViewPrivilege.findByUserId", query = "SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.userId = :userId"),
    @NamedQuery(name = "ViewPrivilege.findByViewId", query = "SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.viewId = :viewId"),
    @NamedQuery(name = "ViewPrivilege.findByPrivilege", query = "SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.privilege = :privilege")})
public class ViewPrivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ViewPrivilegePK viewPrivilegePK;

    public ViewPrivilege() {
    }

    public ViewPrivilege(ViewPrivilegePK viewPrivilegePK) {
        this.viewPrivilegePK = viewPrivilegePK;
    }

    public ViewPrivilege(String userId, String viewId, String privilege) {
        this.viewPrivilegePK = new ViewPrivilegePK(userId, viewId, privilege);
    }

    public ViewPrivilegePK getViewPrivilegePK() {
        return viewPrivilegePK;
    }

    public void setViewPrivilegePK(ViewPrivilegePK viewPrivilegePK) {
        this.viewPrivilegePK = viewPrivilegePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewPrivilegePK != null ? viewPrivilegePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewPrivilege)) {
            return false;
        }
        ViewPrivilege other = (ViewPrivilege) object;
        if ((this.viewPrivilegePK == null && other.viewPrivilegePK != null) || (this.viewPrivilegePK != null && !this.viewPrivilegePK.equals(other.viewPrivilegePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.ViewPrivilege[ viewPrivilegePK=" + viewPrivilegePK + " ]";
    }
    
}
