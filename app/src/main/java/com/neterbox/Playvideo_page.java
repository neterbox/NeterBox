package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neterbox.customadapter.Playvideo_Adapter;

public class Playvideo_page extends AppCompatActivity {

    ListView playvideo_listview;
    Playvideo_Adapter adapter;
    public Playvideo_page playvideo ;
    LinearLayout lplaycomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo_page);

        playvideo = this;

        Resources res =getResources();
        playvideo_listview =(ListView)findViewById( R.id.playvideo_listview );
        lplaycomment=(LinearLayout)findViewById(R.id.lplaycomment);

        adapter=new Playvideo_Adapter(playvideo);
        playvideo_listview.setAdapter( adapter );

        lplaycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Playvideo_page.this,Channel_comment.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Playvideo_page.this,PlayGridview.class);
        startActivity(i);
        finish();
    }
}
