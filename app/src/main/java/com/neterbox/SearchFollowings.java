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

import com.neterbox.customadapter.Search_Following_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;

public class SearchFollowings extends Activity {
    ListView list2;
    LinearLayout lfollowinglist;
    ImageView ileft,iright;
    TextView title;
    Activity activity;
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
        setContentView(R.layout.activity_search_followings);

        activity=this;
        idMappings();
        listener();

        Search_Following_Adapter adapter=new Search_Following_Adapter(this, itemname, imgid);
        list2.setAdapter(adapter);


    }

    private void listener() {
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(SearchFollowings.this,FollowingProfile.class);
                startActivity(it);
                finish();

            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SearchFollowings.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(SearchFollowings.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings()
    {

        list2=(ListView)findViewById(R.id.list2);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Followings");
    }
}
