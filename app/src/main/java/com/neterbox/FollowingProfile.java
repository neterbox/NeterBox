package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Followingpro_Adapter;

public class FollowingProfile extends AppCompatActivity {
    ListView following_listView;
    Followingpro_Adapter adapter;
    ImageView ileft,iright;
    TextView title;
    public FollowingProfile followingProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_profile);
        followingProfile = this;
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.menu);
        title.setText("Jane Wilson");


        Resources res =getResources();
        following_listView =(ListView)findViewById( R.id.following_listview );

        adapter=new Followingpro_Adapter(followingProfile);
        following_listView.setAdapter( adapter );
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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FollowingProfile.this,HomePage.class);
        startActivity(i);
        finish();
    }
}
