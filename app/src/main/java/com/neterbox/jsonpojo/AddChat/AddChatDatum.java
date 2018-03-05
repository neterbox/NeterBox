package com.neterbox.jsonpojo.AddChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 23-02-2018.
 */

public class AddChatDatum
{
    @SerializedName("TblDailog")
    @Expose
    private AdddChatTbldialog tblDailog;
    @SerializedName("Sender")
    @Expose
    private AddChatSender sender;
    @SerializedName("Receiver")
    @Expose
    private AddChatReceiver receiver;

    public AdddChatTbldialog getTblDailog() {
        return tblDailog;
    }

    public void setTblDailog(AdddChatTbldialog tblDailog) {
        this.tblDailog = tblDailog;
    }

    public AddChatSender getSender() {
        return sender;
    }

    public void setSender(AddChatSender sender) {
        this.sender = sender;
    }

    public AddChatReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(AddChatReceiver receiver) {
        this.receiver = receiver;
    }
}
