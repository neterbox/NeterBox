package com.neterbox.jsonpojo.sendfriendrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 30-01-2018.
 */
public class SendRequest {
    @SerializedName("data")
    @Expose
    private SendRequestDatum data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private String response;

    public SendRequestDatum getData() {
        return data;
    }

    public void setData(SendRequestDatum data) {
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
