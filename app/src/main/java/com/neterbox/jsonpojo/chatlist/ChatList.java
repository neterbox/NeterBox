package com.neterbox.jsonpojo.chatlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 14-03-2018.
 */

public class ChatList {

    @SerializedName("data")
    @Expose
    private List<ChatListDatum> data = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ChatListDatum> getData() {
        return data;
    }

    public void setData(List<ChatListDatum> data) {
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
