package com.neterbox;

import android.app.Activity;
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
import com.neterbox.utils.Sessionmanager;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    ListView user_listview;
    Userpro_Adapter adapter;
    Activity activity;
    String Loginname;
    TextView user_text,user_text2;
    CircleImageView iuser_profile;
    Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        sessionmanager= new Sessionmanager(this);
        idmapping();
        user_listview= ( ListView )findViewById( R.id.user_listview );

        adapter=new Userpro_Adapter(activity);
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
}

