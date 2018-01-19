package com.neterbox.jsonpojo.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 12-01-2018.
 */

public class Login {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private LoginDatum data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginDatum getData() {
        return data;
    }

    public void setData(LoginDatum data) {
        this.data = data;
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

}

