package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neterbox.jsonpojo.comment.Comment;
import com.neterbox.utils.Sessionmanager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Circle_chat extends AppCompatActivity {

    ImageView ileft,ivActionImage;
    TextView title;
    Activity activity;
    String Loginname;
    Sessionmanager sessionmanager;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);
        activity=this;
        idMappings();
        Listner();
        sessionmanager = new Sessionmanager(this);
        Loginname = sessionmanager.getValue(Sessionmanager.CircleName);
        if(!(title.getText().toString().equals(""))) {
            title.setText(Loginname);
        }
        if(  new Sessionmanager(activity).getValue(Sessionmanager.Files) != null)
        {
            Glide.with(activity).load(new Sessionmanager(activity).getValue(Sessionmanager.Files)).placeholder(R.drawable.dummy).into(image);

        }
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_chat.this,CirclePost.class);
        startActivity(i);
        finish();
    }

    public void idMappings()
    {
        ileft=(ImageView)findViewById(R.id.ileft);
        ivActionImage=(ImageView)findViewById(R.id.ivActionImage);
        title=(TextView) findViewById(R.id.title);
        image=(CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        ivActionImage.setImageResource(R.drawable.pencile);
    }

    public void Listner()
    {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Circle_chat.this,Circles.class);
                startActivity(i);
                finish();
            }
        });
    }

   /* *//*  TODO : API CALLING COMMENT  *//*
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
