package com.neterbox.jsonpojo.chatlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 14-03-2018.
 */

public class ChatListDatum {
    @SerializedName("TblDailog")
    @Expose
    private ChatListDialog tblDailog;
    @SerializedName("Receiver")
    @Expose
    private ChatListReceiver receiver;

    public ChatListDialog getTblDailog() {
        return tblDailog;
    }

    public void setTblDailog(ChatListDialog tblDailog) {
        this.tblDailog = tblDailog;
    }

    public ChatListReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(ChatListReceiver receiver) {
        this.receiver = receiver;
    }
}
