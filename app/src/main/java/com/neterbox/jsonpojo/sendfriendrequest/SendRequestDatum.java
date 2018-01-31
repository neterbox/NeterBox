package com.neterbox.jsonpojo.sendfriendrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 30-01-2018.
 */

public class SendRequestDatum {

    @SerializedName("Friend")
    @Expose
    private SendRequestFriend friend;
    @SerializedName("Sender")
    @Expose
    private SendRequestSender sender;
    @SerializedName("Receiver")
    @Expose
    private SendRequestReceiver receiver;

    public SendRequestFriend getFriend() {
        return friend;
    }

    public void setFriend(SendRequestFriend friend) {
        this.friend = friend;
    }

    public SendRequestSender getSender() {
        return sender;
    }

    public void setSender(SendRequestSender sender) {
        this.sender = sender;
    }

    public SendRequestReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(SendRequestReceiver receiver) {
        this.receiver = receiver;
    }

}

