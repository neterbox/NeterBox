package com.neterbox;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class MyVideoView extends AppCompatActivity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    String video_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_view);

        idMappings();
        listener();

        video_url = getIntent().getStringExtra("video");

//        // Create a progressbar
//        pDialog = new ProgressDialog(VideoViewActivity.this);
//        pDialog.setMessage("Loading...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        // Show progressbar
//        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    MyVideoView.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(video_url);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void idMappings()
    {
        videoview = (VideoView) findViewById(R.id.VideoView);
    }

    private void listener()
    {
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
//                pDialog.dismiss();
                videoview.start();
            }
        });
    }



//    VideoView simpleVideoView;
//    MediaController mediaControls;
//    String url;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_video_view);
//       url=getIntent().getStringExtra("video");
//        Uri uri = Uri.parse(url); //Declare your url here.
//
//        VideoView mVideoView  = (VideoView)findViewById(R.id.simpleVideoView);
//        mVideoView.setMediaController(new MediaController(this));
//        mVideoView.setVideoURI(uri);
//        mVideoView.requestFocus();
//        mVideoView.start();
//
//
//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
//        {
//            @Override
//            public void onPrepared(MediaPlayer mp)
//            {
//                simpleVideoView.start();
//            }
//        });
//
//    }

}

