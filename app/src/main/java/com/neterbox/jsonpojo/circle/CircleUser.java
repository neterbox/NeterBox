package com.neterbox.jsonpojo.circle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Created by sejal on 1/30/2018.
 */

public class CircleUser implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("files")
    @Expose
    private String files;

    private int message;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
