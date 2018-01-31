package com.neterbox.jsonpojo.near_by_friend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sejal on 1/29/2018.
 */

public class NearbyfriendDatum implements Serializable{
    @SerializedName("Request_status")
    @Expose
    private Integer requestStatus;
    @SerializedName("Received_status")
    @Expose
    private Integer receivedStatus;
    @SerializedName("Miles")
    @Expose
    private String miles;
    @SerializedName("users")
    @Expose
    private NerbyfriendUser users;

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(Integer receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public NerbyfriendUser getUsers() {
        return users;
    }

    public void setUsers(NerbyfriendUser users) {
        this.users = users;
    }

}
