package com.neterbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchGroupFriend extends AppCompatActivity {
LinearLayout lnearby,laddtofollower;
    ImageView ileft,iright;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_friend);

        laddtofollower=(LinearLayout)findViewById(R.id.laddtofollower);
        lnearby=(LinearLayout)findViewById(R.id.lnearby);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Find Friends");

        lnearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,FriendList.class);
                startActivity(i);
                finish();
            }
        });

        laddtofollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,search_followers.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SearchGroupFriend.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

    }
}
