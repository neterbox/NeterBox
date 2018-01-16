package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neterbox.customadapter.Circle_imageAdapter;
import com.neterbox.customadapter.Followerpro_Adapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircleImageview extends AppCompatActivity {

    ListView circleimageview;
    LinearLayout llistview_comment, llistview_likes,llistview_following,llistview_channel;
    CircleImageView icircleprofile;
    Circle_imageAdapter adapter;
    public CircleImageview circleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_imageview);

        circleview = this;

        Resources res =getResources();
        circleimageview =(ListView)findViewById( R.id.circleimageview);
        icircleprofile=(CircleImageView)findViewById(R.id.icircleprofile);
        llistview_comment = (LinearLayout)findViewById(R.id.llistview_comment);
        llistview_likes = (LinearLayout)findViewById(R.id.llistview_likes);
       llistview_following = (LinearLayout)findViewById(R.id.llistview_following);
       llistview_channel = (LinearLayout)findViewById(R.id.llistview_channel);

        adapter=new Circle_imageAdapter(circleview);
        circleimageview.setAdapter( adapter );

        icircleprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CircleImageview.this,CircleImageView.class);
            }
        });

        circleimageview.setAdapter( adapter );

        icircleprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CircleImageview.this,Circle_comment.class);
            }
        });

        llistview_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CircleImageview.this,Circle_comment.class);
            }
        });

        llistview_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CircleImageview.this,Playvideo_page.class);
            }
        });

    }
}