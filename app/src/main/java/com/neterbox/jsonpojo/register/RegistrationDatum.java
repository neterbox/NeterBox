package com.neterbox.jsonpojo.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sejal on 1/12/2018.
 */

public class RegistrationDatum {
    @SerializedName("user")
    @Expose
    private registrationuser user;

    public registrationuser getUser() {
        return user;
    }

    public void setUser(registrationuser user) {
        this.user = user;
    }

}
