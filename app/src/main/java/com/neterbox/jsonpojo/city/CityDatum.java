package com.neterbox.jsonpojo.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 12-02-2018.
 */

public class CityDatum {
    @SerializedName("City")
    @Expose
    private CityUser city;

    public CityUser getCity() {
        return city;
    }

    public void setCity(CityUser city) {
        this.city = city;
    }

}
