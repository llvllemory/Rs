package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped

public class MBLogin {
    
    private String userId;
    private String password;
    private List<Users> usersList = new ArrayList<>();
    private MBUsers mBUsers = new MBUsers();
    
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
            usersList = mBUsers.getUserByUserId(userId);
            
            if (usersList.size() > 0) {
                
                if(usersList.get(0).getPassword().equals(password)){
                    
                    int x = mBUsers.updateUserLastLogin(usersList.get(0));
                    
                    if(x > 0){
                        MBCommon.getInfoMessage("", "أهلا وسهلا بك " + usersList.get(0).getUserName());
                        return "main_page";
                    }else{
                        MBCommon.getFatalMessage("", "هنالك خطأ في تحديث معلومات المستخدم!");
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
    }
////////////////////////////////////////////////////////////////////////////////    
public void logout(){
    
}    




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
