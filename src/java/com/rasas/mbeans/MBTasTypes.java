package com.rasas.mbeans;


import com.rasas.entities.TasTypes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ManagedBean
@RequestScoped

public class MBTasTypes implements Serializable{

    private int tasType;
    private String tasDesc;
    
    private List<TasTypes> tasTypesList;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBTasTypes(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<TasTypes> getTasTypesList(){
        System.out.println("com.rasas.mbeans.MBTasTypes.getTasTypesList()");
        
        tasTypesList = new ArrayList<>();
        
        TypedQuery<TasTypes> query = em.createQuery("SELECT t FROM TasTypes t", TasTypes.class);
        
        tasTypesList = query.getResultList();
        
        return tasTypesList;
    }

//////////////// Getters and Setters ///////////////////////////////////////////

    public int getTasType() {
        return tasType;
    }

    public void setTasType(int tasType) {
        this.tasType = tasType;
    }

    public String getTasDesc() {
        return tasDesc;
    }

    public void setTasDesc(String tasDesc) {
        this.tasDesc = tasDesc;
    }
    
}
