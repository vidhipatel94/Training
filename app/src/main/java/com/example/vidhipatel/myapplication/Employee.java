package com.example.vidhipatel.myapplication;

import java.io.Serializable;

/**
 * Created by vidhi.patel on 6/18/2015.
 */
public class Employee implements Serializable{
    String mName;
    String mDesignation;
    String mEmail;

    public Employee(String name, String designation, String email) {
        this.mName = name;
        this.mDesignation=designation;
        this.mEmail=email;
    }
    public void setName(String n){
        this.mName=n;
    }

    public void setDesignation(String n){
        this.mDesignation=n;
    }

    public void setmEmail(String n){
        this.mEmail=n;
    }

    public String getName(){
        return this.mName;
    }

    public String getDesignation(){
        return this.mDesignation;
    }

    public String getEmail(){
        return this.mEmail;
    }
}
