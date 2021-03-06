package com.neterbox.jsonpojo.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 20-03-2018.
 */

public class CommentDatum {

    @SerializedName("Comment")
    @Expose
    private Comment comment;
    @SerializedName("User")
    @Expose
    private CommentsUser user;
    @SerializedName("Post")
    @Expose
    private CommentsPost post;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public CommentsUser getUser() {
        return user;
    }

    public void setUser(CommentsUser user) {
        this.user = user;
    }

    public CommentsPost getPost() {
        return post;
    }

    public void setPost(CommentsPost post) {
        this.post = post;
    }
}
