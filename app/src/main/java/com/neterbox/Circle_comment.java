package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.jsonpojo.comment.Comment;
import com.neterbox.jsonpojo.like.Like;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Circle_comment extends AppCompatActivity {

    TextView comment_like;
    EditText edit_comments;
    ImageView comment_send;
    String like;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Activity activity;
    Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_comment);
        activity=this;
        sessionmanager = new Sessionmanager(this);

        idMapping();
        Listener();
        like = getIntent().getStringExtra("like");
        comment_like.setText(like);
    }

    private void idMapping() {
        comment_like = (TextView)findViewById(R.id.comment_like);
        comment_send = (ImageView) findViewById(R.id.comment_send);
        edit_comments = (EditText) findViewById(R.id.edit_comments);
    }

    private void Listener() {


        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = sessionmanager.getValue(Sessionmanager.Id);
                String post_id = sessionmanager.getValue(Sessionmanager.postid);
                callapi_comment(user_id,post_id,edit_comments.getText().toString());
            }
        });

    }



    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_comment.this,Circleprofilepic.class);
        startActivity(i);
        finish();
    }

    // TODO : API CALLING COMMENT

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

                } else {
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
    }
}
