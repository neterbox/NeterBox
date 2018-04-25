package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileComment {

    @SerializedName("Comment")
    @Expose
    private GetProfileComment_ comment;
    @SerializedName("User")
    @Expose
    private GetProfileUser_ user;
    @SerializedName("Post")
    @Expose
    private GetProfilePost_ post;

    public GetProfileComment_ getComment() {
        return comment;
    }

    public void setComment(GetProfileComment_ comment) {
        this.comment = comment;
    }

    public GetProfileUser_ getUser() {
        return user;
    }

    public void setUser(GetProfileUser_ user) {
        this.user = user;
    }

    public GetProfilePost_ getPost() {
        return post;
    }

    public void setPost(GetProfilePost_ post) {
        this.post = post;
    }


}
