package com.example.muse.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;

public class SaveState {

    private static MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String USERS = "Users";
    public static final String USER_ID = "User ID";
    public static final String FULL_NAME="Full Name";
    public static final String DEVICE_ID="Device ID";
    public static final String EMAIL="Email";
    public static final String DARK_MODE="dark_mode";
    public static final String NOTIFICATION = "notification";
    public static final String NEW_ALERT = "new alert";

    @SuppressLint("CommitPrefEdits")
    public SaveState(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharedPreferences.edit();
    }

    public static void setDarkModeState(boolean b){
        editor.putBoolean(DARK_MODE,b);
        editor.apply();
    }

    public static boolean getDarkModeState(){
        return sharedPreferences.getBoolean(DARK_MODE,false);
    }

    public static void setNotificationState(boolean b) {
        editor.putBoolean(NOTIFICATION, b);
        editor.apply();
    }

    public static boolean getNotificationState() {
        return sharedPreferences.getBoolean(NOTIFICATION, true);
    }

    public static void setNewAlert(int counter) {
        mutableLiveData.setValue(counter);
        editor.putInt(NEW_ALERT, counter);
        editor.apply();
    }

    public static MutableLiveData<Integer> getNewAlerts() {
        return mutableLiveData;
    }

    public static int getLastAlerts() {
        return sharedPreferences.getInt(NEW_ALERT, 0);
    }

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return false;
                } else return activeNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE;
            }
        }
        return true;
    }
}
