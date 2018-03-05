package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.NearbyfriendAdapter;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NearbyFriendlist extends Activity {
    ListView listview;
    LinearLayout lfrndprofile;
    Activity activity;
    ImageView ileft, iright;
    TextView title;
    List<NearbyfriendDatum> nearbyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_friend_list);
        activity = this;

        nearbyList = (List<NearbyfriendDatum>) getIntent().getSerializableExtra("nearby_data");
        idMappings();
        listener();
        NearbyfriendAdapter adapter = new NearbyfriendAdapter(this, nearbyList);
        listview.setAdapter(adapter);
    }

    private void listener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(NearbyFriendlist.this, FollowerProfile.class);
                it.putExtra("profile", (Serializable)nearbyList.get(i));
//                it.putExtra("profile",(Serializable) response.body().getData().getReceiver());
                startActivity(it);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NearbyFriendlist.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NearbyFriendlist.this, SearchGroupFriend.class);
        startActivity(i);
        finish();
    }

    private void idMappings() {
        listview = (ListView) findViewById(R.id.listview);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Nearby Friend");
    }

}