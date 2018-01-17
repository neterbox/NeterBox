package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Circle_comment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_comment);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_comment.this,Circleprofilepic.class);
        startActivity(i);
        finish();
    }
}
