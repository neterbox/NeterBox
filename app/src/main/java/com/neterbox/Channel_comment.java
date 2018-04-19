package com.neterbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.neterbox.jsonpojo.comment.Comment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Channel_comment extends AppCompatActivity {

    ImageView backblue;
    public Channel_comment channelComment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_comment);

//        backblue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Channel_comment.this, Playvideo_page.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Channel_comment.this,Playvideo_page.class);
        startActivity(i);
        finish();
    }
/*
    *//*  TODO : API CALLING COMMENT  *//*
    public void callapi_comment(String user_id,String post_id,String comments) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<Comment> commentcall = apiInterface.commentpojo(user_id, post_id, comments);
        commentcall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> commentCall, Response<Comment> response) {

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
