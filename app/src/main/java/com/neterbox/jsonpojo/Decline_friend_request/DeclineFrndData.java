package com.neterbox.jsonpojo.Decline_friend_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequestFriend;

/**
 * Created by sejal on 1/31/2018.
 */

public class DeclineFrndData {
    @SerializedName("AcceptFriendRequestFriend")
    @Expose
    private AcceptFriendRequestFriend friend;
    @SerializedName("Sender")
    @Expose
    private DeclineSender sender;
    @SerializedName("Receiver")
    @Expose
    private DeclineReceiver receiver;

    public AcceptFriendRequestFriend getFriend() {
        return friend;
    }

    public void setFriend(AcceptFriendRequestFriend friend) {
        this.friend = friend;
    }

    public DeclineSender getSender() {
        return sender;
    }

    public void setSender(DeclineSender sender) {
        this.sender = sender;
    }

    public DeclineReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(DeclineReceiver receiver) {
        this.receiver = receiver;
    }

}
