package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Friendpro_Adapter;

public class FollowerProfile extends AppCompatActivity {

    ListView follower_listview;
    LinearLayout lfollower_addfrnd;
    Followerpro_Adapter adapter;
    public FollowerProfile followerProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_profile);


        followerProfile = this;

        Resources res =getResources();
        lfollower_addfrnd=(LinearLayout)findViewById(R.id.lfollower_addfrnd);
        follower_listview =(ListView)findViewById( R.id.follower_listview );

        adapter=new Followerpro_Adapter(followerProfile);
        follower_listview.setAdapter( adapter );
//        follower_listview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(FollowerProfile.this,FriendList.class);
//                startActivity(i);
//            }
//        });

    }
}
