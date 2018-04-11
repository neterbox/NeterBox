package com.neterbox.jsonpojo.followingadd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 20-03-2018.
 */

public class FollowingDatum {

    @SerializedName("Follower")
    @Expose
    private Follower follower;
    @SerializedName("FollowerDetail")
    @Expose
    private FollowerDetails followerDetail;
    @SerializedName("FollowingDetail")
    @Expose
    private FollowingDetails followingDetail;

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public FollowerDetails getFollowerDetail() {
        return followerDetail;
    }

    public void setFollowerDetail(FollowerDetails followerDetail) {
        this.followerDetail = followerDetail;
    }

    public FollowingDetails getFollowingDetail() {
        return followingDetail;
    }

    public void setFollowingDetail(FollowingDetails followingDetail) {
        this.followingDetail = followingDetail;
    }

}
