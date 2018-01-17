package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Circle_chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_chat.this,Circles.class);
        startActivity(i);
        finish();
    }
}
