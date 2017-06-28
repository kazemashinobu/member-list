package com.kazema.memberlist.utility;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by kazemashinobu on 2017/6/28.
 */

public class GlobalVariable extends Application {
    public static final String API_DOMAIN_NAME = "http://52.197.192.141:3443";
    public static final String SETTINGS = "com.kazema.memberlist.SETTINGS";
    public static final String DATA_USERNAME = "com.kazema.memberlist.USERNAME";
    public static final String DATA_PWD = "com.kazema.memberlist.PWD";
    public static final String DATA_TOKEN = "com.kazema.memberlist.TOKEN";
    public static final String DATA_EXP_TIME = "com.kazema.memberlist.EXP_TIME";
    private String username;
    private String password;
    private String token;
    private long expTime;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences settings = getSharedPreferences(GlobalVariable.SETTINGS, MODE_PRIVATE);
        username = settings.getString(DATA_USERNAME, "");
        password = settings.getString(DATA_PWD, "");
        token = settings.getString(DATA_TOKEN, "");
        expTime = settings.getLong(DATA_EXP_TIME, 0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        SharedPreferences settings = getSharedPreferences(GlobalVariable.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(DATA_USERNAME, username);
        editor.apply();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        SharedPreferences settings = getSharedPreferences(GlobalVariable.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(DATA_PWD, password);
        editor.apply();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences settings = getSharedPreferences(GlobalVariable.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(DATA_TOKEN, token);
        editor.apply();
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
        SharedPreferences settings = getSharedPreferences(GlobalVariable.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(DATA_EXP_TIME, expTime);
        editor.apply();
    }
}
