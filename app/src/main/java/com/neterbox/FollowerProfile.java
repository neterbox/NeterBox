package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.jsonpojo.cancel_friend_request.CancelFriendRequest;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.jsonpojo.sendfriendrequest.SendRequest;
import com.neterbox.jsonpojo.sendfriendrequest.SendRequestReceiver;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.R.drawable.greenbox;

public class FollowerProfile extends AppCompatActivity {

    ListView follower_listview;
    LinearLayout lfollower_addfrnd;
    Followerpro_Adapter adapter;
    ImageView ileft,iright;
    TextView title,tprofile_name;
    Activity activity;
    String sender_id, receiver_id,req_receiver_id,id;
    Sessionmanager sessionmanager;
    String Profilename;

    CircleImageView followerprofile;
    NearbyfriendDatum nearbyfriendData = new NearbyfriendDatum();
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_profile);
        activity = this;
        sessionmanager = new Sessionmanager(this);
        nearbyfriendData = (NearbyfriendDatum) getIntent().getSerializableExtra("profile");
        idMappings();
        listener();

        sender_id = sessionmanager.getValue(Sessionmanager.Id);
//        receiver_id = nearbyfriendData.getUsers().getId();
        receiver_id = sessionmanager.getValue(Sessionmanager.nearbyId);
        req_receiver_id = sessionmanager.getValue(Sessionmanager.req_receiverId);

        adapter = new Followerpro_Adapter(activity);
        follower_listview.setAdapter(adapter);

        if(receiver_id.equals(req_receiver_id)){
            lfollower_addfrnd.setBackgroundColor(Color.GRAY);
            lfollower_addfrnd.setEnabled(true);
        }
        else {
            lfollower_addfrnd.setBackgroundColor(getResources().getColor(R.color.greenbox));
            lfollower_addfrnd.setEnabled(true);
        }

        if(nearbyfriendData!=null)
        {
            tprofile_name.setText(nearbyfriendData.getUsers().getName());
            Glide.with(activity).load(nearbyfriendData.getUsers().getProfilePic()).placeholder(R.drawable.dummy).into(followerprofile);
        }
    }

//        follower_listview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(FollowerProfile.this,FriendListPojo.class);
//                startActivity(i);
//            }
//        });

    public void idMappings(){
        followerprofile=(CircleImageView)findViewById(R.id.followerprofile);
        lfollower_addfrnd=(LinearLayout)findViewById(R.id.lfollower_addfrnd);
        follower_listview =(ListView)findViewById( R.id.follower_listview );
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        tprofile_name=(TextView)findViewById(R.id.tprofile_name);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.menu);
        title.setVisibility(View.INVISIBLE);

    }

    public void listener() {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FollowerProfile.this, search_followers.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FollowerProfile.this, FriendSetting.class);
                startActivity(i);
                finish();
            }
        });
        lfollower_addfrnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.isConnectingToInternet(activity)){
                    sendrequest(sender_id, receiver_id);
                }
                else {
                    Helper.showToastMessage(activity,"No Internet Connection");
                }
                id = sessionmanager.getValue(Sessionmanager.req_friendId);
//                cancelrequest(id,sender_id);
                Log.e("------Sender_Id----- ",sender_id);
                Log.e("------Receiver_Id------",receiver_id);
            }
        });
    }
    public void sendrequest(final String sender_id, String receiver_id) {
        Call<SendRequest> sendRequestCall = apiInterface.sendrequestpojo(sender_id, receiver_id);
        sendRequestCall.enqueue(new Callback<SendRequest>() {
            @Override
            public void onResponse(Call<SendRequest> call, Response<SendRequest> response) {
                if (response.body().getStatus().equals("Success")) {
                    lfollower_addfrnd.setBackgroundColor(Color.GRAY);
                    lfollower_addfrnd.setEnabled(false);
                    sessionmanager.send_request((response.body().getData()));
                    Toast.makeText(FollowerProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendRequest> call, Throwable t) {
            }
        });
    }

//    public void cancelrequest(String id,String sender_id)
//    {
//        Call<CancelFriendRequest> cancelFriendRequestCall = apiInterface.cancelrequestpojo(id,sender_id);
//        cancelFriendRequestCall.enqueue(new Callback<CancelFriendRequest>() {
//            @Override
//            public void onResponse(Call<CancelFriendRequest> call, Response<CancelFriendRequest> response) {
//                if (response.body().getStatus().equals("Success")) {
//                    lfollower_addfrnd.setBackgroundColor(getResources().getColor(R.color.greenbox));
//                    lfollower_addfrnd.setEnabled(true);
//                    Toast.makeText(FollowerProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(activity, "Please Try Again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CancelFriendRequest> call, Throwable t) {
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FollowerProfile.this,HomePage.class);
        startActivity(i);
        finish();

    }
}