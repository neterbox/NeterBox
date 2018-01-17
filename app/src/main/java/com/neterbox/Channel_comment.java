package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Channel_comment extends AppCompatActivity {

    ImageView backblue;
    public Channel_comment channelComment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_comment);

//        backblue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Channel_comment.this, Playvideo_page.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        Intent i=new Intent(Channel_comment.this,Playvideo_page.class);
        startActivity(i);
        finish();
    }
}
