package com.example.muse.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveState {

    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String DARK_MODE="dark_mode";

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


}
