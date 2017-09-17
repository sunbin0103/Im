package com.raon.im.connection;

import android.content.Intent;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiSoo on 2016-02-05.
 */
public class ConnectThread extends Thread {
    private String url;
    private String service;
    private Intent intent;

    public ConnectThread (String service, Intent intent) {
        this.url = "http://172.30.1.49:8080/Raon_Im/" + service;
        this.service = service;
        this.intent = intent;
    }

    public void run() {
        try {
            if(service == "signup") {
                requestSignup();
            } else if(service == "imresponse") {
                requestImresponse();
            } else if(service == "expirationreset") {
                requestExpirationreset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestSignup() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userID", intent.getStringExtra("userID"));
            jsonObject.put("userPW", intent.getStringExtra("userPW"));
            jsonObject.put("gcmID", intent.getStringExtra("gcmID"));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("information", jsonObject.toString()));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpClient.execute(httpPost);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestImresponse() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            if (intent.getBooleanExtra("res", false)) {
                nameValuePairs.add(new BasicNameValuePair("res", "accept"));

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("userID", intent.getStringExtra("userID"));
                jsonObject.put("companyDomain", intent.getStringExtra("companyDomain"));

                nameValuePairs.add(new BasicNameValuePair("accept", jsonObject.toString()));
            } else {
                nameValuePairs.add(new BasicNameValuePair("res", "deny"));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpClient.execute(httpPost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestExpirationreset() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userID", intent.getStringExtra("userID"));
            jsonObject.put("company", intent.getStringExtra("company"));
            jsonObject.put("year", intent.getStringExtra("year"));
            jsonObject.put("month", intent.getStringExtra("month"));
            jsonObject.put("date", intent.getStringExtra("date"));

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("expirationReset", jsonObject.toString()));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpClient.execute(httpPost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}