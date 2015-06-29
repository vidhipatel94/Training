package com.example.vidhipatel.myapplication;

import java.io.Serializable;

/**
 * Created by vidhi.patel on 6/19/2015.
 */
public class User implements Serializable{
    int id;
    String name;
    String username;
    String email;

    void setId(int id){
        this.id=id;
    }

    void setName(String name){
        this.name=name;
    }

    void setUsername(String username){
        this.username=username;
    }

    void setEmail(String email){
        this.email=email;
    }


    int getId(){
        return this.id;
    }

    String getName(){
        return this.name;
    }

    String getUsername(){
        return this.username;
    }

    String getEmail(){
        return this.email;
    }

}
