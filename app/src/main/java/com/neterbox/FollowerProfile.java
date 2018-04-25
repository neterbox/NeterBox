package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.gson.Gson;
import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.customadapter.Userpro_Adapter;
import com.neterbox.jsonpojo.cancel_friend_request.CancelFriendRequest;
import com.neterbox.jsonpojo.followingadd.FollowerDetails;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;
import com.neterbox.jsonpojo.get_profile.GetProfilePostdetail;
import com.neterbox.jsonpojo.get_profile.GetProfileUser;
import com.neterbox.jsonpojo.get_profile.GetProfile_0;
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
import static com.neterbox.utils.Sessionmanager.follower_pic;
import static com.neterbox.utils.Sessionmanager.index;

public class FollowerProfile extends AppCompatActivity {

    public static ListView follower_listview;
    LinearLayout lfollower_addfrnd;
    TextView tfollower_followingno, tfollower_followersno, tfollower_friendcount, tfollower_totalpostno;

    public static Followerpro_Adapter adapter;
    ImageView ileft,iright;
    TextView title,tprofile_name,tcompany_name;
    Activity activity;
    String sender_id, receiver_id,req_receiver_id,id;
    Sessionmanager sessionmanager;
    String Profilename;
    String index = "1", user_id;
    public static String name;
    public static String followerprofilepic;
    FollowerDetails followerDetails = new FollowerDetails();

    int  getprofile;
    private static List<GetProfileUser> GetProfilePostdetail = new ArrayList<>();
    private static List<GetProfilePostdetail> profilePostdetails = new ArrayList<>();

    CircleImageView ifollowerprofile;
    NearbyfriendDatum nearbyfriendData = new NearbyfriendDatum();
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_profile);
        activity = this;
        sessionmanager = new Sessionmanager(this);
        nearbyfriendData = (NearbyfriendDatum) getIntent().getSerializableExtra("profile");
        user_id= sessionmanager.getValue(Sessionmanager.following_id);
        idMappings();
        listener();

        name = getIntent().getStringExtra("name");
        String followerprofilepic = getIntent().getStringExtra("profile_pic");

        if (followerDetails != null) {
            tprofile_name.setText(name);
            Glide.with(activity).load(followerprofilepic).placeholder(R.drawable.dummy).into(ifollowerprofile);
        }
        user_id = sessionmanager.getValue(sessionmanager.following_id);
        getprofile(index, user_id);

        sender_id = sessionmanager.getValue(Sessionmanager.Id);
        receiver_id = sessionmanager.getValue(Sessionmanager.nearbyuserId);
        req_receiver_id = sessionmanager.getValue(Sessionmanager.frndrecId);

        if(receiver_id.equalsIgnoreCase(req_receiver_id)){
            lfollower_addfrnd.setBackgroundColor(Color.GRAY);
            lfollower_addfrnd.setEnabled(false);
        }
        else {
            lfollower_addfrnd.setBackgroundColor(getResources().getColor(R.color.greenbox));
            lfollower_addfrnd.setEnabled(true);
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
        ifollowerprofile=(CircleImageView)findViewById(R.id.ifollowerprofile);
        lfollower_addfrnd=(LinearLayout)findViewById(R.id.lfollower_addfrnd);
        follower_listview =(ListView)findViewById( R.id.follower_listview );
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        tfollower_followingno=(TextView)findViewById(R.id.tfollower_followingno);
        tfollower_followersno=(TextView)findViewById(R.id.tfollower_followersno);
        tfollower_friendcount=(TextView)findViewById(R.id.tfollower_friendcount);
        tfollower_totalpostno=(TextView)findViewById(R.id.tfollower_totalpostno);
        title=(TextView)findViewById(R.id.title);
        tprofile_name=(TextView)findViewById(R.id.tprofile_name);
        tcompany_name=(TextView)findViewById(R.id.tcompany_name);
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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FollowerProfile.this,HomePage.class);
        startActivity(i);
        finish();
    }


    //TODo data set
    private void setData(List<GetProfileUser> getProfilePostdetail, int total) {

        if (!sessionmanager.getValue(Sessionmanager.following_title).equalsIgnoreCase("")) {
            tcompany_name.setText(sessionmanager.getValue(Sessionmanager.following_title));
        }

        if (!getProfilePostdetail.get(0).getFollowingCount().equals("")) {
            tfollower_followingno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowingCount()));
        }

        if (!getProfilePostdetail.get(0).getFollowerCount().equals("")) {
            tfollower_followersno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowerCount()));
        }

        if (!getProfilePostdetail.get(0).getFriendCount().equals("")) {
            tfollower_friendcount.setText(String.valueOf(getProfilePostdetail.get(0).getFriendCount()));
        }

        tfollower_totalpostno.setText(String.valueOf(getprofile));

    }


    /*TODO get profile API*/

    public void getprofile(String index, String user_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        final Call<GetProfile> getProfileCall = apiInterface.getprofilepojo(index, user_id);
        getProfileCall.enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {

                    getprofile = response.body().getTotalPostcount();
                    Log.e("TOTAL", new Gson().toJson(getprofile));
                    GetProfilePostdetail.add(response.body().getData().getUser());
                    Log.e("Get Profile data", new Gson().toJson(GetProfilePostdetail));

                    int total = response.body().getTotalPostcount();
                    setData(GetProfilePostdetail, total);


//                    profilePostdetails=new GetProfilePostdetail();
                    profilePostdetails= response.body().getData().getUser().getPosetdetail();
//                    profilePostdetails.addAll(GetProfilePostdetail.get(0).get);
//
                    adapter = new Followerpro_Adapter(activity, profilePostdetails);
                    follower_listview.setAdapter(adapter);

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(FollowerProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}