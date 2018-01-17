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
import android.widget.Toast;

import com.neterbox.customadapter.Friendpro_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;

public class FriendList extends Activity {
    ListView listview;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        activity=this;

        idMappings();
        listener();

        Search_Friend_Adapter adapter=new Search_Friend_Adapter(this, itemname, imgid);
        listview.setAdapter(adapter);


    }

    private void listener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(FriendList.this,FreindProfile.class);
                startActivity(it);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FriendList.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FriendList.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings()
    {

        listview=(ListView)findViewById(R.id.listview);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Friends");
    }
}
