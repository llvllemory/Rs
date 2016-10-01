package com.rasas.advancedObjects;

import java.io.Serializable;

public class PrivilegesAdvancedObject implements Serializable{
    
    private boolean view;
    private boolean add;
    private boolean delete;
    private boolean update;
    private boolean null1;
    private boolean null2;
    
    
    
////////////////// Getters and Setters /////////////////////////////////////////

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isNull1() {
        return null1;
    }

    public void setNull1(boolean null1) {
        this.null1 = null1;
    }

    public boolean isNull2() {
        return null2;
    }

    public void setNull2(boolean null2) {
        this.null2 = null2;
    }

    

    
}
