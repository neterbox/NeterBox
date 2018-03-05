package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class One_To_OneChat extends AppCompatActivity {
    ImageView ileft,iright;
    TextView title;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one__to__one_chat);

        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView) findViewById(R.id.title);
        image=(CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setVisibility(View.INVISIBLE);
        image.setImageResource(R.drawable.pic4);

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(One_To_OneChat.this,ContactsForChatActivityNew.class);
                startActivity(i);
                finish();
            }
        });
    }









    @Override
    public void onBackPressed() {
        Intent i=new Intent(One_To_OneChat.this,ContactsForChatActivityNew.class);
        startActivity(i);
        finish();
    }
}

