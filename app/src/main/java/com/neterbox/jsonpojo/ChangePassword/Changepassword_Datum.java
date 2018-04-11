package com.neterbox.jsonpojo.ChangePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Changepassword_Datum {

    @SerializedName("User")
    @Expose
    private Changepassword_user user;

    public Changepassword_user getUser() {
        return user;
    }

    public void setUser(Changepassword_user user) {
        this.user = user;
    }
}
