package com.neterbox.jsonpojo.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sejal on 1/12/2018.
 */

public class RegistrationDatum implements Serializable {
    @SerializedName("User")
    @Expose
    private registrationuser user;

    public registrationuser getUser() {
        return user;
    }

    public void setUser(registrationuser user) {
        this.user = user;
    }

}
