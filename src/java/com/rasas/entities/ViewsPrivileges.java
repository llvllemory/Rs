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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "VIEWS_PRIVILEGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewsPrivileges.findAll", query = "SELECT v FROM ViewsPrivileges v"),
    @NamedQuery(name = "ViewsPrivileges.findByGroupId", query = "SELECT v FROM ViewsPrivileges v WHERE v.viewsPrivilegesPK.groupId = :groupId"),
    @NamedQuery(name = "ViewsPrivileges.findByViewId", query = "SELECT v FROM ViewsPrivileges v WHERE v.viewsPrivilegesPK.viewId = :viewId"),
    @NamedQuery(name = "ViewsPrivileges.findByCanView", query = "SELECT v FROM ViewsPrivileges v WHERE v.canView = :canView"),
    @NamedQuery(name = "ViewsPrivileges.findByCanSave", query = "SELECT v FROM ViewsPrivileges v WHERE v.canSave = :canSave"),
    @NamedQuery(name = "ViewsPrivileges.findByCanDelete", query = "SELECT v FROM ViewsPrivileges v WHERE v.canDelete = :canDelete"),
    @NamedQuery(name = "ViewsPrivileges.findByCanUpdate", query = "SELECT v FROM ViewsPrivileges v WHERE v.canUpdate = :canUpdate"),
    @NamedQuery(name = "ViewsPrivileges.findByCanPrint", query = "SELECT v FROM ViewsPrivileges v WHERE v.canPrint = :canPrint")})
public class ViewsPrivileges implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ViewsPrivilegesPK viewsPrivilegesPK;
    @Size(max = 5)
    @Column(name = "CAN_VIEW")
    private String canView;
    @Size(max = 5)
    @Column(name = "CAN_SAVE")
    private String canSave;
    @Size(max = 5)
    @Column(name = "CAN_DELETE")
    private String canDelete;
    @Size(max = 5)
    @Column(name = "CAN_UPDATE")
    private String canUpdate;
    @Size(max = 5)
    @Column(name = "CAN_PRINT")
    private String canPrint;
    @JoinColumn(name = "VIEW_ID", referencedColumnName = "VIEW_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Views views;

    public ViewsPrivileges() {
    }

    public ViewsPrivileges(ViewsPrivilegesPK viewsPrivilegesPK) {
        this.viewsPrivilegesPK = viewsPrivilegesPK;
    }

    public ViewsPrivileges(String groupId, String viewId) {
        this.viewsPrivilegesPK = new ViewsPrivilegesPK(groupId, viewId);
    }

    public ViewsPrivilegesPK getViewsPrivilegesPK() {
        return viewsPrivilegesPK;
    }

    public void setViewsPrivilegesPK(ViewsPrivilegesPK viewsPrivilegesPK) {
        this.viewsPrivilegesPK = viewsPrivilegesPK;
    }

    public String getCanView() {
        return canView;
    }

    public void setCanView(String canView) {
        this.canView = canView;
    }

    public String getCanSave() {
        return canSave;
    }

    public void setCanSave(String canSave) {
        this.canSave = canSave;
    }

    public String getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(String canDelete) {
        this.canDelete = canDelete;
    }

    public String getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(String canUpdate) {
        this.canUpdate = canUpdate;
    }

    public String getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(String canPrint) {
        this.canPrint = canPrint;
    }

    public Views getViews() {
        return views;
    }

    public void setViews(Views views) {
        this.views = views;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewsPrivilegesPK != null ? viewsPrivilegesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ViewsPrivileges)) {
            return false;
        }
        ViewsPrivileges other = (ViewsPrivileges) object;
        if ((this.viewsPrivilegesPK == null && other.viewsPrivilegesPK != null) || (this.viewsPrivilegesPK != null && !this.viewsPrivilegesPK.equals(other.viewsPrivilegesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.ViewsPrivileges[ viewsPrivilegesPK=" + viewsPrivilegesPK + " ]";
    }
    
}
