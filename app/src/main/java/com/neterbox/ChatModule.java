package com.neterbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.neterbox.fragment.MainChat;

public class ChatModule extends AppCompatActivity implements View.OnClickListener,TabHost.OnTabChangeListener{

    Context context;
    ImageView ileft, iright, ichat, icircle, iplay;
    TextView title;
    RelativeLayout bottom_layout;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_module);

        context=this;
        idMapping();
        Listener();
        fragment = new MainChat();

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
//            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public void idMapping()
    {
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setImageResource(R.drawable.plus);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);
        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        title.setText("Contacts");
    }

    public void Listener()
    {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatModule.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatModule.this, Create_group.class);
                startActivity(i);
                finish();
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatModule.this, Circles.class);
                startActivity(i);
                finish();
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatModule.this, PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
}
    @Override
    public void onClick(View v) {
        bottom_layout.setBackgroundColor(Color.BLUE);
    }

    @Override
    public void onBackPressed () {
        Intent i = new Intent(ChatModule.this, HomePage.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onTabChanged(String tabId) {
    }
}
