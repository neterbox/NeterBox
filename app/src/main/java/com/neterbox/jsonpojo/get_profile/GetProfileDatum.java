package com.neterbox.jsonpojo.get_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 14-02-2018.
 */

public class GetProfileDatum {
    @SerializedName("User")
    @Expose
    private GetProfileUser user;
    @SerializedName("FriendCount")
    @Expose
    private Integer friendCount;
    @SerializedName("FollowerCount")
    @Expose
    private Integer followerCount;
    @SerializedName("FollowingCount")
    @Expose
    private Integer followingCount;
    @SerializedName("Posetdetail")
    @Expose
    private List<Object> posetdetail = null;

    public GetProfileUser getUser() {
        return user;
    }

    public void setUser(GetProfileUser user) {
        this.user = user;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public List<Object> getPosetdetail() {
        return posetdetail;
    }

    public void setPosetdetail(List<Object> posetdetail) {
        this.posetdetail = posetdetail;
    }

}
