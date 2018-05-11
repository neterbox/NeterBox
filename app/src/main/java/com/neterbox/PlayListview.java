package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Playlist_Adapter;

public class PlayListview extends AppCompatActivity {

    ListView playlist_view;
    Playlist_Adapter adapter;
    Activity activity;
    ImageView ichat,icircle,iplay;
    LinearLayout lplaylistitem;
    ImageView ileft,iright;
    TextView title;
    String[] itemname = {
            "Bollywood",
            "Camera",
            "Cold War",
            "beauty",
            "Education",
            "Hardware",
            "Technologies",
            "Mobile",
            "Enterntainment"
    };

    Integer[] imgid = {
            R.drawable.bollywood,
            R.drawable.camera,
            R.drawable.cold_war,
            R.drawable.beauty,
            R.drawable.educational,
            R.drawable.hardware,
            R.drawable.technologies,
            R.drawable.mobile,
            R.drawable.enterntainment,

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_listview);


        activity = this;

        Resources res =getResources();
        playlist_view =(ListView)findViewById( R.id.playlist_view );
        ichat=(ImageView)findViewById(R.id.ichat);
        icircle=(ImageView)findViewById(R.id.icircle);
        iplay=(ImageView)findViewById(R.id.iplay);
        lplaylistitem=(LinearLayout) findViewById(R.id.lplaylistitem);

        adapter=new Playlist_Adapter(activity, itemname, imgid);
        playlist_view.setAdapter( adapter );

        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setImageResource(R.drawable.square);
        title.setText("Channels");


        playlist_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(PlayListview.this, Playvideo_page.class);
                startActivity(it);
                finish();

            }
        });

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,ChatModule.class);
                startActivity(i);
                finish();
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,Circles.class);
                startActivity(i);
                finish();
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PlayListview.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PlayListview.this,PlayGridview.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(PlayListview.this,PlayGridview.class);
        startActivity(i);
        finish();
    }
}
