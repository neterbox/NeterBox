package com.neterbox.jsonpojo.editprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 12-01-2018.
 */

public class EditpageDatum {
    @SerializedName("User")
    @Expose
    private Edituser user;

    public Edituser getUser() {
        return user;
    }

    public void setUser(Edituser user) {
        this.user = user;
    }

}
