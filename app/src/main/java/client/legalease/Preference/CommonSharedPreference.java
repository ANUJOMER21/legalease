package client.legalease.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import client.legalease.Model.VERIFYOTP.User;


public class CommonSharedPreference {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;



    //editor

    public CommonSharedPreference(Context ctx){
        this.ctx= ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor=prefs.edit();
    }

public void setlocation(String lat,String lon){
        editor.putString("lat",lat);
        editor.putString("lon" ,lon);
        editor.commit();
}
public String getlat(){
       return prefs.getString("lat","nul");
}


    public String  getlon(){
      return   prefs.getString("lon","nul");
    }

    //  used to check wheteher you arew logged in or not
    public  void  setLoggedin(boolean loggedin){
        editor.putBoolean("loggedInmode",loggedin);
        editor.commit();
    }
    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

    public void tokenName(String token) {
        editor.putString("Token", token);

    }
public void setProfilephoto(Context context,String photo){
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    editor = sharedPref.edit();
    editor.putString("profilephoto",photo);
    editor.commit();
}
public String getProfilephoto(Context context){
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("profilephoto","");
}
 public  String getToken(){
        return prefs.getString("Token","null");
 }
    // this is used to get the data from session where u need those data
    public User getLoginSharedPref(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPref.getString("LOGIN_PREF", null);
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();

        User beans = null;
        try {
            beans = gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
        return beans;
    }



  //  this is used to store all your  data to session at the time of login

    public void setLoginSharedPref(Context context, User beans) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(beans);
        editor.putString("LOGIN_PREF", json);
        editor.commit();

    }
    public  void logiut(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();


    }
}