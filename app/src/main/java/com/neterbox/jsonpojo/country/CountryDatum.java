package com.neterbox.jsonpojo.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 20-01-2018.
 */

public class CountryDatum {
    @SerializedName("Country")
    @Expose
    private CountryDetails country;

    public CountryDetails getCountry() {
        return country;
    }

    public void setCountry(CountryDetails country) {
        this.country = country;
    }

}

