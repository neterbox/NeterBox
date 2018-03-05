package com.neterbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChatBox extends AppCompatActivity {

    ImageView ileft,iright,chatattach,ichatyellow, icircle_video, ilocation,icontact;
    TextView title;
    CircleImageView image;
    Context context;
    LinearLayout lpost_upload_option;
    public static final int CAMERA_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;
    static final int VIDEO_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_box);
        context = this;
        idMapping();
        Listener();
    }
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)
                    ) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }


    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }


    public void idMapping()
    {
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        image=(CircleImageView)findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.addmember);
        image.setImageResource(R.drawable.pic4);
        ichatyellow=(ImageView) findViewById(R.id.ichatyellow);
        icircle_video=(ImageView) findViewById(R.id.icircle_video);
        icontact=(ImageView) findViewById(R.id.icontact);
        ilocation=(ImageView) findViewById(R.id.ilocation);
        chatattach=(ImageView) findViewById(R.id.chatattach);
        lpost_upload_option=(LinearLayout) findViewById(R.id.lpost_upload_option);
    }
    public void Listener()
    {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GroupChatBox.this,ChatModule.class);
                startActivity(i);
                finish();
            }
        });

        chatattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpost_upload_option.setVisibility(View.VISIBLE);
            }
        });

        ichatyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        icircle_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video();
            }
        });

        icontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ilocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    public void onBackPressed() {
        Intent i=new Intent(context,ChatModule.class);
        startActivity(i);
        finish();
    }
    public void camera() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                checkCameraPermission();
            } else {
                takePhotoFromCamera();
            }
        }
    }

    public void video() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                checkCameraPermission();
            } else {
                dispatchTakeVideoIntent();
            }
        }
    }


}
