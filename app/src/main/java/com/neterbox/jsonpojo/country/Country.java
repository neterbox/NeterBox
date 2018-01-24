package com.neterbox.jsonpojo.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 20-01-2018.
 */

public class Country {
    @SerializedName("meesae")
    @Expose
    private String meesae;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private List<CountryDatum> data = null;

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

    public List<CountryDatum> getData() {
        return data;
    }

    public void setData(List<CountryDatum> data) {
        this.data = data;
    }

}
