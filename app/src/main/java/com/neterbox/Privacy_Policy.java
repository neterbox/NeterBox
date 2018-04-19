package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Privacy_Policy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Privacy_Policy.this,Settings.class);
        startActivity(i);
        finish();
    }
}
