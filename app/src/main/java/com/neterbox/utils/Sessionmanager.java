package com.neterbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListDatum;
import com.neterbox.jsonpojo.circle.CircleListDatum;

import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.Login.LoginDatum;
import com.neterbox.jsonpojo.register.RegistrationDatum;

import static android.accounts.AccountManager.KEY_PASSWORD;

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
    public static final String circleId = "circleIdKey";
    public static final String circleName = "circleNameKey";
    public static final String Username = "UsernameKey";
    public static final String Address = "AddressKey";
    public static final String Birthdate = "BirthdateKey";
    public static final String Company = "CompanyKey";
    public static final String Gender = "GenderKey";
    public static final String Latitude = "LatitudeKey";
    public static final String Longitude = "LongitudeKey";
    public static final String Password = "PasswordKey";
    public static final String Phone_number = "Phone_numberKey";
    public static final String Quickbox_Id = "Quickbox_IdKey";
    public static final String Title = "TitleKey";
    public static final String Type = "TypeKey";
    public static final String Usertype = "UsertypeKey";
    public static final String profile = "pickey";
    public static final String Files = "Fileskey";
    public static final String index = "indexkey";
    public static final String loginuserid = "loginuserkey";


    public Sessionmanager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }

    public String getValue(String KEY_ID) {
        return sharedPreferences.getString(KEY_ID, "");
    }


    public void putSessionValue(String KEY_NAME, String KEY_VALUE) {
        sharedPreferences.edit().putString(KEY_NAME, KEY_VALUE).apply();
    }

    public static void setPreferenceBoolean(Context activity, String key, boolean value) {
        sharedPreferences = activity.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context activity, String key, boolean Default) {
        sharedPreferences = activity.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, Default);
    }

    public void logoutUser() {
        sharedPreferences.edit().clear().apply();
    }

    public void createSession_userLogin(LoginDatum userLogin) {
        sharedPreferences.edit().putString(Id, userLogin.getUser().getId()).apply();
        sharedPreferences.edit().putString(Name, userLogin.getUser().getName()).apply();
        sharedPreferences.edit().putString(Username, userLogin.getUser().getUsername()).apply();
        sharedPreferences.edit().putString(Address, userLogin.getUser().getAddress()).apply();
        sharedPreferences.edit().putString(Birthdate, userLogin.getUser().getBirthDate()).apply();
        sharedPreferences.edit().putString(Company, userLogin.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(Email, userLogin.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Gender, userLogin.getUser().getGender()).apply();
        sharedPreferences.edit().putString(Latitude, userLogin.getUser().getLatitude()).apply();
        sharedPreferences.edit().putString(Longitude, userLogin.getUser().getLongitude()).apply();
        sharedPreferences.edit().putString(Password, userLogin.getUser().getPassword()).apply();
        sharedPreferences.edit().putString(Phone_number, userLogin.getUser().getPhoneNumber()).apply();
        sharedPreferences.edit().putString(profile, userLogin.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(Quickbox_Id, userLogin.getUser().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(Title, userLogin.getUser().getTitle()).apply();
        sharedPreferences.edit().putString(Type, userLogin.getUser().getType()).apply();
        sharedPreferences.edit().putString(Usertype, userLogin.getUser().getUsertype()).apply();
    }

    public void createSession_userRegister(RegistrationDatum userregister) {
        sharedPreferences.edit().putString(Id, userregister.getUser().getId()).apply();
        sharedPreferences.edit().putString(Name, userregister.getUser().getName()).apply();
        sharedPreferences.edit().putString(Username, userregister.getUser().getUsername()).apply();
        sharedPreferences.edit().putString(Address, userregister.getUser().getAddress()).apply();
        sharedPreferences.edit().putString(Birthdate, userregister.getUser().getBirthDate()).apply();
        sharedPreferences.edit().putString(Company, userregister.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(Email, userregister.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Gender, userregister.getUser().getGender()).apply();
        sharedPreferences.edit().putString(Latitude, userregister.getUser().getLatitude()).apply();
        sharedPreferences.edit().putString(Longitude, userregister.getUser().getLongitude()).apply();
        sharedPreferences.edit().putString(Password, userregister.getUser().getPassword()).apply();
        sharedPreferences.edit().putString(Phone_number, userregister.getUser().getPhoneNumber()).apply();
        sharedPreferences.edit().putString(profile, userregister.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(Quickbox_Id, userregister.getUser().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(Title, userregister.getUser().getTitle()).apply();
        sharedPreferences.edit().putString(Type, userregister.getUser().getType()).apply();
        sharedPreferences.edit().putString(Usertype, userregister.getUser().getUsertype()).apply();
    }


    public void createSession_circledata(CircleListDatum circledata) {
        sharedPreferences.edit().putString(circleId, circledata.getCircle().getId()).apply();
        sharedPreferences.edit().putString(circleName, circledata.getCircle().getName()).apply();
        sharedPreferences.edit().putString(Files, circledata.getCircle().getFiles()).apply();
    }

    public void createSession_friendrequestlistdata(FriendRequestListDatum frienrequestlistdata) {
        sharedPreferences.edit().putString(loginuserid, frienrequestlistdata.getFriend().getId()).apply();
    }
}