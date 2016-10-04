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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "VIEWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Views.findAll", query = "SELECT v FROM Views v"),
    @NamedQuery(name = "Views.findByViewId", query = "SELECT v FROM Views v WHERE v.viewId = :viewId"),
    @NamedQuery(name = "Views.findByViewName", query = "SELECT v FROM Views v WHERE v.viewName = :viewName")})
public class Views implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VIEW_ID")
    private String viewId;
    @Size(max = 100)
    @Column(name = "VIEW_NAME")
    private String viewName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "views")
    private Collection<ViewsPrivileges> viewsPrivilegesCollection;

    public Views() {
    }

    public Views(String viewId) {
        this.viewId = viewId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @XmlTransient
    public Collection<ViewsPrivileges> getViewsPrivilegesCollection() {
        return viewsPrivilegesCollection;
    }

    public void setViewsPrivilegesCollection(Collection<ViewsPrivileges> viewsPrivilegesCollection) {
        this.viewsPrivilegesCollection = viewsPrivilegesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewId != null ? viewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Views)) {
            return false;
        }
        Views other = (Views) object;
        if ((this.viewId == null && other.viewId != null) || (this.viewId != null && !this.viewId.equals(other.viewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.Views[ viewId=" + viewId + " ]";
    }
    
}
