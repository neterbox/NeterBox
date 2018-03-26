package com.neterbox.jsonpojo.circlepostlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sejal on 2/2/2018.
 */

public class CirclePostListPojo implements Serializable{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_post")
    @Expose
    private Integer totalPost;
    @SerializedName("data")
    @Expose
    private List<CirclePostListDatum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }

    public List<CirclePostListDatum> getData() {
        return data;
    }

    public void setData(List<CirclePostListDatum> data) {
        this.data = data;
    }

}
