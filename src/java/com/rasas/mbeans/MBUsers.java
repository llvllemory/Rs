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
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBUsers(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
////////////////////////////////////////////////////////////////////////////////
    
    public List<Users> getUserByUserId(String userId){
        System.out.println("com.rasas.mbeans.MBUsers.getUserByUserId()---------->");
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userId = ?1", Users.class)
                .setParameter(1, userId);
        List usersList = query.getResultList();
        
        return usersList;
    }
    
    public int updateUserLastLogin(Users user){
        System.out.println("com.rasas.mbeans.MBUsers.updateUserLastLogin()---------->");
        
        return em.createQuery("UPDATE Users SET lastLogin = ?1 WHERE userId = ?2")
                .setParameter(1, new java.util.Date())
                .setParameter(2, user.getUserId()).executeUpdate();
         
    }
}
