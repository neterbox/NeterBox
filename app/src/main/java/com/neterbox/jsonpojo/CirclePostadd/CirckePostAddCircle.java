package com.neterbox.jsonpojo.CirclePostadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 19-03-2018.
 */

public class CirckePostAddCircle {
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("files")
    @Expose
    private Object files;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }

}
