package com.neterbox.jsonpojo.friend_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DeLL on 12-02-2018.
 */

public class FriendListDatum implements Serializable{
    @SerializedName("Friend")
    @Expose
    private FriendListFriend friend;
    @SerializedName("Sender")
    @Expose
    private FriendListSender sender;
    @SerializedName("Receiver")
    @Expose
    private FriendListReceiver receiver;

    public FriendListFriend getFriend() {
        return friend;
    }

    public void setFriend(FriendListFriend friend) {
        this.friend = friend;
    }

    public FriendListSender getSender() {
        return sender;
    }

    public void setSender(FriendListSender sender) {
        this.sender = sender;
    }

    public FriendListReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(FriendListReceiver receiver) {
        this.receiver = receiver;
    }
}
