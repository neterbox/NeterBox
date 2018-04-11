package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.Circle_post_Adapter;
import com.neterbox.customadapter.Followers_Adapter;
import com.neterbox.customadapter.Userpro_Adapter;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListDatum;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;
import com.neterbox.jsonpojo.get_profile.GetProfilePostdetail;
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

    ListView user_listview;
    Userpro_Adapter adapter;
    Activity activity;
    String index="1",user_id;
    TextView user_text,user_text2, tuser_followingno,tuser_friendcount,tuser_followersno,tuser_totalpostno;
    CircleImageView iuser_profile;
    List<GetProfilePostdetail> GetProfilePostdetail =new ArrayList<>();

    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        GetProfilePostdetail = new ArrayList<>();
        sessionmanager= new Sessionmanager(this);

        idmapping();
//        user_listview= ( ListView )findViewById( R.id.user_listview );
        user_id = sessionmanager.getValue(sessionmanager.Id);
        getprofile(index,user_id);

        if(!(user_text.getText().toString().equals(""))) {
            user_text.setText(sessionmanager.getValue(Sessionmanager.Name));
        }

        if(!(user_text2.getText().toString().equals(""))) {
            user_text2.setText(sessionmanager.getValue(Sessionmanager.Title));
        }

        if(!(tuser_followingno.getText().toString().equals(""))) {
            tuser_followingno.setText(sessionmanager.getValue(Sessionmanager.followingcount));
        }

        if(!(tuser_totalpostno.getText().toString().equals(""))) {
            tuser_totalpostno.setText(sessionmanager.getValue(Sessionmanager.postcount));
        }

        if(!(tuser_followersno.getText().toString().equals(""))) {
            tuser_followersno.setText(sessionmanager.getValue(Sessionmanager.followercount));
        }

        if(!(tuser_friendcount.getText().toString().equals(""))) {
            tuser_friendcount.setText(sessionmanager.getValue(Sessionmanager.profile_friend_count));
        }
        if(  sessionmanager.getValue(Sessionmanager.profile) != null)
        {
            Glide.with(activity).load(sessionmanager.getValue(Sessionmanager.profile)).placeholder(R.drawable.dummy).into(iuser_profile);
        }
    }

    public void idmapping(){
        user_text=(TextView)findViewById(R.id.user_text);
        user_text2=(TextView)findViewById(R.id.user_text2);
        iuser_profile=(CircleImageView) findViewById(R.id.iuser_profile);
        user_listview=(ListView) findViewById(R.id.user_listview);
        tuser_followingno=(TextView)findViewById(R.id.tuser_followingno);
        tuser_friendcount=(TextView)findViewById(R.id.tuser_friendcount);
        tuser_followersno=(TextView)findViewById(R.id.tuser_followersno);
        tuser_totalpostno=(TextView)findViewById(R.id.tuser_totalpostno);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(UserProfile.this,HomePage.class);
        startActivity(i);
        finish();
    }

/*TODO get profile API*/

    public void getprofile(String index,String user_id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        final Call<GetProfile> getProfileCall = apiInterface.getprofilepojo(index, user_id);
        getProfileCall.enqueue(new Callback<GetProfile>()
        {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response)
            {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {
                    GetProfile getProfile = new GetProfile();
                    getProfile = response.body();
                    sessionmanager.createSession_userprofile(getProfile);
                    GetProfilePostdetail = new ArrayList<GetProfilePostdetail>();
                    GetProfilePostdetail = response.body().getData().getUser().getPosetdetail();
                    Log.e("Get Profile data", new Gson().toJson(GetProfilePostdetail));
                    Userpro_Adapter adapter = new Userpro_Adapter(activity, GetProfilePostdetail);
                    user_listview.setAdapter(adapter);
                }
                else
                {
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