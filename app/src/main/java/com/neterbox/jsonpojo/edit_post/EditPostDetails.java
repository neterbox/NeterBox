package com.neterbox.jsonpojo.edit_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 4/21/2018.
 */

public class EditPostDetails {
    @SerializedName("files")
    @Expose
    private String files;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
