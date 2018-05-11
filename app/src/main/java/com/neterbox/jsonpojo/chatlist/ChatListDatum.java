package com.neterbox.jsonpojo.chatlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeLL on 14-03-2018.
 */

public class ChatListDatum {
    @SerializedName("TblDailog")
    @Expose
    private ChatListDialog tblDailog;
    @SerializedName("Receiver")
    @Expose
    private List<ChatListReceiver> receiver = null;

    public ChatListDialog getTblDailog() {
        return tblDailog;
    }

    public void setTblDailog(ChatListDialog tblDailog) {
        this.tblDailog = tblDailog;
    }

    public List<ChatListReceiver> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<ChatListReceiver> receiver) {
        this.receiver = receiver;
    }


}
