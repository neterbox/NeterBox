package com.neterbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.Friend_RequestList_Adapter;
import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreindProfile extends Activity {

    Sessionmanager sessionmanager;
    ListView frnd_listview;
    LinearLayout lfrnd_chat;
    ImageView ileft, iright;
    TextView title, tprofile_name, tcompany_name;
    Friendpro_Adapter adapter;
    Activity activity;
    SharedPreferences sharedPreferences;
    public static final int REQUEST_DIALOG_ID_FOR_UPDATE = 1;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    CircleImageView frnd_profile;
    FriendListDatum friendListdata = new FriendListDatum();
    List<QBChatDialog> qbChatDialogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind_profile);

        activity = this;
        friendListdata = (FriendListDatum) getIntent().getSerializableExtra("friendlist");
        Log.e("data", new Gson().toJson(friendListdata));
        sessionmanager = new Sessionmanager(this);
        sharedPreferences = activity.getSharedPreferences(Constants.mypreference, Context.MODE_PRIVATE);
        idMappings();
        listener();

        Friendpro_Adapter adapter = new Friendpro_Adapter(activity);
        frnd_listview.setAdapter(adapter);

        if (friendListdata != null) {
            tprofile_name.setText(friendListdata.getReceiver().getName());
            Glide.with(activity).load(friendListdata.getReceiver().getProfilePic()).placeholder(R.drawable.dummy).into(frnd_profile);
        }
    }

    public void idMappings() {
        frnd_listview = (ListView) findViewById(R.id.frnd_listview);
        lfrnd_chat = (LinearLayout) findViewById(R.id.lfrnd_chat);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        tprofile_name = (TextView) findViewById(R.id.tprofile_name);
        tcompany_name = (TextView) findViewById(R.id.tcompany_name);
        frnd_profile = (CircleImageView) findViewById(R.id.frnd_profile);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.menu);
        title.setVisibility(View.INVISIBLE);
    }

    public void listener() {
        lfrnd_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               /* Intent i = new Intent(FreindProfile.this,ChatBox.class);
//                i.putExtra("name",friendListdata.getReceiver().getName());
//                i.putExtra("Profile",friendListdata.getReceiver().getProfilePic());
//                startActivity(i);
//                finish();*/
//                i.putExtra("name",friendListdata.getReceiver().getName());
//               i.putExtra("Profile",friendListdata.getReceiver().getProfilePic());

                QBUser qbUser = new QBUser();
                qbUser.setEmail(friendListdata.getReceiver().getEmail());
                qbUser.setLogin(friendListdata.getReceiver().getName());
                Log.e("Friend Name : ", friendListdata.getReceiver().getName());
                qbUser.setId(Integer.parseInt(friendListdata.getReceiver().getQuickbloxId()));

                QBUser user = new QBUser();
                user.setEmail(sessionmanager.getValue(sessionmanager.Email));
                user.setLogin(sessionmanager.getValue(sessionmanager.Id));
                user.setId(Integer.parseInt(sessionmanager.getValue(sessionmanager.Quickbox_Id)));

                List<QBUser> selectedUsers = new ArrayList<QBUser>();
                selectedUsers.add(user);
                selectedUsers.add(qbUser);
                Log.e("consultant list", ":" + new Gson().toJson(selectedUsers));

                if (isPrivateDialogExist((ArrayList<QBUser>) selectedUsers)) {
                    selectedUsers.remove(ChatHelper.getCurrentUser());
                    QBChatDialog existingPrivateDialog = QbDialogHolder.getInstance().getPrivateDialogWithUser(selectedUsers.get(0), null);

                    ChatBox.startForResult(activity, REQUEST_DIALOG_ID_FOR_UPDATE, existingPrivateDialog,friendListdata.getReceiver().getName(),friendListdata.getReceiver().getProfilePic());
                    (activity).finish();
//           isProcessingResultInProgress = false;
                }
                else
                    {
                    createDialog(friendListdata);
                }
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FreindProfile.this, FriendList.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FreindProfile.this, FriendSetting.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(FreindProfile.this, FriendList.class);
        startActivity(i);
        finish();
    }

    private boolean isPrivateDialogExist(ArrayList<QBUser> allSelectedUsers) {
        ArrayList<QBUser> selectedUsers = new ArrayList<>();
        selectedUsers.addAll(allSelectedUsers);
        selectedUsers.remove(ChatHelper.getCurrentUser());
        boolean b = selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
        Log.e("selcteduser", ":" + b);
        return selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
    }


    private void createDialog(final FriendListDatum userDetail) {

        String receiver_id = friendListdata.getReceiver().getQuickbloxId() ;

        ArrayList<Integer> value = new ArrayList<Integer>();
        value.add(new Integer(receiver_id));

      /*  //for creating PRIVATE dialog
        QBChatDialog dialog1 = DialogUtils.buildPrivateDialog(receiver_id);
        dialog1.setPhoto(userDetail.getProfileImage());
        dialog1.setName(userDetail.getFullname());*/

        QBChatDialog dialog = new QBChatDialog();
//        dialog1.setName(tvGroupName.getText().toString().trim());
        if (userDetail.getReceiver().getProfilePic() != null) {
//            group_pic = getPath(selectedImageUri);
            dialog.setPhoto(userDetail.getReceiver().getProfilePic());
        }

        dialog.setType(QBDialogType.PRIVATE);
        dialog.setOccupantsIds(value);

        final StringBuilder occupantList = new StringBuilder();

        for (Integer integer : value) {
            occupantList.append(integer);
        }
        Log.e("occupantList", ":" + new Gson().toJson(occupantList));

        //for creating private dialog
        QBChatDialog dialog1 = DialogUtils.buildDialog(userDetail.getReceiver().getName(), QBDialogType.PRIVATE, value);

        QBRestChatService.createChatDialog(dialog1).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(final QBChatDialog result, Bundle params) {

                sharedPreferences.edit().putString(sessionmanager.dialogId, (String.valueOf(result.getDialogId()))).apply();
                Log.e("DIALOGID",sessionmanager.getValue(Sessionmanager.dialogId));

                String sender_id = sessionmanager.getValue(Sessionmanager.Id);
                String receiver_id = friendListdata.getReceiver().getId();
                String sender_qb_id = sessionmanager.getValue(Sessionmanager.Quickbox_Id);
                String receiver_qb_id = friendListdata.getReceiver().getQuickbloxId();
                String dialog_id = sessionmanager.getValue(Sessionmanager.dialogId);
                String group_name = sessionmanager.getValue(Sessionmanager.group_name);

//                ProgressDialogFragment.hide(context.get());
                QbDialogHolder.getInstance().addDialog(result);
                Log.e("Friendlist", ":" + new Gson().toJson(result));

                qbChatDialogs.add(result);

                QbDialogHolder.getInstance().addDialogs(qbChatDialogs);
                if (Helper.isConnectingToInternet(activity)) {
                    callApi_Chat(sender_id,receiver_id,sender_qb_id,receiver_qb_id,dialog_id, "private","1",group_name);
                }

                ChatBox.startForResult(activity, REQUEST_DIALOG_ID_FOR_UPDATE, result, friendListdata.getReceiver().getName(),friendListdata.getReceiver().getProfilePic());
                (activity).finish();
//                ProgressDialogFragment.hide(getChildFragmentManager());
            }

            @Override
            public void onError(QBResponseException responseException) {
                Helper.showToastMessage(activity, responseException.getMessage());

            }
        });
    }

    // TODO : CALL ADD CHAT API

    public void callApi_Chat(String sender_id, String receiver_id, String sender_qb_id, String receiver_qb_id, String dialog_id, String dialog_type, String dialog_status, String group_name) {
        Call<AddChat> chatCall = apiInterface.addchatpojo(sender_id, receiver_id, sender_qb_id, receiver_qb_id, dialog_id, dialog_type, dialog_status, group_name);

        chatCall.enqueue(new Callback<AddChat>() {
            @Override
            public void onResponse(Call<AddChat> call, Response<AddChat> response) {
                if (response.body().getStatus().equals("Success")) {

                    sessionmanager.createSession_addchat(response.body());
                    Log.e("receive data : ",":"+response.body());
//                    Toast.makeText(activity,new Gson().toJson(response.body()), Toast.LENGTH_LONG).show();
//                    Toast.makeText(activity,response.body().getData().getReceiver().getName(), Toast.LENGTH_LONG).show();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
                else
                    {
                    Toast.makeText(FreindProfile.this, "problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddChat> call, Throwable t) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
