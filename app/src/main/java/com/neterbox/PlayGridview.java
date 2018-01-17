package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Circle_Adapter;
import com.neterbox.customadapter.Playgrid_Adapter;
import com.neterbox.customadapter.Playlist_Adapter;

public class PlayGridview extends AppCompatActivity {

    GridView playgrid_view;
    Activity activity;
    ImageView ileft,iright;
    TextView title;

    String[] itemname = {
            "Charmis",
            "Camera",
            "Cold War"
    };

    Integer[] imgid = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,

    };
    String[] itemname1 = {
            "Charmis",
            "Camera",
            "Cold War"
    };

    Integer[] imgid1 = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,

    };
    ImageView ichat, icircle, iplay, iplaygrid_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_gridview);

        activity = this;
        idMappings();
        listener();

        Playgrid_Adapter adapter = new Playgrid_Adapter(this, itemname, imgid, itemname1, imgid1);
        playgrid_view.setAdapter(adapter);
    }

    private void listener() {
        playgrid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(PlayGridview.this, Playvideo_page.class);
                startActivity(it);
                finish();

            }
        });
        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayGridview.this, Contactsforoneononechat.class);
                startActivity(i);
                finish();
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayGridview.this, Circles.class);
                startActivity(i);
                finish();
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayGridview.this, PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PlayGridview.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PlayGridview.this,PlayListview.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(PlayGridview.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings() {

        playgrid_view = (GridView) findViewById(R.id.playgrid_view);
        ichat = (ImageView) findViewById(R.id.ichat);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);
        iplaygrid_pic = (ImageView) findViewById(R.id.iplaygrid_pic);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setImageResource(R.drawable.correctwithwhitebox);
        title.setText("Channels");
    }

}