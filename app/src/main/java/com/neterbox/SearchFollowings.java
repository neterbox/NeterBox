package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neterbox.customadapter.Search_Following_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;

public class SearchFollowings extends Activity {
    ListView list2;
    LinearLayout lfollowinglist;
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

            }
        });
    }

    private void idMappings()
    {
        list2=(ListView)findViewById(R.id.list2);
    }
}
