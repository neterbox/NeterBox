package com.neterbox.jsonpojo.updateqb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 14-03-2018.
 */

public class UpdateQB {

    @SerializedName("data")
    @Expose
    private UpdateQBDatum data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public UpdateQBDatum getData() {
        return data;
    }

    public void setData(UpdateQBDatum data) {
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
