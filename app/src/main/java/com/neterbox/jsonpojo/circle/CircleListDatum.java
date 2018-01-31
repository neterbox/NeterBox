package com.neterbox.jsonpojo.circle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sejal on 1/30/2018.
 */

public class CircleListDatum implements Serializable{

    @SerializedName("Circle")
    @Expose
    private CircleUser circle;

    public CircleUser getCircle() {
        return circle;
    }

    public void setCircle(CircleUser circle) {
        this.circle = circle;
    }

}
