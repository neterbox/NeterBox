package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Followingpro_Adapter;
import com.neterbox.customadapter.Userpro_Adapter;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfilePostdetail;
import com.neterbox.jsonpojo.get_profile.GetProfileUser;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.utils.Sessionmanager.index;

public class FollowingProfile extends AppCompatActivity {

    public static ListView following_listView;
    public static Followingpro_Adapter adapter;

    Activity activity;
    ImageView ileft,iright;
    TextView tfriendTitle,tfriend_name;
    public FollowingProfile followingProfile ;
    String index = "1", user_id;

    TextView  tfollowing_followingno, tfollowing_following,tfollowing_followersno, tfollowing_followers, tfollowing_friendcountno
            ,tfollowing_Friend, tfollowing_totalpostno, tfollowing_totalpost;
    CircleImageView cifollowing_profile;
    private static List<GetProfileUser> GetProfilePostdetail = new ArrayList<>();
    private static List<GetProfilePostdetail> profilePostdetails = new ArrayList<>();
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    int  getprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_profile);

        sessionmanager = new Sessionmanager(this);

        idmapping();
        listener();

        user_id = sessionmanager.getValue(sessionmanager.Id);
        getprofile(index, user_id);

        activity = this;

        Resources res =getResources();
        following_listView =(ListView)findViewById( R.id.following_listview );

    }

    private void listener() {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FollowingProfile.this,SearchFollowings.class);
                startActivity(i);
                finish();

            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FollowingProfile.this,FriendSetting.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void idmapping() {
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        tfriendTitle=(TextView)findViewById(R.id.tfriendTitle);
        tfriend_name=(TextView)findViewById(R.id.tfriend_name);
        tfollowing_followingno=(TextView)findViewById(R.id.tfollowing_followingno);
        tfollowing_following=(TextView)findViewById(R.id.tfollowing_following);
        tfollowing_followersno=(TextView)findViewById(R.id.tfollowing_followersno);
        tfollowing_followers=(TextView)findViewById(R.id.tfollowing_followers);
        tfollowing_friendcountno=(TextView)findViewById(R.id.tfollowing_friendcountno);
        tfollowing_Friend=(TextView)findViewById(R.id.tfollowing_Friend);
        tfollowing_totalpostno=(TextView)findViewById(R.id.tfollowing_totalpostno);
        tfollowing_totalpost=(TextView)findViewById(R.id.tfollowing_totalpost);

        cifollowing_profile=(CircleImageView) findViewById(R.id.cifollowing_profile);

        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.menu);
//        title.setText("Jane Wilson");
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FollowingProfile.this,HomePage.class);
        startActivity(i);
        finish();
    }
    //TODo data set
    private void setData(List<GetProfileUser> getProfilePostdetail, int total) {
        if (!sessionmanager.getValue(Sessionmanager.follower_name).equalsIgnoreCase("")) {
            tfriend_name.setText(sessionmanager.getValue(Sessionmanager.follower_name));
        }

        if (!sessionmanager.getValue(Sessionmanager.follower_title).equalsIgnoreCase("")) {
            tfriendTitle.setText(sessionmanager.getValue(Sessionmanager.follower_title));
        }


        if (!getProfilePostdetail.get(0).getFollowingCount().equals("")) {
            tfollowing_followingno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowingCount()));
        }

        if (!getProfilePostdetail.get(0).getFollowerCount().equals("")) {
            tfollowing_followersno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowerCount()));
        }

        if (!getProfilePostdetail.get(0).getFriendCount().equals("")) {
            tfollowing_friendcountno.setText(String.valueOf(getProfilePostdetail.get(0).getFriendCount()));
        }

        tfollowing_totalpostno.setText(String.valueOf(getprofile));

        if (sessionmanager.getValue(Sessionmanager.profilefollower) != null) {
            Glide.with(activity).load(sessionmanager.getValue(Sessionmanager.profilefollower)).placeholder(R.drawable.dummy).into(cifollowing_profile);
        }
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

                    profilePostdetails.addAll(GetProfilePostdetail.get(0).getPosetdetail());
                    adapter = new Followingpro_Adapter(activity, profilePostdetails);
                    following_listView.setAdapter(adapter);

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(FollowingProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
