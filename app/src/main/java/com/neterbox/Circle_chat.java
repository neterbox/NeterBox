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
import com.neterbox.utils.Sessionmanager;

import de.hdodenhof.circleimageview.CircleImageView;

public class Circle_chat extends AppCompatActivity {

    ImageView ileft,ivActionImage;
    TextView title;
    Activity activity;
    String Loginname;
    Sessionmanager sessionmanager;
    CircleImageView image;

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

    public void idMappings()
    {
        ileft=(ImageView)findViewById(R.id.ileft);
        ivActionImage=(ImageView)findViewById(R.id.ivActionImage);
        title=(TextView) findViewById(R.id.title);
        image=(CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        ivActionImage.setImageResource(R.drawable.pencile);
    }

    public void Listner()
    {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Circle_chat.this,Circles.class);
                startActivity(i);
                finish();
            }
        });
    }
}
