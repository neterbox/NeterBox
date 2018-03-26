package com.neterbox.jsonpojo.CirclePostadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 19-03-2018.
 */

public class CirclePostAddPostDetails {
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
