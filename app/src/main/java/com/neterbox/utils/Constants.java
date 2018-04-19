package com.neterbox.utils;

import android.util.Patterns;

import com.neterbox.R;
import com.quickblox.sample.core.utils.ResourceUtils;

/**
 * Created by sejal on 1/17/2018.
 */

public class Constants {

    public static String IS_LOGIN = "isLogin";
    public static final int SOCKET_TIMEOUT = 60;
    public static final String AUDIO = "audio";
    public static final String VIDEO = "video";
    public static final String PHOTO = "photo";
    public static final String DOCUMENT = "content-type";
    public static final String CONTACT = "contact";
    public static final String mypreference = "mypref";
    public static String PREFERENCE_NAME = "neterbox.pref";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static final int MY_PERMISSIONS_REQUEST_GALLARY = 11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;
    public static final int MY_PERMISSIONS_RECORD_AUDIO = 13;
    public static final int MY_PERMISSIONS_RECORD_VIDEO = 13;
    public static final int ACCESS_FINE_LOCATION = 14;
    public static final String[] cvFileExtensions = new String[]{"pdf", "xlsx", "docx"};

    public static String shareLoc = "";


    public static boolean isEmpty(String data)
    {
        // TODO Auto-generated method stub

        if (data == null)
        {

            return true;
        }

        if (data.trim().equals(""))
        {
            return true;
        }

        return false;
    }
    /**
     * Check Mail Is Valid Or Not
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email)
    {
        // TODO Auto-generated method stub

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return true;
        }
        return false;
    }

}
