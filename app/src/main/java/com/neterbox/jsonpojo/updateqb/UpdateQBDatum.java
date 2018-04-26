package com.neterbox.jsonpojo.updateqb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 14-03-2018.
 */

public class UpdateQBDatum {

    @SerializedName("User")
    @Expose
    private UpdateQBUser user;

    public UpdateQBUser getUser() {
        return user;
    }

    public void setUser(UpdateQBUser user) {
        this.user = user;
    }


}
