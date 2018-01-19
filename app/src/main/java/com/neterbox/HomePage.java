package com.neterbox;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends Activity {

    TextView taddfriend, tlogout;
    LinearLayout lph;
    RelativeLayout relative_following, relative_follower, relative_frnd, relative_settings;
    ImageView iback1, iback2, iback3, iback4, ichat, icircle, iplay;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        taddfriend = (TextView) findViewById(R.id.taddfriend);
        lph = (LinearLayout) findViewById(R.id.lph);
        iback1 = (ImageView) findViewById(R.id.iback1);
        iback2 = (ImageView) findViewById(R.id.iback2);
        iback3 = (ImageView) findViewById(R.id.iback3);
        iback4 = (ImageView) findViewById(R.id.iback4);
        tlogout = (TextView) findViewById(R.id.tlogout);
        relative_following = (RelativeLayout) findViewById(R.id.relative_following);
        relative_follower = (RelativeLayout) findViewById(R.id.relative_follower);
        relative_frnd = (RelativeLayout) findViewById(R.id.relative_frnd);
        relative_settings = (RelativeLayout) findViewById(R.id.relative_settings);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        ichat = (ImageView) findViewById(R.id.ichat);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);

       profile_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i = new Intent(HomePage.this, EditProfile.class);
                                                startActivity(i);
                                                finish();
                                            }
       });
        lph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, UserProfile.class);
                startActivity(i);
                finish();
            }
        });
        taddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchGroupFriend.class);
                startActivity(i);
                finish();
            }
        });
        relative_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, FriendList.class);
                startActivity(i);
                finish();
            }
        });
       relative_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchFollowings.class);
                startActivity(i);
                finish();
            }
        });
        relative_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, search_followers.class);
                startActivity(i);
                finish();
            }
        });
        relative_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Settings.class);
                startActivity(i);
                finish();
            }
        });
        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, LoginPage.class);
                startActivity(i);
                finish();
            }
        });

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, ContactsforChat.class);
                startActivity(i);
                finish();
            }
        });
        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Circles.class);
                startActivity(i);
                finish();
            }
        });
        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
       }
}