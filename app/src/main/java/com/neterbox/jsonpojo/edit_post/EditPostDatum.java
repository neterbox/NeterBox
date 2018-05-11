package com.neterbox.jsonpojo.edit_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 4/21/2018.
 */

public class EditPostDatum {
    @SerializedName("Post")
    @Expose
    private EditPostPost post;
    @SerializedName("Circle")
    @Expose
    private EditPostCircle circle;
    @SerializedName("State")
    @Expose
    private EditPostState state;
    @SerializedName("Country")
    @Expose
    private EditPostCountry country;
    @SerializedName("PostDetail")
    @Expose
    private List<EditPostDetails> postDetail = null;

    public EditPostPost getPost() {
        return post;
    }

    public void setPost(EditPostPost post) {
        this.post = post;
    }

    public EditPostCircle getCircle() {
        return circle;
    }

    public void setCircle(EditPostCircle circle) {
        this.circle = circle;
    }

    public EditPostState getState() {
        return state;
    }

    public void setState(EditPostState state) {
        this.state = state;
    }

    public EditPostCountry getCountry() {
        return country;
    }

    public void setCountry(EditPostCountry country) {
        this.country = country;
    }

    public List<EditPostDetails> getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(List<EditPostDetails> postDetail) {
        this.postDetail = postDetail;
    }

}
