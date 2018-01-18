package com.neterbox;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.neterbox.utils.Constants;
import com.neterbox.utils.Securedpreferences;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Log.e("boolean",":"+ Securedpreferences.getPreferenceBoolean(Splash_screen.this, Constants.IS_LOGIN,false));

                if(Securedpreferences.getPreferenceBoolean(Splash_screen.this, Constants.IS_LOGIN,false))
                {
                    Intent mainIntent = new Intent(Splash_screen.this, HomePage.class);
                    startActivity(mainIntent);
                    finish();
                }
                else
                {
                    Intent mainIntent = new Intent(Splash_screen.this, LoginPage.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, 1000);
    }
}
