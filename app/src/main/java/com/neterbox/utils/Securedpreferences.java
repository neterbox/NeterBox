package com.neterbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sejal on 1/17/2018.
 */

public class Securedpreferences {

    static SharedPreferences sh_Pref;
    public static String Preference_Name = "NeterBox";

    public static void setPreferenceBoolean( Context activity,String key, boolean value
    ) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh_Pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context activity, String key, boolean Default) {
        sh_Pref = activity.getSharedPreferences(Preference_Name, Context.MODE_PRIVATE);
        return sh_Pref.getBoolean(key, Default);

    }
}