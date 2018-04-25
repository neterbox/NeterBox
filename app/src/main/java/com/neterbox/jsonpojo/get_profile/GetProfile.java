package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 14-02-2018.
 */

public class GetProfile {

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
    private GetProfileDatum data;
    @SerializedName("total_postcount")
    @Expose
    private Integer totalPostcount;

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

    public GetProfileDatum getData() {
        return data;
    }

    public void setData(GetProfileDatum data) {
        this.data = data;
    }

    public Integer getTotalPostcount() {
        return totalPostcount;
    }

    public void setTotalPostcount(Integer totalPostcount) {
        this.totalPostcount = totalPostcount;
    }


}