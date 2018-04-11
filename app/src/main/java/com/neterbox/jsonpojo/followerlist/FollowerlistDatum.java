package com.neterbox.jsonpojo.followerlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 30-03-2018.
 */

public class FollowerlistDatum {

    @SerializedName("Follower")
    @Expose
    private Follower follower;
    @SerializedName("FollowerDetail")
    @Expose
    private FollowerlistDetails followerDetail;
    @SerializedName("FollowingDetail")
    @Expose
    private FollowinglistDetails followingDetail;

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public FollowerlistDetails getFollowerDetail() {
        return followerDetail;
    }

    public void setFollowerDetail(FollowerlistDetails followerDetail) {
        this.followerDetail = followerDetail;
    }

    public FollowinglistDetails getFollowingDetail() {
        return followingDetail;
    }

    public void setFollowingDetail(FollowinglistDetails followingDetail) {
        this.followingDetail = followingDetail;
    }

}
