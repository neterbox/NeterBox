package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neterbox.customadapter.Followerpro_Adapter;
import com.neterbox.customadapter.Playlist_Adapter;

public class PlayListview extends AppCompatActivity {

    ListView playlist_view;
    Playlist_Adapter adapter;
    public PlayListview playListview;
    ImageView ichat,icircle,iplay;
    LinearLayout lplaylistitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_listview);


        playListview = this;

        Resources res =getResources();
        playlist_view =(ListView)findViewById( R.id.playlist_view );
        ichat=(ImageView)findViewById(R.id.ichat);
        icircle=(ImageView)findViewById(R.id.icircle);
        iplay=(ImageView)findViewById(R.id.iplay);
        lplaylistitem=(LinearLayout) findViewById(R.id.lplaylistitem);

        adapter=new Playlist_Adapter(playListview);
        playlist_view.setAdapter( adapter );

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,Contactsforoneononechat.class);
                startActivity(i);
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,Circles.class);
                startActivity(i);
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(PlayListview.this,PlayGridview.class);
                startActivity(i);
            }
        });


//        lplaylistitem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(PlayListview.this,Playvideo_page.class);
//                startActivity(i);
//            }
//        });

    }
}
