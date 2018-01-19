package com.neterbox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by DeLL on 18-01-2018.
 */

public class Sessionmanager {
    static SharedPreferences sharedPreferences;
    Context context;
    public static final String mypreference = "mypref";
    public static final String Id = "idKey";
    public static final String Email = "emailKey";
    public static final String Name = "nameKey";
    public static final String profile ="pickey";

    public Sessionmanager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }

    public String getValue(String KEY_ID){
            return sharedPreferences.getString(KEY_ID, "");
        }
    public void putSessionValue(String KEY_NAME, String KEY_VALUE) {
        sharedPreferences.edit().putString(KEY_NAME, KEY_VALUE).apply();
    }

    public static void setPreferenceBoolean( Context activity,String key, boolean value) {
        sharedPreferences = activity.getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context activity, String key, boolean Default) {
        sharedPreferences = activity.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, Default);

    }
}

