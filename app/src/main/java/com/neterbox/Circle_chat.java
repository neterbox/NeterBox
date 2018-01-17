package com.neterbox;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.media.Image;
>>>>>>> 56f18088548bbf6bcdd0f66e634b03ea75312377
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Circle_chat extends AppCompatActivity {

ImageView ileft,iright;
    TextView title;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);

        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView) findViewById(R.id.title);
        image=(CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.pencile);
        image.setImageResource(R.drawable.pic4);
        title.setText("Travel");


        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Circle_chat.this,Circles.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_chat.this,HomePage.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_chat.this,Circles.class);
        startActivity(i);
        finish();
    }
}
