/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasas.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Naser
 */
@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName"),
    @NamedQuery(name = "Users.findByUserType", query = "SELECT u FROM Users u WHERE u.userType = :userType"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByPrivilege", query = "SELECT u FROM Users u WHERE u.privilege = :privilege"),
    @NamedQuery(name = "Users.findByEntryDate", query = "SELECT u FROM Users u WHERE u.entryDate = :entryDate"),
    @NamedQuery(name = "Users.findByLastLogin", query = "SELECT u FROM Users u WHERE u.lastLogin = :lastLogin")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USER_ID")
    private String userId;
    @Size(max = 50)
    @Column(name = "USER_NAME")
    private String userName;
    @Size(max = 1)
    @Column(name = "USER_TYPE")
    private String userType;
    @Size(max = 32)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PRIVILEGE")
    private Integer privilege;
    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;
    @Column(name = "LAST_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "users")
    private GroupMembers groupMembers;
    @OneToMany(mappedBy = "groupId")
    private Collection<GroupMembers> groupMembersCollection;
    @JoinColumn(name = "USER_CENTER", referencedColumnName = "CENTER_NO")
    @ManyToOne
    private Centers userCenter;
    @JoinColumn(name = "USER_SUB_CENTER", referencedColumnName = "SUB_CENTER_NO")
    @ManyToOne
    private SubCenters userSubCenter;

    public Users() {
    }

    public Users(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public GroupMembers getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(GroupMembers groupMembers) {
        this.groupMembers = groupMembers;
    }

    @XmlTransient
    public Collection<GroupMembers> getGroupMembersCollection() {
        return groupMembersCollection;
    }

    public void setGroupMembersCollection(Collection<GroupMembers> groupMembersCollection) {
        this.groupMembersCollection = groupMembersCollection;
    }

    public Centers getUserCenter() {
        return userCenter;
    }

    public void setUserCenter(Centers userCenter) {
        this.userCenter = userCenter;
    }

    public SubCenters getUserSubCenter() {
        return userSubCenter;
    }

    public void setUserSubCenter(SubCenters userSubCenter) {
        this.userSubCenter = userSubCenter;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rasas.entities.Users[ userId=" + userId + " ]";
    }
    
}
