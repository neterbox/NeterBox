package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DeLL on 14-02-2018.
 */

public class GetProfileDatum implements Serializable {
    @SerializedName("User")
    @Expose
    private GetProfileUser user;

    public GetProfileUser getUser() {
        return user;
    }

    public void setUser(GetProfileUser user) {
        this.user = user;
    }

}
