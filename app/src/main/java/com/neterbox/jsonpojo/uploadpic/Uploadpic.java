package com.neterbox.jsonpojo.uploadpic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 18-01-2018.
 */

public class Uploadpic {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private UploadpicDatum data;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UploadpicDatum getData() {
        return data;
    }

    public void setData(UploadpicDatum data) {
        this.data = data;
    }

}

