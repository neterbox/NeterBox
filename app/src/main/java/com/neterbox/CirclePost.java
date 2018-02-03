package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CirclePost extends AppCompatActivity {


    ListView li_circle_post;
    LinearLayout lcirclestrip;
    ImageView ichatgreen,ichatyellow,icircle_video,iimage_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_post);


        ichatgreen = (ImageView) findViewById(R.id.ichatgreen);
        ichatyellow = (ImageView) findViewById(R.id.ichatyellow);
        icircle_video = (ImageView) findViewById(R.id.icircle_video);
        iimage_upload = (ImageView) findViewById(R.id.iimage_upload);
        li_circle_post = (ListView) findViewById(R.id.li_circle_post);
        lcirclestrip = (LinearLayout) findViewById(R.id.lcirclestrip);

        ichatgreen.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent i = new Intent(CirclePost.this, Circle_chat.class);
                                              startActivity(i);
                                              finish();
                                          }

                                      });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CirclePost.this, Circles.class);
        startActivity(i);
        finish();
    }
}
