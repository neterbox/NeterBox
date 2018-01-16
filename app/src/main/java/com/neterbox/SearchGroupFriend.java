package com.neterbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SearchGroupFriend extends AppCompatActivity {
LinearLayout lnearby,laddtofollower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_friend);

        laddtofollower=(LinearLayout)findViewById(R.id.laddtofollower);
        lnearby=(LinearLayout)findViewById(R.id.lnearby);

        lnearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,FriendList.class);
                startActivity(i);
            }
        });

        laddtofollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,search_followers.class);
                startActivity(i);
            }
        });

    }
}
