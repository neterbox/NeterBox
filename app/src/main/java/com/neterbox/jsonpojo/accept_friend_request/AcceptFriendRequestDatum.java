package com.neterbox.jsonpojo.accept_friend_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sejal on 1/31/2018.
 */

public class AcceptFriendRequestDatum {

    @Expose
    private AcceptFriend friend;
    @SerializedName("Sender")
    @Expose
    private AcceptFriendRequestSender sender;
    @SerializedName("Receiver")
    @Expose
    private AcceptFriendRequestReceiver receiver;

    public AcceptFriend getFriend() {
        return friend;
    }

    public void setFriend(AcceptFriend friend) {
        this.friend = friend;
    }

    public AcceptFriendRequestSender getSender() {
        return sender;
    }

    public void setSender(AcceptFriendRequestSender sender) {
        this.sender = sender;
    }

    public AcceptFriendRequestReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(AcceptFriendRequestReceiver receiver) {
        this.receiver = receiver;
    }
}
