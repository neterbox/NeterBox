package com.neterbox.jsonpojo.Decline_friend_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriend;

/**
 * Created by sejal on 1/31/2018.
 */

public class DeclineFrndData {
    @SerializedName("AcceptFriend")
    @Expose
    private AcceptFriend friend;
    @SerializedName("Sender")
    @Expose
    private DeclineSender sender;
    @SerializedName("Receiver")
    @Expose
    private DeclineReceiver receiver;

    public AcceptFriend getFriend() {
        return friend;
    }

    public void setFriend(AcceptFriend friend) {
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
