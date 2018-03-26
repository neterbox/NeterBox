package com.neterbox.jsonpojo.AddChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neterbox.jsonpojo.updateqb.UpdateQBDatum;

/**
 * Created by DeLL on 23-02-2018.
 */

public class AddChat {
    @SerializedName("data")
    @Expose
    private AddChatDatum data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public AddChatDatum getData() {
        return data;
    }

    public void setData(AddChatDatum data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class AddChatDatum {
        @SerializedName("TblDailog")
        @Expose
        private AddChatTbldialog tblDailog;
        @SerializedName("Sender")
        @Expose
        private AddChatSender sender;
        @SerializedName("Receiver")
        @Expose
        private AddChatReceiver receiver;

        public AddChatTbldialog getTblDailog() {
            return tblDailog;
        }

        public void setTblDailog(AddChatTbldialog tblDailog) {
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

        public class AddChatReceiver {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("quickblox_id")
            @Expose
            private String quickbloxId;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("birth_date")
            @Expose
            private String birthDate;
            @SerializedName("phone_number")
            @Expose
            private String phoneNumber;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;
            @SerializedName("company")
            @Expose
            private String company;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("usertype")
            @Expose
            private String usertype;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQuickbloxId() {
                return quickbloxId;
            }

            public void setQuickbloxId(String quickbloxId) {
                this.quickbloxId = quickbloxId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
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

        public class AddChatSender {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("quickblox_id")
            @Expose
            private String quickbloxId;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("birth_date")
            @Expose
            private String birthDate;
            @SerializedName("phone_number")
            @Expose
            private String phoneNumber;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("profile_pic")
            @Expose
            private String profilePic;
            @SerializedName("company")
            @Expose
            private String company;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("usertype")
            @Expose
            private String usertype;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQuickbloxId() {
                return quickbloxId;
            }

            public void setQuickbloxId(String quickbloxId) {
                this.quickbloxId = quickbloxId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
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


    }
}



