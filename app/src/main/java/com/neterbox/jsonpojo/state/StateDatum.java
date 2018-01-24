package com.neterbox.jsonpojo.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 21-01-2018.
 */

public class StateDatum {

    @SerializedName("State")
    @Expose
    private StateDetails state;

    public StateDetails getState() {
        return state;
    }

    public void setState(StateDetails state) {
        this.state = state;
    }

}


