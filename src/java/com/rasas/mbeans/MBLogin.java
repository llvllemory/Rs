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
    private List<Users> usersList = new ArrayList<>();
    private MBUsers mBUsers;
    private MBGroupMembers mBGroupMembers = new MBGroupMembers();
    
    private final FacesContext context = FacesContext.getCurrentInstance();
    
    public MBLogin(){
        
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public String login(){
        System.out.println("com.rasas.mbeans.MBLogin.login()");
        
        if(userId.equals("")){
            MBCommon.getWarnMessage("", "يجب ادخال اسم المستخدم ");
            return "";
        }
        
        if(password.equals("")){
            MBCommon.getWarnMessage("", "يجب ادخال كلمة السر");
            return "";
        }
        
        
        try {
            mBUsers = new MBUsers();
            System.out.println("--------------------------------- 1");
            usersList = mBUsers.getUserByUserId(userId);
            System.out.println("--------------------------------- 2" + userId);
            if (usersList.size() > 0) {

                if (usersList.get(0).getPassword().equals(password)) {
                    System.out.println("--------------------------------- 3");
                    if (usersList.get(0).getPrivilege() == 1 || usersList.get(0).getPrivilege() == 2) {
                        System.out.println("--------------------------------- 4");
                        String userGroupId = mBGroupMembers.getGroupIdByUserId(userId);

                        if (userGroupId.equals("")) {
                            System.out.println("--------------------------------- 5");
                            MBCommon.getFatalMessage("", "المستخدم لا ينتمي لأي مجموعة, الرجاء التأكد من معلومات المستخدم أو الإتصال مع مدير النظام !");
                            return "";
                        } else {

                            int x = mBUsers.updateUserLastLogin(usersList.get(0));

                            if (x > 0) {
                                System.out.println("--------------------------------- 6");
                                context.getExternalContext().getSessionMap().put("loggedUser", usersList.get(0));

                                MBCommon.getInfoMessage("", "أهلا وسهلا بك " + usersList.get(0).getUserName());
                                return "rs_main_page";
                            } else {
                                MBCommon.getFatalMessage("", "هنالك خطأ في تحديث معلومات المستخدم  !");
                                return "";
                            }
                        }

                    } else if (usersList.get(0).getPrivilege() == 3) {
                        System.out.println("--------------------------------- 7");
                        MBCommon.getFatalMessage("", "المستخدم موقوف, الرجاء الإتصال مع مدير النظام !");
                        return "";
                    }

                } else {
                    System.out.println("--------------------------------- 8");
                    MBCommon.getErrorMessage("", "خطأ في اسم المستخدم أو كلمة السر !");
                    return "";
                }

            } else {
                System.out.println("--------------------------------- 9");
                MBCommon.getErrorMessage("", "خطأ في اسم المستخدم أو كلمة السر !");
                return "";
            }

        } catch (Exception e) {
            System.out.println("--------------------------------- 10");
            System.out.println("com.rasas.mbeans.MBLogin.login()-----> e.getMessage()" + e.getMessage());
            System.out.println("com.rasas.mbeans.MBLogin.login()-----> e.getMessage()" + e.getCause());
            return "";
        }
        return "";
    }
////////////////////////////////////////////////////////////////////////////////    
    public String logout() {
        System.out.println("com.rasas.mbeans.MBLogin.logout()");

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "rs_login_page";

    } 
////////////////////////////////////////////////////////////////////////////////
    public Users getLoggedUser(){
        System.out.println("com.rasas.mbeans.MBLogin.getLoggedUser()");
                          
        Users loggedUser = (Users) context.getExternalContext().getSessionMap().get("loggedUser");
        
        return loggedUser;
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
