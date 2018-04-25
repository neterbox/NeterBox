package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfilePost_ {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("circle_id")
    @Expose
    private Object circleId;
    @SerializedName("countries_id")
    @Expose
    private Object countriesId;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("created")
    @Expose
    private Object created;
    @SerializedName("modified")
    @Expose
    private Object modified;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getCircleId() {
        return circleId;
    }

    public void setCircleId(Object circleId) {
        this.circleId = circleId;
    }

    public Object getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(Object countriesId) {
        this.countriesId = countriesId;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Object getModified() {
        return modified;
    }

    public void setModified(Object modified) {
        this.modified = modified;
    }

}
