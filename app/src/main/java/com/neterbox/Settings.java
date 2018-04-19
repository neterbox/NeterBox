package com.neterbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    ImageView ileft,iright;
    TextView title;
    LinearLayout ledit_profile,llAboutus,llPrivacypolicy;
    RelativeLayout rchangepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ledit_profile=(LinearLayout)findViewById(R.id.ledit_profile);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        rchangepassword=(RelativeLayout) findViewById(R.id.rchangepassword);
        llAboutus=(LinearLayout) findViewById(R.id.llAboutus);
        llPrivacypolicy =(LinearLayout) findViewById(R.id.llPrivacypolicy);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Settings");

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Settings.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
        ledit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i =new Intent(Settings.this,EditProfile.class);
                startActivity(i);
                finish();
            }
        });
        llAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,AboutUs.class);
                startActivity(i);
                finish();
            }
        });
        llPrivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Privacy_Policy.class);
                startActivity(i);
                finish();
            }
        });
        rchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Change_password.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Settings.this,HomePage.class);
        startActivity(i);
        finish();
    }
}