package com.example.vidhipatel.myapplication;

import java.io.Serializable;

/**
 * Created by vidhi.patel on 6/19/2015.
 */
//package-protected
public class User implements Serializable{
    private int id;
    private String name;
    private String username;
    private String email;

    //package-protected
    User(int id, String name, String username, String email){
        this.id=id;
        this.name=name;
        this.username=username;
        this.email=email;
    }

    //accessors without mutators
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
