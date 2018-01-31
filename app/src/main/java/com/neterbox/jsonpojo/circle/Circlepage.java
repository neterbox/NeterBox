package com.neterbox.jsonpojo.circle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sejal on 1/30/2018.
 */

public class Circlepage implements Serializable {

    @SerializedName("meesae")
    @Expose
    private String meesae;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_circle")
    @Expose
    private Integer totalCircle;
    @SerializedName("data")
    @Expose
    private List<CircleListDatum> data = null;

    public String getMeesae() {
        return meesae;
    }

    public void setMeesae(String meesae) {
        this.meesae = meesae;
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

    public Integer getTotalCircle() {
        return totalCircle;
    }

    public void setTotalCircle(Integer totalCircle) {
        this.totalCircle = totalCircle;
    }

    public List<CircleListDatum> getData() {
        return data;
    }

    public void setData(List<CircleListDatum> data) {
        this.data = data;
    }

}
