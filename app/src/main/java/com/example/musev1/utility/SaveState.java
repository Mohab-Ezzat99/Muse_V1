package com.example.musev1.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

public class SaveState {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String DARK_MODE="dark_mode";
    public static final String NOTIFICATION = "notification";
    public static final String NEW_ALERT = "new alert";
    public static final String ON_BOARDING = "onBoarding";
    public static final String TOKEN = "token";

    @SuppressLint("CommitPrefEdits")
    public SaveState(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharedPreferences.edit();
    }

    //____________________________________________________________________________________________//
    //api token

    public static void setToken(String token) {
        editor.putString(TOKEN,token);
        editor.apply();
    }

    public static String getToken() {
        return sharedPreferences.getString(TOKEN,null);
    }

    //____________________________________________________________________________________________//
    //on boarding

    public static boolean getShownOnBoarding() {
        return sharedPreferences.getBoolean(ON_BOARDING, false);
    }

    public static void setShownOnBoarding(boolean isShown) {
        editor.putBoolean(ON_BOARDING, isShown);
        editor.apply();
    }

    //____________________________________________________________________________________________//
    //dark mode

    public static void setDarkModeState(boolean b){
        editor.putBoolean(DARK_MODE,b);
        editor.apply();
    }

    public static boolean getDarkModeState(){
        return sharedPreferences.getBoolean(DARK_MODE,false);
    }

    //____________________________________________________________________________________________//
    //notification in more tab

    public static void setNotificationState(boolean b) {
        editor.putBoolean(NOTIFICATION, b);
        editor.apply();
    }

    public static boolean getNotificationState() {
        return sharedPreferences.getBoolean(NOTIFICATION, true);
    }

    //____________________________________________________________________________________________//
    //alert tab

    public static void setLastAlert(int counter) {
        editor.putInt(NEW_ALERT, counter);
        editor.apply();
    }

    public static int getLastAlerts() {
        return sharedPreferences.getInt(NEW_ALERT, 0);
    }

    //____________________________________________________________________________________________//
    //internet connection

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}
