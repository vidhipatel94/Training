package com.example.vidhipatel.myapplication;

import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by vidhi.patel on 6/19/2015.
 */
@SuppressWarnings("ALL")
public class ApiConnector {

    public JSONArray getAllEmployee(String url) throws IOException, JSONException {

        HttpEntity httpEntity = null;
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        httpEntity = response.getEntity();

        JSONArray jsonArray = null;
        if(httpEntity !=null)
        {
            String entityRespose= EntityUtils.toString(httpEntity);
            //Log.i("LENGTH",entityRespose);
            jsonArray= new JSONArray(entityRespose);
        }

     /*   HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://mkyong.com");
        HttpResponse response = client.execute(request);
       */


        return jsonArray;
    }
}
