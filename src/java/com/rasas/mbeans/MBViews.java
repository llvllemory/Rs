package com.rasas.mbeans;

import com.rasas.entities.Views;
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

public class MBViews {
    
    private String viewId;
    private String viewName;
    
    private Views view;
    private List<Views> viewsList;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBViews(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////
    public String checkToSaveNewView(){
        System.out.println("com.rasas.mbeans.MBViews.checkToSaveNewView()----------> " + MBCommon.getCurrentDateTime());
        
        if(viewId.equals("")){
            MBCommon.getErrorMessage("", "يجب إدخال رمز الشاشة, يبدأ ب/ وينتهي ب .xhtml.");
            return "";
        }
        
        if(viewName.equals("")){
            MBCommon.getErrorMessage("", "يجب إدخال اسم الشاشة.");
            return "";
        }
        
        viewsList = new ArrayList<>();
        viewsList = getViewByViewId(viewId);
                
        if(viewsList.size() > 0){
            MBCommon.getFatalMessage("", "رمز الشاشة موجود مسبقا, الرجاء التأكد والمحاولة مرة اخرى !");
        }else{
            
            int x = saveNewView();
            
            if(x == 0){
                MBCommon.getFatalMessage("", "لم يتم تخزين معلومات الشاشة, الرجاء المحاولة مرة اخرى أو الأتصال مع مدير النظام !");
            }else{
                MBCommon.getInfoMessage("", "تم تخزين معلومات الشاشة ينجاح .");
            }
            return "";
        }
        return "";
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<Views> getViewByViewId(String viewId){
        System.out.println("com.rasas.mbeans.MBViews.getViewByViewId()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        
        TypedQuery<Views> query = em.createQuery("SELECT v FROM Views v WHERE v.viewId = ?1", Views.class)
                .setParameter(1, viewId);
        
        viewsList = new ArrayList<>();
        viewsList = query.getResultList();
        
        return viewsList;
    }

////////////////////////////////////////////////////////////////////////////////    
    public int saveNewView(){
        System.out.println("com.rasas.mbeans.MBViews.saveNewView()----------> " + MBCommon.getCurrentDateTime());
        
        view = new Views();
        view.setViewId(viewId);
        view.setViewName(viewName);
        
        emf.getCache().evictAll();
        
        em.persist(view);
        em.getTransaction().commit();
        
        view = em.find(Views.class, view.getViewId());
        
        if(view.getViewId().equals("")){
            return 0;
        }else{
            return 1;
        }
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public List<Views> getAllViews(){
        System.out.println("com.rasas.mbeans.MBViews.getAllViews()----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        
        TypedQuery<Views> query = em.createQuery("SELECT v FROM Views v", Views.class);
        
        viewsList = new ArrayList<>();
        viewsList = query.getResultList();
        
        if(viewsList.size() > 0 ){
            return viewsList;
        }else{
            return null;
        }
    }

////////////////////////////////////////////////////////////////////////////////
    public void loadViewInfoByViewId(){
        System.out.println("com.rasas.mbeans.MBViews.loadViewInfoByViewId() ----------> " + MBCommon.getCurrentDateTime());
        
        emf.getCache().evictAll();
        
        view = new Views();
        view = em.find(Views.class, viewId);
        
        viewId = view.getViewId();
        viewName = view.getViewName();
    }

////////////////////// Getters and Setters /////////////////////////////////////

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
    
}
