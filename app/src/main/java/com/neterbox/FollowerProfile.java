package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Friendpro_Adapter;

public class FollowerProfile extends AppCompatActivity {

    ListView follower_listview;
    LinearLayout lfollower_addfrnd;
    Followerpro_Adapter adapter;
    ImageView ileft,iright;
    TextView title;
    public FollowerProfile followerProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_profile);


        followerProfile = this;

        Resources res =getResources();
        lfollower_addfrnd=(LinearLayout)findViewById(R.id.lfollower_addfrnd);
        follower_listview =(ListView)findViewById( R.id.follower_listview );
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.playdot);
        title.setText("Jane Wilson");

        adapter=new Followerpro_Adapter(followerProfile);
        follower_listview.setAdapter( adapter );

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FollowerProfile.this,search_followers.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FollowerProfile.this,FriendSetting.class);
                startActivity(i);
                finish();
            }
        });
//        follower_listview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(FollowerProfile.this,FriendList.class);
//                startActivity(i);
//            }
//        });



    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FollowerProfile.this,search_followers.class);
        startActivity(i);
        finish();

    }
}
