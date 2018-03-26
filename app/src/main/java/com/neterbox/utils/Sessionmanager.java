package com.neterbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddPojo;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.jsonpojo.circle.CircleListDatum;

import com.neterbox.jsonpojo.Login.LoginDatum;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListPojo;
import com.neterbox.jsonpojo.editprofile.EditpageDatum;
import com.neterbox.jsonpojo.following.FollowingDatum;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.jsonpojo.register.RegistrationDatum;
import com.neterbox.jsonpojo.sendfriendrequest.SendRequestDatum;

/**
 * Created by DeLL on 18-01-2018.
 */

public class Sessionmanager {
    static SharedPreferences sharedPreferences;
    Context context;
    public static final String mypreference = "mypref";
    public static final String Id = "idKey";
    public static final String Email = "emailKey";
    public static final String Name = "nameKey";
    public static final String Username = "UsernameKey";
    public static final String Address = "AddressKey";
    public static final String Birthdate = "BirthdateKey";
    public static final String Company = "CompanyKey";
    public static final String Gender = "GenderKey";
    public static final String Latitude = "LatitudeKey";
    public static final String Longitude = "LongitudeKey";
    public static final String Password = "PasswordKey";
    public static final String Phone_number = "Phone_numberKey";
    public static final String Quickbox_Id = "Quickbox_IdKey";
    public static final String Title = "TitleKey";
    public static final String Type = "TypeKey";
    public static final String Usertype = "UsertypeKey";
    public static final String profile ="pickey";
    public static final String CircleId ="CircleIdkey";
    public static final String CircleName ="CircleNamekey";
    public static final String Files = "Fileskey";
    public static final String index = "indexkey";
    public static final String req_receiverId = "req_receiverIdkey";
    public static final String req_receivername = "req_receivernamekey";
    public static final String req_friendId = "req_friendIdkey";
    public static final String circlepostindex = "indexkey";
    public static final String user_id = "user_idkey";
    public static final String postid = "post_idkey";
    public static final String nearbyId = "nearbyId_idkey";
    public static final String profileId = "profileId_idkey";
    public static final String profileName = "profileName_idkey";
    public static final String profileuname = "profileuname_idkey";
    public static final String profilecreated = "profilecreated_idkey";
    public static final String profilecompany = "profilecompany_idkey";
    public static final String profile_profilepic = "profile_profilepic_idkey";
    public static final String profiletype = "profiletype_idkey";
    public static final String profilefollower = "profilefollower_idkey";
    public static final String profilefollowing = "profilefollowing_idkey";
    public static final String profilefriend = "profilefriend_idkey";
    public static final String profilepostdetail = "profilepostdetail_idkey";
    public static final String frndId = "frndIdkey";
    public static final String frndname = "frndNamekey";
    public static final String frndPic = "frndPickey";
    public static final String frndTitle = "frndTitlekey";
    public static final String frndCompany = "frndCompanykey";
    public static final String frndEmail = "frndEmailkey";
    public static final String dialogId = "dialogIdkey";
    public static final String receiver_Id = "receiver_Idkey";
    public static final String receiver_name = "receiver_namekey";
    public static final String receiver_pic = "receiver_pickey";
    public static final String receiver_qb_id = "receiver_qb_idkey";
    public static final String dialog_Id = "dialog_Idkey";
    public static final String dialog_type = "dialog_typekey";
    public static final String group_name = "group_namekey";
    public static final String dialog_status = "dialog_statuskey";
    public static final String chat_receiver_Id = "chat_receiver_Idkey";
    public static final String chat_receiver_Name = "chat_receiver_Namekey";
    public static final String chat_receiver_qbID = "chat_receiver_qbIDkey";
    public static final String chat_receiver_pic = "chat_receiver_pickey";
    public static final String chat_receiver_title = "chat_receiver_titlekey";
    public static final String follower_id = "follower_idkey";
    public static final String follower_name = "follower_namekey";
    public static final String follower_pic = "follower_pickey";
    public static final String follower_title = "follower_titlekey";
    public static final String following_id = "following_idkey";
    public static final String following_name = "following_namekey";
    public static final String following_pic = "following_pickey";
    public static final String following_title = "following_titlekey";


