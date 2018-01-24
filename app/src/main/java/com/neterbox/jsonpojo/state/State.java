package com.neterbox.jsonpojo.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 21-01-2018.
 */

public class State {

    @SerializedName("meesae")
    @Expose
    private String meesae;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private List<StateDatum> data = null;

    public String getMeesae() {
        return meesae;
    }

    public void setMeesae(String meesae) {
        this.meesae = meesae;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StateDatum> getData() {
        return data;
    }

    public void setData(List<StateDatum> data) {
        this.data = data;
    }

//    public class Datum {
//
//        @SerializedName("State")
//        @Expose
//        private State_ state;
//
//        public State_ getState() {
//            return state;
//        }
//
//        public void setState(State_ state) {
//            this.state = state;
//        }
//
//        public class State_ {
//
//            @SerializedName("id")
//            @Expose
//            private String id;
//            @SerializedName("name")
//            @Expose
//            private String name;
//            @SerializedName("country_id")
//            @Expose
//            private String countryId;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getCountryId() {
//                return countryId;
//            }
//
//            public void setCountryId(String countryId) {
//                this.countryId = countryId;
//            }
//
//        }
//    }

}

