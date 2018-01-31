package com.neterbox;

import android.app.Activity;
import android.content.Intent;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.jsonpojo.circle.CircleListDatum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Circle_chat extends AppCompatActivity {

    ImageView ileft, iright;
    Activity activity;
    TextView title;
    CircleImageView image;
    CircleListDatum circleListData = new CircleListDatum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);

        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        image = (CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.pencile);
        image.setImageResource(R.drawable.pic4);

        this.circleListData = circleListData;


        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Circle_chat.this, Circles.class);
                circleListData = (CircleListDatum) getIntent().getSerializableExtra("circledataextra");
                startActivity(i);
                finish();
            }
        });
        if (circleListData != null) {
            title.setText(circleListData.getCircle().getName());
            Glide.with(activity).load(circleListData.getCircle().getFiles()).placeholder(R.drawable.dummy).into(image);
        }
//        if(circleListData.getCircle().getName().equals("")){
//            title.setText(circleListData.getCircle().getName());
//        }
//        if(circleListData.getCircle().getFiles().equals("")){
//
//        }
    }
        @Override
        public void onBackPressed () {
            Intent i = new Intent(Circle_chat.this, Circles.class);
            startActivity(i);
            finish();
        }
}