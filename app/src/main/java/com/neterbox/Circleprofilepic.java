package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Circleprofilepic extends AppCompatActivity {

    ImageView icirclepicopen;
    TextView tcircleslikeno;
    String pic,like;
    LinearLayout lcpcomment;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleprofilepic);
        activity = this;

        pic = getIntent().getStringExtra("pic");
        like = getIntent().getStringExtra("like");

        IdMapping();
        Listeners();

        tcircleslikeno.setText(like);
        Glide.with(activity).load(pic).placeholder(R.drawable.dummy).into(icirclepicopen);

    }

    private void Listeners() {
        lcpcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Circleprofilepic.this,Circle_comment.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void IdMapping() {
        lcpcomment=(LinearLayout) findViewById(R.id.lcpcomment);
        icirclepicopen=(ImageView) findViewById(R.id.icirclepicopen);
        tcircleslikeno=(TextView) findViewById(R.id.tcircleslikeno);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circleprofilepic.this,CirclePost.class);
        startActivity(i);
        finish();
    }
}