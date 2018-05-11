package com.neterbox.jsonpojo.edit_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 4/21/2018.
 */

public class EditPostCircle {

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
