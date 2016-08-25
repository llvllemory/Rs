package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBUsers implements Serializable{
    
    private String userId;
    private String userName;
    private String password;
    private String userType;
    private int privilege = 2;
    private String userCenter;
    private String userSubCenter;
            
            
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBUsers(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<Users> getUserByUserId(String userId){
        System.out.println("com.rasas.mbeans.MBUsers.getUserByUserId()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userId = ?1", Users.class)
                .setParameter(1, userId);
        
        List usersList = query.getResultList();
        
        return usersList;
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public int updateUserLastLogin(Users user){
        System.out.println("com.rasas.mbeans.MBUsers.updateUserLastLogin()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        return em.createQuery("UPDATE Users SET lastLogin = ?1 WHERE userId = ?2")
                .setParameter(1, new java.util.Date())
                .setParameter(2, user.getUserId()).executeUpdate();
         
    }
    
//////////////////// Getters and Setters ///////////////////////////////////////

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public String getUserCenter() {
        return userCenter;
    }

    public void setUserCenter(String userCenter) {
        this.userCenter = userCenter;
    }

    public String getUserSubCenter() {
        return userSubCenter;
    }

    public void setUserSubCenter(String userSubCenter) {
        this.userSubCenter = userSubCenter;
    }
    
}
