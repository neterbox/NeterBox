package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.customadapter.Userpro_Adapter;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;
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
    TextView user_text,user_text2;
    CircleImageView iuser_profile;
    List<GetProfileDatum> getProfileDatumList;
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        getProfileDatumList = new ArrayList<>();
        sessionmanager= new Sessionmanager(this);

        idmapping();
        user_listview= ( ListView )findViewById( R.id.user_listview );
        user_id = sessionmanager.getValue(sessionmanager.Id);
        getprofile(index,user_id);
        adapter=new Userpro_Adapter(activity,getProfileDatumList);
        user_listview.setAdapter( adapter );

        if(!(user_text.getText().toString().equals(""))) {
            user_text.setText(sessionmanager.getValue(Sessionmanager.Name));
        }

        if(!(user_text2.getText().toString().equals(""))) {
            user_text2.setText(sessionmanager.getValue(Sessionmanager.Title));
        }

        if(  sessionmanager.getValue(Sessionmanager.profile) != null)
        {
            Glide.with(activity).load(new Sessionmanager(activity).getValue(Sessionmanager.profile)).placeholder(R.drawable.dummy).into(iuser_profile);

        }
    }

    public void idmapping(){
        user_text=(TextView)findViewById(R.id.user_text);
        user_text2=(TextView)findViewById(R.id.user_text2);
        iuser_profile=(CircleImageView) findViewById(R.id.iuser_profile);
        user_listview=(ListView) findViewById(R.id.user_listview);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(UserProfile.this,HomePage.class);
        startActivity(i);
        finish();
    }
    public void getprofile(String index,String user_id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<GetProfile> getProfileCall = apiInterface.getprofilepojo(index, user_id);
        getProfileCall.enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response)
            {
                dialog.dismiss();
                if(response.body().getStatus().equalsIgnoreCase("Success")){
                    sessionmanager.createSession_userprofile((response.body().getData()));

                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
}
