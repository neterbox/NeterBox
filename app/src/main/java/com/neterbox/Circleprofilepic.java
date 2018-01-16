package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Circleprofilepic extends AppCompatActivity {

    LinearLayout lcpcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleprofilepic);


        lcpcomment=(LinearLayout) findViewById(R.id.lcpcomment);

        lcpcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Circleprofilepic.this,Circle_comment.class);
                startActivity(i);
            }
        });


    }
}
