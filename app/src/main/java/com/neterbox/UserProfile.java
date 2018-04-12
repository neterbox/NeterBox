package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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

public class UserProfile extends AppCompatActivity {

    public static ListView user_listview;
    public static Userpro_Adapter adapter;
    Activity activity;
    String index = "1", user_id;
    TextView user_text, user_text2, tuser_followingno, tuser_friendcount, tuser_followersno, tuser_totalpostno;
    CircleImageView iuser_profile;
    private static List<GetProfileUser> GetProfilePostdetail = new ArrayList<>();
    private static List<GetProfilePostdetail> profilePostdetails = new ArrayList<>();
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    int  getprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        sessionmanager = new Sessionmanager(this);

        idmapping();

        user_id = sessionmanager.getValue(sessionmanager.Id);
        getprofile(index, user_id);
    }

    public void idmapping() {
        user_text = (TextView) findViewById(R.id.user_text);
        user_text2 = (TextView) findViewById(R.id.user_text2);
        iuser_profile = (CircleImageView) findViewById(R.id.iuser_profile);
        user_listview = (ListView) findViewById(R.id.user_listview);
        tuser_followingno = (TextView) findViewById(R.id.tuser_followingno);
        tuser_friendcount = (TextView) findViewById(R.id.tuser_friendcount);
        tuser_followersno = (TextView) findViewById(R.id.tuser_followersno);
        tuser_totalpostno = (TextView) findViewById(R.id.tuser_totalpostno);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UserProfile.this, HomePage.class);
        startActivity(i);
        finish();
    }

    //TODo data set
    private void setData(List<GetProfileUser> getProfilePostdetail, int total) {
        if (!sessionmanager.getValue(Sessionmanager.Username).equalsIgnoreCase("")) {
            user_text.setText(sessionmanager.getValue(Sessionmanager.Username));
        }

        if (!sessionmanager.getValue(Sessionmanager.Title).equalsIgnoreCase("")) {
            user_text2.setText(sessionmanager.getValue(Sessionmanager.Title));
        }


        if (!getProfilePostdetail.get(0).getFollowingCount().equals("")) {
            tuser_followingno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowingCount()));
        }

        if (!getProfilePostdetail.get(0).getFollowerCount().equals("")) {
            tuser_followersno.setText(String.valueOf(getProfilePostdetail.get(0).getFollowerCount()));
        }

        if (!getProfilePostdetail.get(0).getFriendCount().equals("")) {
            tuser_friendcount.setText(String.valueOf(getProfilePostdetail.get(0).getFriendCount()));
        }

            tuser_totalpostno.setText(String.valueOf(getprofile));

        if (sessionmanager.getValue(Sessionmanager.profile) != null) {
            Glide.with(activity).load(sessionmanager.getValue(Sessionmanager.profile)).placeholder(R.drawable.dummy).into(iuser_profile);
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
                    adapter = new Userpro_Adapter(activity, profilePostdetails);
                    user_listview.setAdapter(adapter);

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(UserProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}