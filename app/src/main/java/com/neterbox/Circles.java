package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.Circle_Adapter;
import com.neterbox.customadapter.Followers_Adapter;

public class Circles extends Activity {
    GridView gcirclegrid;
    Activity activity;
    ImageView ileft,iright;
    TextView title;

    String[] itemname ={
            "Charmis",
            "Camera",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,

    };
    ImageView ichat,icircle,iplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles);

        activity = this;
        idMappings();
        listener();

        Circle_Adapter adapter = new Circle_Adapter(this, itemname, imgid);
        gcirclegrid.setAdapter(adapter);
    }


    private void listener() {
        gcirclegrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(Circles.this,Circle_chat.class);
                startActivity(it);
                finish();

            }
        });
        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Circles.this,Contactsforoneononechat.class);
                startActivity(i);
                finish();
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Circles.this,Circles.class);
                startActivity(i);
                finish();
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Circles.this,PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Circles.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circles.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings()
    {

        gcirclegrid=(GridView)findViewById(R.id.gcirclegrid);
        ichat=(ImageView)findViewById(R.id.ichat);
        icircle=(ImageView)findViewById(R.id.icircle);
        iplay=(ImageView)findViewById(R.id.iplay);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Channels");
    }
    }