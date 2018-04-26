package com.neterbox.jsonpojo.AddChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeLL on 23-02-2018.
 */

public class AddChatTbldialog {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("sender_qb_id")
    @Expose
    private String senderQbId;
    @SerializedName("receiver_qb_id")
    @Expose
    private String receiverQbId;
    @SerializedName("dialog_id")
    @Expose
    private String dialogId;
    @SerializedName("dialog_type")
    @Expose
    private String dialogType;
    @SerializedName("dialog_status")
    @Expose
    private Boolean dialogStatus;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderQbId() {
        return senderQbId;
    }

    public void setSenderQbId(String senderQbId) {
        this.senderQbId = senderQbId;
    }

    public String getReceiverQbId() {
        return receiverQbId;
    }

    public void setReceiverQbId(String receiverQbId) {
        this.receiverQbId = receiverQbId;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public String getDialogType() {
        return dialogType;
    }

    public void setDialogType(String dialogType) {
        this.dialogType = dialogType;
    }

    public Boolean getDialogStatus() {
        return dialogStatus;
    }

    public void setDialogStatus(Boolean dialogStatus) {
        this.dialogStatus = dialogStatus;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
