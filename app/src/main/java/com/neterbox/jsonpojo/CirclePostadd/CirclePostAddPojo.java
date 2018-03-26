package com.neterbox.jsonpojo.CirclePostadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sejal on 2/3/2018.
 */

public class CirclePostAddPojo implements Serializable {

    @SerializedName("data")
    @Expose
    private CirclePostAddDatum data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private String response;

    public CirclePostAddDatum getData() {
        return data;
    }

    public void setData(CirclePostAddDatum data) {
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
