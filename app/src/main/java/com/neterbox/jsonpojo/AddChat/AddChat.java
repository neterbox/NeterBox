package com.neterbox.jsonpojo.AddChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 23-02-2018.
 */

public class AddChat {
    @SerializedName("data")
    @Expose
    private AddChatDatum data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public AddChatDatum getData() {
        return data;
    }

    public void setData(AddChatDatum data) {
        this.data = data;
    }

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
}
