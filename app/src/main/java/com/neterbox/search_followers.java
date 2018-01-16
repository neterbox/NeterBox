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

import com.neterbox.customadapter.Followers_Adapter;
import com.neterbox.customadapter.Search_Following_Adapter;

public class search_followers extends AppCompatActivity {

    ListView list1;
    Activity activity;
    LinearLayout lfollowerlist;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_followers);

        activity=this;
        idMappings();
        listener();

        Followers_Adapter adapter=new Followers_Adapter(this, itemname, imgid);
        list1.setAdapter(adapter);


    }

    private void listener() {
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(search_followers.this,FollowerProfile.class);
                startActivity(it);

            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(search_followers.this,HomePage.class);
                startActivity(i);
            }
        });
    }

    private void idMappings()
    {

        list1=(ListView)findViewById(R.id.list1);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Followers");
    }
}
