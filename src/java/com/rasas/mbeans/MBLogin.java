package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped

public class MBLogin implements Serializable{
    
    private String userId;
    private String password;
    private List<Users> usersList;
    private MBUsers mBUsers = new MBUsers();
    private final FacesContext context = FacesContext.getCurrentInstance();
    public MBLogin(){
        
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public String login(){
        System.out.println("com.rasas.mbeans.MBLogin.login()---------->");
        
        if(userId.equals("")){
            MBCommon.getWarnMessage("", "يجب ادخال اسم المستخدم");
            return "";
        }
        
        if(password.equals("")){
            MBCommon.getWarnMessage("", "يجب ادخال كلمة السر");
            return "";
        }
        
        try {
            usersList = new ArrayList<>();
            usersList = mBUsers.getUserByUserId(userId);
            
            if (usersList.size() > 0) {
                
                if(usersList.get(0).getPassword().equals(password)){
                    
                    if(usersList.get(0).getPrivilege() == 1 || usersList.get(0).getPrivilege() == 2){
                        
                        int x = mBUsers.updateUserLastLogin(usersList.get(0));

                        if (x > 0) {
                            
                            context.getExternalContext().getSessionMap().put("loggedUser", usersList.get(0));
                            
                            MBCommon.getInfoMessage("", "أهلا وسهلا بك " + usersList.get(0).getUserName());
                            return "main_page";
                        } else {
                            MBCommon.getFatalMessage("", "هنالك خطأ في تحديث معلومات المستخدم!");
                            return "";
                        }
                        
                    }else if(usersList.get(0).getPrivilege() == 3){
                        MBCommon.getFatalMessage("", "المستخدم موقوف, الرجاء الإتصال مع مدير النظام!");
                        return "";
                    }
                    
                }else{
                    MBCommon.getErrorMessage("", "خطأ في اسم المستخدم او كلمة السر!");
                    return "";
                }
                
            } else {
                MBCommon.getErrorMessage("", "خطأ في اسم المستخدم او كلمة السر!");
                return "";
            }
            
        } catch (Exception e) {
            System.out.println("com.rasas.mbeans.MBLogin.login()-----> e.getMessage()" + e.getMessage());
            return "";
        }
        
        return "";
    }
////////////////////////////////////////////////////////////////////////////////    
    public String logout() {
        System.out.println("com.rasas.mbeans.MBLogin.logout()---------->");

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login_page";

    } 
////////////////////////////////////////////////////////////////////////////////
    public Users getLoggedUser(){
        System.out.println("com.rasas.mbeans.MBLogin.getLoggedUser()---------->");
                          
        Users loggedUser = (Users) context.getExternalContext().getSessionMap().get("loggedUser");
        
        return loggedUser;
    }
////////////////////////////////////////////////////////////////////////////////


//////////////////// Geteers and Setters ///////////////////////////////////////

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
