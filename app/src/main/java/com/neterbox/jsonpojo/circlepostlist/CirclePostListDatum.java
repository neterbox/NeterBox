package com.neterbox.jsonpojo.circlepostlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 19-03-2018.
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
    private List<CirclePostListPostFile> postFile = null;
    @SerializedName("Comment")
    @Expose
    private List<CirclePostListComment> comment = null;
    @SerializedName("Postlike")
    @Expose
    private List<CirclePostListPostLike> postlike = null;

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

    public List<CirclePostListPostFile> getPostFile() {
        return postFile;
    }

    public void setPostFile(List<CirclePostListPostFile> postFile) {
        this.postFile = postFile;
    }

    public List<CirclePostListComment> getComment() {
        return comment;
    }

    public void setComment(List<CirclePostListComment> comment) {
        this.comment = comment;
    }

    public List<CirclePostListPostLike> getPostlike() {
        return postlike;
    }

    public void setPostlike(List<CirclePostListPostLike> postlike) {
        this.postlike = postlike;
    }
}
