package com.neterbox.jsonpojo.accept_friend_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sejal on 1/31/2018.
 */

public class AcceptFriendRequestDatum {

    @SerializedName("Friend")
    @Expose
    private AcceptFriendRequest friend;
    @SerializedName("Sender")
    @Expose
    private AcceptFriendRequestSender sender;
    @SerializedName("Receiver")
    @Expose
    private AcceptFriendRequestReceiver receiver;

    public AcceptFriendRequest getFriend() {
        return friend;
    }

    public void setFriend(AcceptFriendRequest friend) {
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
    }}
