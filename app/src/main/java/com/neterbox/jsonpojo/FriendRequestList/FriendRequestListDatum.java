package com.neterbox.jsonpojo.FriendRequestList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sejal on 2/2/2018.
 */

public class FriendRequestListDatum  implements Serializable{

    @SerializedName("Friend")
    @Expose
    private FriendUser friend;
    @SerializedName("Sender")
    @Expose
    private FriendRequestListSender sender;
    @SerializedName("Receiver")
    @Expose
    private FriendRequestListReceiver receiver;

    public FriendUser getFriend() {
        return friend;
    }

    public void setFriend(FriendUser friend) {
        this.friend = friend;
    }

    public FriendRequestListSender getSender() {
        return sender;
    }

    public void setSender(FriendRequestListSender sender) {
        this.sender = sender;
    }

    public FriendRequestListReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(FriendRequestListReceiver receiver) {
        this.receiver = receiver;
    }

}
