package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.fragment.MainChat;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.utils.Sessionmanager;

import de.hdodenhof.circleimageview.CircleImageView;

public class FreindProfile extends Activity{

    Sessionmanager sessionmanager;
    ListView frnd_listview;
    LinearLayout lfrnd_chat;
    ImageView ileft,iright;
    TextView title,tprofile_name,tcompany_name;
    Friendpro_Adapter adapter;
    Activity activity ;

    CircleImageView frnd_profile;
    FriendListDatum friendListdata = new FriendListDatum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind_profile);

        activity = this;
        friendListdata = (FriendListDatum) getIntent().getSerializableExtra("friendlist");
        sessionmanager = new Sessionmanager(this);
        idMappings();
        listener();

        Friendpro_Adapter adapter = new Friendpro_Adapter(activity);
        frnd_listview.setAdapter(adapter);

        if(friendListdata!=null)
        {
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
                Intent i = new Intent(FreindProfile.this, MainChat.class);
                startActivity(i);
                finish();
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
        Intent i=new Intent(FreindProfile.this,FriendList.class);
        startActivity(i);
        finish();
    }
}
