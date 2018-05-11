package com.neterbox.jsonpojo.circle_chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 28-04-2018.
 */

public class CircleChatDatum {
    @SerializedName("User")
    @Expose
    private CircleChatUser user;
    @SerializedName("Circlechat")
    @Expose
    private CircleChatDetail circlechat;

    public CircleChatUser getUser() {
        return user;
    }

    public void setUser(CircleChatUser user) {
        this.user = user;
    }

    public CircleChatDetail getCirclechat() {
        return circlechat;
    }

    public void setCirclechat(CircleChatDetail circlechat) {
        this.circlechat = circlechat;
    }
}
