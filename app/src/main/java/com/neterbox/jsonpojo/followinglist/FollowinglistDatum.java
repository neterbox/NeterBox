package com.neterbox.jsonpojo.followinglist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 30-03-2018.
 */

public class FollowinglistDatum {

    @SerializedName("Follower")
    @Expose
    private FollowingListFollower follower;
    @SerializedName("FollowerDetail")
    @Expose
    private FollowingListFollowerDetails followerDetail;
    @SerializedName("FollowingDetail")
    @Expose
    private FollowingListFolllowingDetails followingDetail;

    public FollowingListFollower getFollower() {
        return follower;
    }

    public void setFollower(FollowingListFollower follower) {
        this.follower = follower;
    }

    public FollowingListFollowerDetails getFollowerDetail() {
        return followerDetail;
    }

    public void setFollowerDetail(FollowingListFollowerDetails followerDetail) {
        this.followerDetail = followerDetail;
    }

    public FollowingListFolllowingDetails getFollowingDetail() {
        return followingDetail;
    }

    public void setFollowingDetail(FollowingListFolllowingDetails followingDetail) {
        this.followingDetail = followingDetail;
    }
}
