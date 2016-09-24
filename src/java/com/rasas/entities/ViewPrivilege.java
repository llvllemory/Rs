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
@Table(name = "VIEW_PRIVILEGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewPrivilege.findAll", query = "SELECT v FROM ViewPrivilege v"),
    @NamedQuery(name = "ViewPrivilege.findByGroupId", query = "SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.groupId = :groupId"),
    @NamedQuery(name = "ViewPrivilege.findByViewId", query = "SELECT v FROM ViewPrivilege v WHERE v.viewPrivilegePK.viewId = :viewId"),
    @NamedQuery(name = "ViewPrivilege.findByPrivilege", query = "SELECT v FROM ViewPrivilege v WHERE v.privilege = :privilege")})
public class ViewPrivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ViewPrivilegePK viewPrivilegePK;
    @Size(max = 6)
    @Column(name = "PRIVILEGE")
    private String privilege;

    public ViewPrivilege() {
    }

    public ViewPrivilege(ViewPrivilegePK viewPrivilegePK) {
        this.viewPrivilegePK = viewPrivilegePK;
    }

    public ViewPrivilege(String groupId, String viewId) {
        this.viewPrivilegePK = new ViewPrivilegePK(groupId, viewId);
    }

    public ViewPrivilegePK getViewPrivilegePK() {
        return viewPrivilegePK;
    }

    public void setViewPrivilegePK(ViewPrivilegePK viewPrivilegePK) {
        this.viewPrivilegePK = viewPrivilegePK;
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
