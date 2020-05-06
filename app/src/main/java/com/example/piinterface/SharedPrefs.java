package com.example.piinterface;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private final static String SHARED_PREF = "Shared Prefs";
    private final static String IP_ADRESSE_KEY = "ip_adresse_key";
    private final static String PORT_NUMBER_KEY = "port_number_key";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sEditor;

    public SharedPrefs(Context context) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        sEditor = sharedPreferences.edit();
    }

    public String getIP(){
        return sharedPreferences.getString(IP_ADRESSE_KEY,"");
    }

    public int getPort (){
        return sharedPreferences.getInt(PORT_NUMBER_KEY,0);
    }

    public void setIP(String ip){
        sEditor.putString(IP_ADRESSE_KEY,String.valueOf(ip));
        sEditor.apply();
    }
    public void setPort(int port){
        sEditor.putInt(PORT_NUMBER_KEY,port);
        sEditor.apply();
    }
}
