/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "GROUP_MEMBERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupMembers.findAll", query = "SELECT g FROM GroupMembers g"),
    @NamedQuery(name = "GroupMembers.findByGroupId", query = "SELECT g FROM GroupMembers g WHERE g.groupId = :groupId"),
    @NamedQuery(name = "GroupMembers.findByUserId", query = "SELECT g FROM GroupMembers g WHERE g.userId = :userId"),
    @NamedQuery(name = "GroupMembers.findByEntryDate", query = "SELECT g FROM GroupMembers g WHERE g.entryDate = :entryDate")})
public class GroupMembers implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Size(max = 10)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    public GroupMembers() {
    }

    public GroupMembers(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
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
        if (!(object instanceof GroupMembers)) {
            return false;
        }
        GroupMembers other = (GroupMembers) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.GroupMembers[ userId=" + userId + " ]";
    }
    
}
