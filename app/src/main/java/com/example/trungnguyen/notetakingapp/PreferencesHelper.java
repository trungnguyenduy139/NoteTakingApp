package com.example.trungnguyen.notetakingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    public static final String KEY_NIGHT_MODE = "key_night_mode";

    private static SharedPreferences getSharedPreferenceUtil(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public static void saveBoolPreferences(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferenceUtil(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolPreferences(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = getSharedPreferenceUtil(context);
        return preferences.getBoolean(key, defaultValue);
    }
}
