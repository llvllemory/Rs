package com.rasas.mbeans;

import com.rasas.entities.GroupMembers;
import com.rasas.entities.Users;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



@ManagedBean
@RequestScoped

public class MBGroupMembers {
    
    private String groupId;
    private String groupUser;
    private String groupsUser;

    private List<GroupMembers> groupIdList;
    private List<GroupMembers> groupMembersList;
            
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBGroupMembers(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkUserInGroupToAdd(){
        System.out.println("com.rasas.mbeans.MBGroupMembers.checkUserInGroupToAdd()");
           
        if(groupId == null){
            MBCommon.getErrorMessage("", "يجب إختيار المجموعة !");
            return "";
        }
        
        if(groupsUser == null){
            MBCommon.getErrorMessage("", "يجب إختيار موظف لإضافته للمجموعة !");
            return "";
        }
        
        
        groupMembersList = new ArrayList<>();
        groupMembersList = getGroubMemberByUserId(groupsUser);
        
        if(groupMembersList.size() > 0){
            if(groupMembersList.get(0).getGroupId().equals(groupId)){
                MBCommon.getErrorMessage("", "الموظف ينتمي لنفس المجموعة, الرجاء التأكد والمحاولة مرة اخرى !");
            }else{
               
                int x = updateUserGroupId(groupsUser);
                
                if(x == 0){
                    MBCommon.getErrorMessage("", "لم يتم تحديث معومات الموظف, الرجاء التأكد والمحاولة مرة اخرى أو الإتصال مع مدير النظام !");
                }else{
                    MBCommon.getInfoMessage("", "تم إضافة الموظف للمجموعة بنجاح.");
                }   
            }
            return "";
        }else{
            
            int x = saveNewGroupMemberUser(groupId, groupsUser);
            
            if (x == 0) {
                MBCommon.getErrorMessage("", "لم يتم إضافة الموظف للمجموعة, الرجاء التأكد والمحاولة مرة اخرى أو الإتصال مع مدير النظام !");
            } else {
                MBCommon.getInfoMessage("", "تم إضافة الموظف للمجموعة بنجاح.");
            }
        }
        return "";
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkUserInGroupToDelete(){
        System.out.println("com.rasas.mbeans.MBGroupMembers.checkUserInGroupToDelete()");
           
        if(groupId == null){
            MBCommon.getErrorMessage("", "يجب إختيار المجموعة !");
            return "";
        }
        
        if(groupUser == null){
            MBCommon.getErrorMessage("", "يجب إختيار موظف لإزالة من المجموعات !");
            return "";
        }
        
        groupMembersList = new ArrayList<>();
        groupMembersList = getGroubMemberByUserId(groupUser);
        
        if(groupMembersList.size() > 0){
            
            int x = deleteGroupUserByUserId(groupUser);
            
            if(x == 0){
                MBCommon.getErrorMessage("", "لم يتم إزالة المستخدم من المجموعة, الرجاء التأكد والمحاولة مرة اخرى !");
            }else{
                MBCommon.getInfoMessage("", "تم إزالة المستخدم من المجموعة بنجاح .");
            }
            return "";
        }else{
            MBCommon.getErrorMessage("", "المستخدم لا ينتمي لأي مجموعة, الرجاء التأكد والمحاولة مرة اخرى !");
        }
        return "";
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String getGroupIdByUserId(String userId){
        System.out.println("com.rasas.mbeans.MBGroupMembers.getGroupIdByUserId()");
        
        emf.getCache().evictAll();
        groupIdList = new ArrayList<>();
        
        TypedQuery<GroupMembers> query = em.createQuery("SELECT g FROM GroupMembers g WHERE g.userId = ?1", GroupMembers.class)
                .setParameter(1, userId);
        
        groupIdList = query.getResultList();
        
        if(groupIdList.size() > 0){
           return groupIdList.get(0).getGroupId();
        }else{
            return "";
        }
    }

////////////////////////////////////////////////////////////////////////////////
    public List<GroupMembers> getGroubMemberByUserId(String groupsUser){
        System.out.println("com.rasas.mbeans.MBGroupMembers.getGroubMemberByUserId()");
        
        emf.getCache().evictAll();
        
        TypedQuery<GroupMembers> query = em.createQuery("SELECT g FROM GroupMembers g WHERE g.userId = ?1", GroupMembers.class)
                .setParameter(1, groupsUser);
        
        List<GroupMembers> member = query.getResultList();
        
        return member;
    }

////////////////////////////////////////////////////////////////////////////////
    public List<Users> getAllMembers(){
        System.out.println("com.rasas.mbeans.MBGroupMembers.getAllMembers()");
        
        emf.getCache().evictAll();
 
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userType = 'U' AND u.privilege <> 3 ORDER BY u.userId ASC", Users.class);
        
        List<Users> usersList = query.getResultList();
        
        return usersList;
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<GroupMembers> getAllGroupMembers(){
        System.out.println("com.rasas.mbeans.MBGroupMembers.getAllGroupMembers()");
        
        emf.getCache().evictAll();
        groupMembersList = new ArrayList<>();
        
        TypedQuery<GroupMembers> query = em.createQuery("SELECT g From GroupMembers g WHERE g.groupId = ?1 ORDER BY g.userId ASC", GroupMembers.class)
                .setParameter(1, groupId);
        
        groupMembersList = query.getResultList();
        
        return groupMembersList;
    }

////////////////////////////////////////////////////////////////////////////////
    public int saveNewGroupMemberUser(String groupId, String groupsId){
        System.out.println("com.rasas.mbeans.MBGroupMembers.saveNewGroupMemberUser()");
        
        emf.getCache().evictAll();
        
        int rows = em.createNativeQuery("INSERT INTO group_members VALUES (?1, ?2, ?3)")
                .setParameter(1, groupId)
                .setParameter(2, groupsId)
                .setParameter(3, new java.util.Date()).executeUpdate();
        
        return rows;
    }

////////////////////////////////////////////////////////////////////////////////
    public int updateUserGroupId(String groupsUser){
        System.out.println("com.rasas.mbeans.MBGroupMembers.updateUserGroupId()");
        
        emf.getCache().evictAll();
        
        int rows = em.createQuery("UPDATE GroupMembers g SET g.groupId = ?1, g.entryDate = ?2 WHERE g.userId = ?3")
                        .setParameter(1, groupId)
                        .setParameter(2, new java.util.Date())
                        .setParameter(3, groupsUser).executeUpdate();
        
        return rows; 
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int deleteGroupUserByUserId(String groupUser){
        System.out.println("com.rasas.mbeans.MBGroupMembers.deleteGroupUserByUserId()");
        
        emf.getCache().evictAll();
        
        Query query = em.createQuery("DELETE FROM GroupMembers g where g.userId = ?1")
                .setParameter(1, groupUser);
        
        int rows = query.executeUpdate();
        
        return rows;    
    }
    
//////////////////////////////// Getters and Setters ///////////////////////////  
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
    }

    public String getGroupsUser() {
        return groupsUser;
    }

    public void setGroupsUser(String groupsUser) {    
        this.groupsUser = groupsUser;
    } 
}
