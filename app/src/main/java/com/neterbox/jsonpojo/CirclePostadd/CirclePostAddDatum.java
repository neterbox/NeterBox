package com.neterbox.jsonpojo.CirclePostadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 19-03-2018.
 */

public class CirclePostAddDatum {
    @SerializedName("Post")
    @Expose
    private CirclePostAddPost post;
    @SerializedName("Circle")
    @Expose
    private CirckePostAddCircle circle;
    @SerializedName("State")
    @Expose
    private CirclePostAddState state;
    @SerializedName("Country")
    @Expose
    private CirclePostAddCountry country;
    @SerializedName("PostDetail")
    @Expose
    private List<CirclePostAddPostDetails> postDetail = null;

    public CirclePostAddPost getPost() {
        return post;
    }

    public void setPost(CirclePostAddPost post) {
        this.post = post;
    }

    public CirckePostAddCircle getCircle() {
        return circle;
    }

    public void setCircle(CirckePostAddCircle circle) {
        this.circle = circle;
    }

    public CirclePostAddState getState() {
        return state;
    }

    public void setState(CirclePostAddState state) {
        this.state = state;
    }

    public CirclePostAddCountry getCountry() {
        return country;
    }

    public void setCountry(CirclePostAddCountry country) {
        this.country = country;
    }

    public List<CirclePostAddPostDetails> getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(List<CirclePostAddPostDetails> postDetail) {
        this.postDetail = postDetail;
    }
}