    public Sessionmanager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }


    public String getValue(String KEY_ID){
        return sharedPreferences.getString(KEY_ID, "");
    }


    public void putSessionValue(String KEY_NAME, String KEY_VALUE) {
        sharedPreferences.edit().putString(KEY_NAME, KEY_VALUE).apply();
    }

    public static void setPreferenceBoolean(Context activity, String key, boolean value) {
        sharedPreferences = activity.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferenceBoolean(Context activity, String key, boolean Default) {
        sharedPreferences = activity.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, Default);
    }

    public void logoutUser()
    {
        sharedPreferences.edit().clear().apply();
    }

    public void createSession_userLogin(LoginDatum userLogin)
    {
        sharedPreferences.edit().putString(Id,userLogin.getUser().getId()).apply();
        sharedPreferences.edit().putString(Name,userLogin.getUser().getName()).apply();
        sharedPreferences.edit().putString(Username,userLogin.getUser().getUsername()).apply();
        sharedPreferences.edit().putString(Address,userLogin.getUser().getAddress()).apply();
        sharedPreferences.edit().putString(Birthdate,userLogin.getUser().getBirthDate()).apply();
        sharedPreferences.edit().putString(Company,userLogin.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(Email,userLogin.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Gender,userLogin.getUser().getGender()).apply();
        sharedPreferences.edit().putString(Latitude,userLogin.getUser().getLatitude()).apply();
        sharedPreferences.edit().putString(Longitude,userLogin.getUser().getLongitude()).apply();
        sharedPreferences.edit().putString(Password,userLogin.getUser().getPassword()).apply();
        sharedPreferences.edit().putString(Phone_number,userLogin.getUser().getPhoneNumber()).apply();
        sharedPreferences.edit().putString(profile,userLogin.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(Quickbox_Id,userLogin.getUser().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(Title,userLogin.getUser().getTitle()).apply();
        sharedPreferences.edit().putString(Type,userLogin.getUser().getType()).apply();
        sharedPreferences.edit().putString(Usertype,userLogin.getUser().getUsertype()).apply();
    }

    public void createSession_userRegister(RegistrationDatum userregister)
    {
        sharedPreferences.edit().putString(Id,userregister.getUser().getId()).apply();
        sharedPreferences.edit().putString(Name,userregister.getUser().getName()).apply();
        sharedPreferences.edit().putString(Username,userregister.getUser().getUsername()).apply();
        sharedPreferences.edit().putString(Address,userregister.getUser().getAddress()).apply();
        sharedPreferences.edit().putString(Birthdate,userregister.getUser().getBirthDate()).apply();
        sharedPreferences.edit().putString(Company,userregister.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(Email,userregister.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Gender,userregister.getUser().getGender()).apply();
        sharedPreferences.edit().putString(Latitude,userregister.getUser().getLatitude()).apply();
        sharedPreferences.edit().putString(Longitude,userregister.getUser().getLongitude()).apply();
        sharedPreferences.edit().putString(Password,userregister.getUser().getPassword()).apply();
        sharedPreferences.edit().putString(Phone_number,userregister.getUser().getPhoneNumber()).apply();
        sharedPreferences.edit().putString(profile,userregister.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(Quickbox_Id,userregister.getUser().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(Title,userregister.getUser().getTitle()).apply();
        sharedPreferences.edit().putString(Type,userregister.getUser().getType()).apply();
        sharedPreferences.edit().putString(Usertype,userregister.getUser().getUsertype()).apply();
    }

    public void createSession_userEdit(EditpageDatum useredit)
    {
        sharedPreferences.edit().putString(Id,useredit.getUser().getId()).apply();
        sharedPreferences.edit().putString(Name,useredit.getUser().getName()).apply();
        sharedPreferences.edit().putString(Username,useredit.getUser().getUsername()).apply();
        sharedPreferences.edit().putString(Address,useredit.getUser().getAddress()).apply();
        sharedPreferences.edit().putString(Birthdate,useredit.getUser().getBirthDate()).apply();
        sharedPreferences.edit().putString(Company,useredit.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(Email,useredit.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(Gender,useredit.getUser().getGender()).apply();
        sharedPreferences.edit().putString(Latitude,useredit.getUser().getLatitude()).apply();
        sharedPreferences.edit().putString(Longitude,useredit.getUser().getLongitude()).apply();
        sharedPreferences.edit().putString(Password,useredit.getUser().getPassword()).apply();
        sharedPreferences.edit().putString(Phone_number,useredit.getUser().getPhoneNumber()).apply();
        sharedPreferences.edit().putString(profile,useredit.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(Quickbox_Id,useredit.getUser().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(Title,useredit.getUser().getTitle()).apply();
        sharedPreferences.edit().putString(Type,useredit.getUser().getType()).apply();
        sharedPreferences.edit().putString(Usertype,useredit.getUser().getUsertype()).apply();
    }

    public void createSession_circledata(CircleListDatum circledata) {
        sharedPreferences.edit().putString(CircleId, circledata.getCircle().getId()).apply();
        sharedPreferences.edit().putString(CircleName, circledata.getCircle().getName()).apply();
        sharedPreferences.edit().putString(Files, circledata.getCircle().getFiles()).apply();
    }

    public void createSession_userprofile(GetProfileDatum getProfileDatum) {
        sharedPreferences.edit().putString(profileId, getProfileDatum.getUser().getId()).apply();
        sharedPreferences.edit().putString(profileName, getProfileDatum.getUser().getName()).apply();
        sharedPreferences.edit().putString(profileuname, getProfileDatum.getUser().getEmail()).apply();
        sharedPreferences.edit().putString(profilecreated, getProfileDatum.getUser().getCreated()).apply();
        sharedPreferences.edit().putString(profilecompany, getProfileDatum.getUser().getCompany()).apply();
        sharedPreferences.edit().putString(profile_profilepic, getProfileDatum.getUser().getProfilePic()).apply();
        sharedPreferences.edit().putString(profiletype, getProfileDatum.getUser().getType()).apply();
        sharedPreferences.edit().putString(profilefollower, getProfileDatum.getFollowerCount().toString()).apply();
        sharedPreferences.edit().putString(profilefollowing, getProfileDatum.getFollowingCount().toString()).apply();
        sharedPreferences.edit().putString(profilefriend, getProfileDatum.getFriendCount().toString()).apply();
        sharedPreferences.edit().putString(profilepostdetail, getProfileDatum.getPosetdetail().toString()).apply();
    }


    public void createSession_frienddata(FriendListDatum friendListDatum) {

        sharedPreferences.edit().putString(frndId, friendListDatum.getFriend().getId()).apply();
        sharedPreferences.edit().putString(frndname, friendListDatum.getReceiver().getName()).apply();
        sharedPreferences.edit().putString(frndPic, friendListDatum.getReceiver().getProfilePic()).apply();
        sharedPreferences.edit().putString(frndTitle, friendListDatum.getReceiver().getTitle()).apply();
        sharedPreferences.edit().putString(frndCompany, friendListDatum.getReceiver().getCompany()).apply();
        sharedPreferences.edit().putString(frndEmail, friendListDatum.getReceiver().getEmail()).apply();

    }

    public void createSession_nearbydata(NearbyfriendDatum nearbyfriendDatum) {
        sharedPreferences.edit().putString(nearbyId, nearbyfriendDatum.getUsers().getId()).apply();
    }

    public void send_request(SendRequestDatum sendRequestdata) {
        sharedPreferences.edit().putString(req_receiverId, sendRequestdata.getReceiver().getId()).apply();
        sharedPreferences.edit().putString(req_receivername, sendRequestdata.getReceiver().getName()).apply();
        sharedPreferences.edit().putString(req_friendId, sendRequestdata.getFriend().getId()).apply();

    }

    public void createSession_circlepostlistdata(CirclePostListPojo circlelistdata) {
        sharedPreferences.edit().putString(circlepostindex, circlelistdata.getCode()).apply();

    }

    public void createSession_circlepostadddata(CirclePostAddPojo circlePostAddP) {
        sharedPreferences.edit().putString(user_id, circlePostAddP.getResponse()).apply();

    }


    public void createSession_circlepostDeletdata(CirclePostDeleteP circlePostDeleteP) {
        sharedPreferences.edit().putString(postid, circlePostDeleteP.getResponse()).apply();

    }

    public void createSession_addchat(AddChat addChatDatum)
    {
        sharedPreferences.edit().putString(receiver_Id,addChatDatum.getData().getReceiver().getId()).apply();
        sharedPreferences.edit().putString(receiver_name,addChatDatum.getData().getReceiver().getName()).apply();
        sharedPreferences.edit().putString(receiver_pic,addChatDatum.getData().getReceiver().getProfilePic()).apply();
        sharedPreferences.edit().putString(receiver_qb_id,addChatDatum.getData().getReceiver().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(dialog_Id,addChatDatum.getData().getTblDailog().getDialogId()).apply();
        sharedPreferences.edit().putString(dialog_type,addChatDatum.getData().getTblDailog().getDialogType()).apply();
        sharedPreferences.edit().putString(group_name,addChatDatum.getData().getTblDailog().getGroupName()).apply();
        sharedPreferences.edit().putBoolean(dialog_status,addChatDatum.getData().getTblDailog().getDialogStatus()).apply();
    }

    public void createSession_chatlist(ChatListDatum ChatlistDatum)
    {
        sharedPreferences.edit().putString(chat_receiver_Id,ChatlistDatum.getReceiver().getId()).apply();
        sharedPreferences.edit().putString(chat_receiver_Name,ChatlistDatum.getReceiver().getName()).apply();
        sharedPreferences.edit().putString(chat_receiver_qbID,ChatlistDatum.getReceiver().getQuickbloxId()).apply();
        sharedPreferences.edit().putString(chat_receiver_pic,ChatlistDatum.getReceiver().getProfilePic()).apply();
        sharedPreferences.edit().putString(chat_receiver_title,ChatlistDatum.getReceiver().getTitle()).apply();
    }

    public void createSession_followinglist(FollowingDatum followingDatum) {
        sharedPreferences.edit().putString(follower_id, followingDatum.getFollowerDetail().getId()).apply();
        sharedPreferences.edit().putString(follower_name, followingDatum.getFollowerDetail().getName()).apply();
        sharedPreferences.edit().putString(follower_pic, followingDatum.getFollowerDetail().getProfilePic()).apply();
        sharedPreferences.edit().putString(follower_title, followingDatum.getFollowerDetail().getTitle()).apply();
        sharedPreferences.edit().putString(following_id, followingDatum.getFollowingDetail().getId()).apply();
        sharedPreferences.edit().putString(following_name, followingDatum.getFollowingDetail().getName()).apply();
        sharedPreferences.edit().putString(following_pic, followingDatum.getFollowingDetail().getProfilePic()).apply();
        sharedPreferences.edit().putString(following_title, followingDatum.getFollowingDetail().getTitle()).apply();
    }
}