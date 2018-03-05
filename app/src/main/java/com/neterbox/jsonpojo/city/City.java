package com.neterbox.jsonpojo.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 12-02-2018.
 */

public class City {
    @SerializedName("meesae")
    @Expose
    private String meesae;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private List<CityDatum> data = null;

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

    public List<CityDatum> getData() {
        return data;
    }

    public void setData(List<CityDatum> data) {
        this.data = data;
    }
}
