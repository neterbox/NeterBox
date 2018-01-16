package com.neterbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    TextView taddfriend,tlogout;
    LinearLayout lph;
    RelativeLayout relative_following,relative_follower,relative_frnd,relative_settings;
    ImageView iback1,iback2,iback3,iback4,ichat,icircle,iplay,profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        taddfriend=(TextView)findViewById(R.id.taddfriend);
        lph=(LinearLayout)findViewById(R.id.lph);
        iback1=(ImageView)findViewById(R.id.iback1);
        iback2=(ImageView)findViewById(R.id.iback2);
        iback3=(ImageView)findViewById(R.id.iback3);
        iback4=(ImageView)findViewById(R.id.iback4);
        tlogout=(TextView)findViewById(R.id.tlogout);
        relative_following=(RelativeLayout)findViewById(R.id.relative_following);
        relative_follower=(RelativeLayout)findViewById(R.id.relative_follower);
        relative_frnd=(RelativeLayout)findViewById(R.id.relative_frnd);
        relative_settings=(RelativeLayout)findViewById(R.id.relative_settings);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        ichat=(ImageView)findViewById(R.id.ichat);
        icircle=(ImageView)findViewById(R.id.icircle);
        iplay=(ImageView)findViewById(R.id.iplay);

       profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,EditProfile.class);
                startActivity(i);
            }
        });

        lph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,UserProfile.class);
                startActivity(i);
            }
        });

        taddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,SearchGroupFriend.class);
                startActivity(i);
            }
        });


        relative_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i =new Intent(HomePage.this,FriendList.class);
                startActivity(i);
            }
        });

        relative_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,SearchFollowings.class);
                startActivity(i);
            }
        });

        relative_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,search_followers.class);
                startActivity(i);
            }
        });

        relative_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,Settings.class);
                startActivity(i);
            }
        });

        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,LoginPage.class);
                startActivity(i);
            }
        });

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,Contactsforoneononechat.class);
                startActivity(i);
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,Circles.class);
                startActivity(i);
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePage.this,PlayGridview.class);
                startActivity(i);
            }
        });
    }
}
