package com.neterbox.jsonpojo.near_by_friend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sejal on 1/29/2018.
 */

public class Nearbyfriend {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private List<NearbyfriendDatum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<NearbyfriendDatum> getData() {
        return data;
    }

    public void setData(List<NearbyfriendDatum> data) {
        this.data = data;
    }

}

