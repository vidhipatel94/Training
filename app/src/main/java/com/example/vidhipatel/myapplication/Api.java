package com.example.vidhipatel.myapplication;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by vidhi.patel on 6/19/2015.
 */
public interface Api {
    @GET("/users")
    void getUser(Callback<List<User>> callback);
}
