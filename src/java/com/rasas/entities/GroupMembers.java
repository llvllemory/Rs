/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "GroupMembers.findByGroupId", query = "SELECT g FROM GroupMembers g WHERE g.groupMembersPK.groupId = :groupId"),
    @NamedQuery(name = "GroupMembers.findByUserId", query = "SELECT g FROM GroupMembers g WHERE g.groupMembersPK.userId = :userId"),
    @NamedQuery(name = "GroupMembers.findByEntryDate", query = "SELECT g FROM GroupMembers g WHERE g.entryDate = :entryDate")})
public class GroupMembers implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupMembersPK groupMembersPK;
    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    public GroupMembers() {
    }

    public GroupMembers(GroupMembersPK groupMembersPK) {
        this.groupMembersPK = groupMembersPK;
    }

    public GroupMembers(String groupId, String userId) {
        this.groupMembersPK = new GroupMembersPK(groupId, userId);
    }

    public GroupMembersPK getGroupMembersPK() {
        return groupMembersPK;
    }

    public void setGroupMembersPK(GroupMembersPK groupMembersPK) {
        this.groupMembersPK = groupMembersPK;
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
        hash += (groupMembersPK != null ? groupMembersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupMembers)) {
            return false;
        }
        GroupMembers other = (GroupMembers) object;
        if ((this.groupMembersPK == null && other.groupMembersPK != null) || (this.groupMembersPK != null && !this.groupMembersPK.equals(other.groupMembersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.GroupMembers[ groupMembersPK=" + groupMembersPK + " ]";
    }
    
}
