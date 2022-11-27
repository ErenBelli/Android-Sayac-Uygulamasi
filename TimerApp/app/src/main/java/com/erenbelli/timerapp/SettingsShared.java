package com.erenbelli.timerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsShared {

    int upperLimit, lowerLimit, currentValue;
    boolean upperVib, upperVoice, lowerVib, lowerVoice;

    static SettingsShared settingsShared = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private SettingsShared(Context context){
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadValues();
    }

    public static  SettingsShared getSettingsShared(Context context){
        if (settingsShared == null){
            settingsShared = new SettingsShared(context);
        }
        return settingsShared;
    }
    public void saveValues(){
        editor.putInt("upperLimit",upperLimit);
        editor.putInt("lowerLimit",lowerLimit);
        editor.putInt("currentValue",currentValue);
        editor.putBoolean("upperVib", upperVib);
        editor.putBoolean("upperVoice", upperVoice);
        editor.putBoolean("lowerVib", lowerVib);
        editor.putBoolean("lowerVoice", lowerVoice);
        editor.commit();

    }


    public void loadValues(){
        upperLimit = sharedPreferences.getInt("upperLimit",50);
        lowerLimit = sharedPreferences.getInt("lowerLimit",0);
        currentValue = sharedPreferences.getInt("currentValue", 0);
        upperVib = sharedPreferences.getBoolean("upperVib", false);
        lowerVib = sharedPreferences.getBoolean("lowerVib", false);
        lowerVoice = sharedPreferences.getBoolean("lowerVoice", false);
        upperVoice = sharedPreferences.getBoolean("upperVoice", false);


    }


}
