package com.neterbox.jsonpojo.uploadpic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 18-01-2018.
 */

public class UploadpicDatum {
    @SerializedName("User")
    @Expose
    private UploadpicUser user;

    public UploadpicUser getUser() {
        return user;
    }

    public void setUser(UploadpicUser user) {
        this.user = user;
    }

}

