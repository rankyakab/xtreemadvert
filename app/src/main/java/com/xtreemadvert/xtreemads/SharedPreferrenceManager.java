package com.xtreemadvert.xtreemads;


import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferrenceManager {
    private static SharedPreferrenceManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREFERENCE_NAME="mysharedpref12";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USEREMAIL = "useremail";
    private static final String KEY_USERID="userid";


    private SharedPreferrenceManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPreferrenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferrenceManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USERID, id);
        editor.putString(KEY_USEREMAIL,email);
        editor.putString(KEY_USERNAME,username);
        editor.apply();
        return true;

    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME,null)!=null){
                return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USEREMAIL, null);
    }
    public Integer getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USERID,0);
    }

}