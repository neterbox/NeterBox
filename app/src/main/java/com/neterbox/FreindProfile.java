package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;
import com.neterbox.customadapter.Userpro_Adapter;

import static com.neterbox.R.id.listview;

public class FreindProfile extends Activity{

    ListView frnd_listview;
    LinearLayout lfrnd_chat;
    ImageView ileft,iright;
    TextView title;
    Friendpro_Adapter adapter;
    Activity activity ;
    String[] itemname ={
            "Charmis",
            "sejal"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
    };
    String[] itemname1 ={
            "20",
            "30"
    };

    Integer[] imgid1={
            R.drawable.pic3,
            R.drawable.pic4,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind_profile);

        activity = this;

        frnd_listview=(ListView)findViewById(R.id.frnd_listview);
        lfrnd_chat = (LinearLayout) findViewById(R.id.lfrnd_chat);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.playdot);
        title.setText("Jane Wilson");

        Friendpro_Adapter adapter = new Friendpro_Adapter(this, itemname, imgid, itemname1, imgid1);
        frnd_listview.setAdapter(adapter);

        lfrnd_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FreindProfile.this, One_To_OneChat.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FreindProfile.this,FriendList.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FreindProfile.this,FriendSetting.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FreindProfile.this,SearchGroupFriend.class);
        startActivity(i);
        finish();
        finish();

    }
}
