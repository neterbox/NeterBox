package com.neterbox;

import android.app.Activity;
import android.content.Intent;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
<<<<<<< HEAD
import com.neterbox.utils.Sessionmanager;
=======
import com.google.gson.Gson;
import com.neterbox.jsonpojo.circle.CircleListDatum;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 7a229364e04a7f07edcebd5859c752759a0f714d

import de.hdodenhof.circleimageview.CircleImageView;

public class Circle_chat extends AppCompatActivity {

    ImageView ileft, iright;
    Activity activity;
    TextView title;
    Activity activity;
    String Loginname;
    Sessionmanager sessionmanager;
    CircleImageView image;
    CircleListDatum circleListData;
//    List<CircleListDatum> circlelistdata = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);
        activity=this;
        idMappings();
        Listner();
        sessionmanager = new Sessionmanager(this);
        Loginname = sessionmanager.getValue(Sessionmanager.CircleName);
        if(!(title.getText().toString().equals(""))) {
            title.setText(Loginname);
        }
        if(  new Sessionmanager(activity).getValue(Sessionmanager.Files) != null)
        {
            Glide.with(activity).load(new Sessionmanager(activity).getValue(Sessionmanager.Files)).placeholder(R.drawable.dummy).into(image);

        }

    }
       @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_chat.this,CirclePost.class);
        startActivity(i);
        finish();
    }

<<<<<<< HEAD
    public void idMappings()
    {
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView) findViewById(R.id.title);
        image=(CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.pencile);
    }
=======
        activity=this;

        circleListData= new CircleListDatum();
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        image = (CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.pencile);
        circleListData = (CircleListDatum)getIntent().getSerializableExtra("circledataextra");

        Log.e("data",":"+new Gson().toJson(circleListData));
>>>>>>> 7a229364e04a7f07edcebd5859c752759a0f714d

    public void Listner()
    {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Circle_chat.this, Circles.class);
                startActivity(i);
                finish();
            }
        });
        if (circleListData != null) {
            title.setText(circleListData.getCircle().getName());
            Glide.with(activity).load(circleListData.getCircle().getFiles()).placeholder(R.drawable.dummy).into(image);
        }
    }
<<<<<<< HEAD
}
=======
        @Override
        public void onBackPressed () {
            Intent i = new Intent(Circle_chat.this, Circles.class);
            startActivity(i);
            finish();
        }
}
>>>>>>> 7a229364e04a7f07edcebd5859c752759a0f714d
