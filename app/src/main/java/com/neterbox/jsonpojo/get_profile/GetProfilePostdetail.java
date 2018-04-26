package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfilePostdetail {

    @SerializedName("Post")
    @Expose
    private GetProfilePost post;
    @SerializedName("Circle")
    @Expose
    private GetProfileCircle circle;
    @SerializedName("Country")
    @Expose
    private GetProfileCountry country;
    @SerializedName("State")
    @Expose
    private GetProfileState state;
    @SerializedName("PostFile")
    @Expose
    private List<GetProfilePostfiles> postFile = null;
    @SerializedName("PostLike")
    @Expose
    private List<GetProfilePostLike> postLike = null;
    @SerializedName("Comment")
    @Expose
    private List<GetProfileComment> comment = null;

    public GetProfilePost getPost() {
        return post;
    }

    public void setPost(GetProfilePost post) {
        this.post = post;
    }

    public GetProfileCircle getCircle() {
        return circle;
    }

    public void setCircle(GetProfileCircle circle) {
        this.circle = circle;
    }

    public GetProfileCountry getCountry() {
        return country;
    }

    public void setCountry(GetProfileCountry country) {
        this.country = country;
    }

    public GetProfileState getState() {
        return state;
    }

    public void setState(GetProfileState state) {
        this.state = state;
    }

    public List<GetProfilePostfiles> getPostFile() {
        return postFile;
    }

    public void setPostFile(List<GetProfilePostfiles> postFile) {
        this.postFile = postFile;
    }

    public List<GetProfilePostLike> getPostLike() {
        return postLike;
    }

    public void setPostLike(List<GetProfilePostLike> postLike) {
        this.postLike = postLike;
    }

    public List<GetProfileComment> getComment() {
        return comment;
    }

    public void setComment(List<GetProfileComment> comment) {
        this.comment = comment;
    }


}




