package com.neterbox;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Followingpro_Adapter;

public class FollowingProfile extends AppCompatActivity {
    ListView following_listView;
    Followingpro_Adapter adapter;
    public FollowingProfile followingProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_profile);
        followingProfile = this;

        Resources res =getResources();
        following_listView =(ListView)findViewById( R.id.following_listview );

        adapter=new Followingpro_Adapter(followingProfile);
        following_listView.setAdapter( adapter );
    }
}
