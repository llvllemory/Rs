package com.rasas.mbeans;

import com.rasas.entities.SubCenters;
import com.rasas.entities.Users;
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

public class MBUsers implements Serializable{
    
    private String userId;
    private String userName;
    private String password;
    private String userType;
    private int privilege;
    private String userCenter;
    private String userSubCenter;
    
    private String oldPassword;
    private String newPassword;
    private String retypeNewPassword;
    
    private String userTxtPasswordEdit = "false";
    
    private List<Users> usersList;
    
    private MBLogin mBLogin;
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBUsers(){
     
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }

////////////////////////////////////////////////////////////////////////////////
    public String checkNewUserToSave(){
        System.out.println("com.rasas.mbeans.MBUsers.checkNewUserToSave()");
        
        if(userType == null){
            MBCommon.getWarnMessage("", "يحب إختيار صفة المستخدم !");
            return "";
            
        }else if(userType.equals("U")){
            
            if (userId.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال رمز المستخدم !");
                return "";
            }

            if (userName.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال اسم المستخدم !");
                return "";
            }

            if (userCenter == null) {
                MBCommon.getWarnMessage("", "يجب إختيار مركز عمل المستخدم !");
                return "";
            }

            if (userSubCenter == null) {
                MBCommon.getWarnMessage("", "يجب إختيار القسم الذي يتع له المستخدم !");
                return "";
            }

            if (privilege == 0) {
                MBCommon.getWarnMessage("", "يجب إختيار صلاحيات المستخدم !");
                return "";
            }

            if (password.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال كلمة السر !");
                return "";
            }
            
        }else if(userType.equals("G")){
            
            if (userId.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال رمز المستخدم !");
                return "";
            }

            if (userName.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال اسم المستخدم !");
                return "";
            }

//            if (userCenter == null) {
//                MBCommon.getWarnMessage("", "يجب إختيار مركز عمل المستخدم !");
//                return "";
//            }
        }
        
        usersList = new ArrayList<>();
        usersList = getUserByUserId(userId);
        
        if(usersList.size() > 0){
            MBCommon.getErrorMessage("", "المستخدم موجود مسبقا في ملف المستخدمين, الرجاء التأكد والمحاولة مرة اخرى!");
            return "";
        }else if(usersList.size() == 0){
            
            int x = saveNewUser();
            
            if(x == 0){
                MBCommon.getErrorMessage("", "لم يتم تخزين معلومات المستخدم لإسباب فنية, يرجى التأكد من المعلومات أو الإتصال مع مدير النظام !");
            }else{
                MBCommon.getInfoMessage("", "تم إنشاء المستخدم الجديد بنجاح .");
            }
            return "";
        }
        return "";
    }
////////////////////////////////////////////////////////////////////////////////
    public String checkUserToUpdate(){
        System.out.println("com.rasas.mbeans.MBUsers.checkUserToUpdate()");
        
        if(userType == null){
            MBCommon.getWarnMessage("", "يحب إختيار صفة المستخدم !");
            return "";
            
        }else if(userType.equals("U")){
            
            if (userId.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال رمز المستخدم !");
                return "";
            }

            if (userName.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال اسم المستخدم !");
                return "";
            }

            if (userCenter == null) {
                MBCommon.getWarnMessage("", "يجب إختيار مركز عمل المستخدم !");
                return "";
            }

            if (userSubCenter == null) {
                MBCommon.getWarnMessage("", "يجب إختيار القسم الذي يتع له المستخدم !");
                return "";
            }

            if (privilege == 0) {
                MBCommon.getWarnMessage("", "يجب إختيار صلاحيات المستخدم !");
                return "";
            }

            if (password.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال كلمة السر !");
                return "";
            }
            
        }else if(userType.equals("G")){
            
            if (userId.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال رمز المستخدم !");
                return "";
            }

            if (userName.equals("")) {
                MBCommon.getWarnMessage("", "يجب إدخال اسم المستخدم !");
                return "";
            }

//            if (userCenter == null) {
//                MBCommon.getWarnMessage("", "يجب إختيار مركز عمل المستخدم !");
//                return "";
//            }
        }
        
        usersList = new ArrayList<>();
        usersList = getUserByUserId(userId);
        
        if(usersList.size() > 0){
            
            int x = updateUserInfo();
            
            if(x == 1){
                MBCommon.getInfoMessage("", "تم تحديث معلومات المستخدم بنجاح .");
            }else{
                MBCommon.getErrorMessage("", "لم يتم تحديث معلومات المستخدم, الرجاء التأكد والمحاولة مرة اخرى أو الإتصال مع مدير النظام !");
            }
            return "";
        }else{
            MBCommon.getErrorMessage("", "لا يمكن تعديل معلومات مستخدم غير موجود في ملف المستخدمين, الرجاء التأكد والمحاولة مرة اخرى !");
        }        
        return "";
    }
////////////////////////////////////////////////////////////////////////////////
    public String checkUserToUpdatePassword(){
        System.out.println("com.rasas.mbeans.MBUsers.checkUserToUpdatePassword()");
        
        if(oldPassword.equals("")){
            MBCommon.getErrorMessage("","يجب إدخال كلمة السر القديمة!");
            return "";
        }
        
        if(newPassword.equals("")){
            MBCommon.getErrorMessage("", "يجب إدخال كلمة السر الجديدة !");
            return "";
        }
        
        if(retypeNewPassword.equals("")){
            MBCommon.getErrorMessage("", "يحب إعادة إدخال كلمة السر الجديدة !");
            return "";
        }
        
        if(!newPassword.equals(retypeNewPassword)){
            MBCommon.getErrorMessage("", "كلمات السر الجديدة غير متطابقة, يرجى التأكد والمحاولة مرة اخرى !");
            return "";
        }
        
        mBLogin = new MBLogin();
        usersList = new ArrayList<>();
        usersList = getUserByUserId(mBLogin.getLoggedUser().getUserId());
        
        if(usersList.get(0).getPassword().equals(oldPassword)){
            
            int x = updateUserPassword(usersList.get(0).getUserId(), newPassword);
            
            if(x == 1){
                MBCommon.getInfoMessage("", "تم تعديل كلمة السر بنجاح !");
                mBLogin.logout();
                return "/rs_login_page";
            }else{
                MBCommon.getFatalMessage("", "لم يتم تعديل كلمة السر, الرجاء الإتصال مع مدير النظام !");
            }
            return "";
            
        }else{
            MBCommon.getErrorMessage("", "كلمة السر القديمة خاطئة, لا يمكنك تعديل كلمة السر !");
        }
        return "";
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public List<Users> getUserByUserId(String userId){
        System.out.println("com.rasas.mbeans.MBUsers.getUserByUserId()");
        
        emf.getCache().evictAll();
        usersList = new ArrayList<>();
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userId = ?1", Users.class)
                .setParameter(1, userId);
        
        usersList = query.getResultList();
        
        return usersList;
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public void loadUserInfo(){
        System.out.println("com.rasas.mbeans.MBUsers.getUserByUserId()");
        
        emf.getCache().evictAll();
        usersList = new ArrayList<>();
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userId = ?1", Users.class)
                .setParameter(1, userId);
        
        usersList = query.getResultList();
        
        if(usersList.size() > 0){
            
            if (usersList.get(0).getUserType().equals("U")) {
                
                userId = usersList.get(0).getUserId();
                userName = usersList.get(0).getUserName();
                password = usersList.get(0).getPassword();
                userType = usersList.get(0).getUserType();
                privilege = usersList.get(0).getPrivilege();
                userCenter = usersList.get(0).getUserCenter();
                userSubCenter = usersList.get(0).getUserSubCenter();
                
            } else if (usersList.get(0).getUserType().equals("G")) {
                
                userId = usersList.get(0).getUserId();
                userName = usersList.get(0).getUserName();
                password = usersList.get(0).getPassword();
                userType = usersList.get(0).getUserType();
                userCenter = usersList.get(0).getUserCenter();
                
            }
        }
        
        mBLogin = new MBLogin();
        if(userId.equals(mBLogin.getLoggedUser().getUserId())){
            userTxtPasswordEdit = "false";
        }else{
            userTxtPasswordEdit = "true";
        }
    }
        
////////////////////////////////////////////////////////////////////////////////    
    public int updateUserLastLogin(Users user){
        System.out.println("com.rasas.mbeans.MBUsers.updateUserLastLogin()");
        
        emf.getCache().evictAll();
        return em.createQuery("UPDATE Users u SET u.lastLogin = ?1 WHERE u.userId = ?2")
                .setParameter(1, new java.util.Date())
                .setParameter(2, user.getUserId()).executeUpdate();
         
    }

    
////////////////////////////////////////////////////////////////////////////////    
    public int updateUserPassword(String userId, String password){
        System.out.println("com.rasas.mbeans.MBUsers.updateUserPassword()");
        
        emf.getCache().evictAll();
        return em.createQuery("UPDATE Users u SET u.password = ?1 WHERE u.userId = ?2")
                .setParameter(1, password)
                .setParameter(2, userId).executeUpdate();
         
    }
    
////////////////////////////////////////////////////////////////////////////////
    public List<Users> getAllUsersByCenter(){
        System.out.println("com.rasas.mbeans.MBUsers.getAllUsersByCenter()");
        
        emf.getCache().evictAll();
        
        mBLogin = new MBLogin();
        usersList = new ArrayList<>();
        
        if(mBLogin.getLoggedUser().getPrivilege() == 1){
            
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u ORDER BY u.userId ASC", Users.class);
            usersList = query.getResultList();
            
        }else{
            
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.privilege != 3 AND u.userCenter = ?1 ORDER BY u.userId ASC", Users.class)
                .setParameter(1, mBLogin.getLoggedUser().getUserCenter());
        
            usersList = query.getResultList();
            
        }
        
        if(usersList.size() > 0){
            return usersList;
        }else{
            return null;
        }
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int saveNewUser(){
        System.out.println("com.rasas.mbeans.MBUsers.saveNewUser()");
        
        emf.getCache().evictAll();
        
        Users user = new Users();
        
        if (userType.equals("U")) {

            user.setUserId(userId);
            user.setUserName(userName);
            user.setPassword(password);
            user.setUserType(userType);
            user.setPrivilege(privilege);
            user.setUserCenter(userCenter);
            user.setUserSubCenter(userSubCenter);
            user.setEntryDate(new java.util.Date());
            
        } else if (userType.equals("G")) {
            
            user.setUserId(userId);
            user.setUserName(userName);
            user.setUserType(userType);
            user.setUserCenter(userCenter);
            user.setEntryDate(new java.util.Date());
        }

        em.persist(user);
        em.getTransaction().commit();
        
        user = em.find(Users.class, user.getUserId());
        
        if(user.getUserId().equals("")){
            return 0;
        }else{
            return 1;
        }
        
    }
    
////////////////////////////////////////////////////////////////////////////////
    public int updateUserInfo(){
        System.out.println("com.rasas.mbeans.MBUsers.updateUserInfo()");
        
        emf.getCache().evictAll();
        
        Users user = new Users();
        
        int u = em.createQuery("UPDATE Users u SET u.userName = ?1, u.userCenter = ?2, u.userSubCenter = ?3, u.userType = ?4, u.privilege = ?5, u.password = ?6 WHERE u.userId = ?7")
                .setParameter(1, userName)
                .setParameter(2, userCenter)
                .setParameter(3, userSubCenter)
                .setParameter(4, userType)
                .setParameter(5, privilege)
                .setParameter(6, password)
                .setParameter(7, userId).executeUpdate();
        
        emf.getCache().evictAll();
        return u;
    }

////////////////////////////////////////////////////////////////////////////////
    public List<Users> getAllGroups(){
        System.out.println("com.rasas.mbeans.MBUsers.getAllGroups()");
        
        emf.getCache().evictAll();
        usersList = new ArrayList<>();
        
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.userType = 'G'", Users.class);
        
        usersList = query.getResultList();
        
        if(usersList.size() > 0){
            return usersList;
        }else{
            return null;
        }
    }
////////////////////////////////////////////////////////////////////////////////
    public List<SubCenters> getSubCentersByCenterNo(){
        System.out.println("com.rasas.mbeans.MBUsers.getSubCentersByCenterNo()");
        
        TypedQuery<SubCenters> query = em.createQuery("SELECT s FROM SubCenters s WHERE s.centerNo = ?1 AND s.subCenterName IS NOT NULL ORDER BY s.subCenterNo DESC", SubCenters.class)
                .setParameter(1, userCenter);
        
        List<SubCenters> subCentersList = query.getResultList();
        
        if(subCentersList.size() > 0){
            return subCentersList;
        }else{
            return null;
        }
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

    public String getUserTxtPasswordEdit() {
        return userTxtPasswordEdit;
    }

    public void setUserTxtPasswordEdit(String userTxtPasswordPrivilege) {
        this.userTxtPasswordEdit = userTxtPasswordPrivilege;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }
    
    
    
}
