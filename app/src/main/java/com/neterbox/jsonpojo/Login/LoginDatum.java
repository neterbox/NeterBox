package com.neterbox.jsonpojo.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 12-01-2018.
 */

public class LoginDatum {

    @SerializedName("User")
    @Expose
    private Loginuser user;

    public Loginuser getUser() {
        return user;
    }

    public void setUser(Loginuser user) {
        this.user = user;
    }

}