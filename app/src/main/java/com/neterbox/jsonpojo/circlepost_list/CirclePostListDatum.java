package com.neterbox.jsonpojo.circlepost_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 27-04-2018.
 */

public class CirclePostListDatum {

    @SerializedName("Post")
    @Expose
    private CirclePostListPost post;
    @SerializedName("Circle")
    @Expose
    private CirclePostListCircle circle;
    @SerializedName("Country")
    @Expose
    private CirclePostListCountry country;
    @SerializedName("State")
    @Expose
    private CirclePostListState state;
    @SerializedName("User")
    @Expose
    private CirclePostListUser user;
    @SerializedName("PostFile")
    @Expose
    private List<CircleListPostFile> postFile = null;
    @SerializedName("Comment")
    @Expose
    private List<Object> comment = null;
    @SerializedName("Postlike")
    @Expose
    private String postlike;
    @SerializedName("is_like")
    @Expose
    private Boolean isLike;

    public CirclePostListPost getPost() {
        return post;
    }

    public void setPost(CirclePostListPost post) {
        this.post = post;
    }

    public CirclePostListCircle getCircle() {
        return circle;
    }

    public void setCircle(CirclePostListCircle circle) {
        this.circle = circle;
    }

    public CirclePostListCountry getCountry() {
        return country;
    }

    public void setCountry(CirclePostListCountry country) {
        this.country = country;
    }

    public CirclePostListState getState() {
        return state;
    }

    public void setState(CirclePostListState state) {
        this.state = state;
    }

    public CirclePostListUser getUser() {
        return user;
    }

    public void setUser(CirclePostListUser user) {
        this.user = user;
    }

    public List<CircleListPostFile> getPostFile() {
        return postFile;
    }

    public void setPostFile(List<CircleListPostFile> postFile) {
        this.postFile = postFile;
    }

    public List<Object> getComment() {
        return comment;
    }

    public void setComment(List<Object> comment) {
        this.comment = comment;
    }

    public String getPostlike() {
        return postlike;
    }

    public void setPostlike(String postlike) {
        this.postlike = postlike;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

}
