package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.CircleChatAdapter;
import com.neterbox.jsonpojo.circle_chat.CircleChat;
import com.neterbox.jsonpojo.circle_chat.CircleChatDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Circle_chat extends AppCompatActivity {

    ImageView ileft, ivActionImage, button_chat_send;
    TextView title;
    Activity activity;
    String Loginname, pic, user_id, circle_id, message = null, id;
    Sessionmanager sessionmanager;
    CircleImageView image;
    ListView listchat;
    EditText edit_chat_message;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<CircleChatDatum> circleChatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_chat);
        activity = this;
        idMappings();
        Listner();
        sessionmanager = new Sessionmanager(this);
        user_id = sessionmanager.getValue(Sessionmanager.Id);
        circle_id = getIntent().getStringExtra("circle_id");
        Loginname = getIntent().getStringExtra("name");
        pic = getIntent().getStringExtra("pic");

        if (!(title.getText().toString().equals(""))) {
            title.setText(Loginname);
        }
        if (new Sessionmanager(activity).getValue(Sessionmanager.Files) != null) {
            Glide.with(activity).load(pic).placeholder(R.drawable.dummy).into(image);

        }

        circlechat(user_id,circle_id,message);
        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circlechat(user_id, circle_id, edit_chat_message.getText().toString());
                hideKeyboard();
                edit_chat_message.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Circle_chat.this, Circles.class);
        startActivity(i);
        finish();
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void idMappings() {
        ileft = (ImageView) findViewById(R.id.ileft);
        ivActionImage = (ImageView) findViewById(R.id.ivActionImage);
        button_chat_send = (ImageView) findViewById(R.id.button_chat_send);
        title = (TextView) findViewById(R.id.title);
        edit_chat_message = (EditText) findViewById(R.id.edit_chat_message);
        image = (CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
        ivActionImage.setImageResource(R.drawable.pencile);
        listchat = (ListView) findViewById(R.id.listchat);
    }

    public void Listner() {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Circle_chat.this, Circles.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void circlechat(String user_id, String circle_id, String message) {
        Call<CircleChat> followerlistCall = apiInterface.circlechatpojo(user_id, circle_id, message);

        followerlistCall.enqueue(new Callback<CircleChat>() {
            @Override
            public void onResponse(Call<CircleChat> call, Response<CircleChat> response) {
                if (response.body().getStatus().equals("Success")) {
                    Log.e("Followerlist REPONSE", new Gson().toJson(response.body().getData()));
                    if (Constants.circleChatData != null) {
                        Constants.circleChatData.clear();
                    }
                    Constants.circleChatData = response.body().getData();
                    CircleChatAdapter adapter = new CircleChatAdapter(activity);
                    listchat.setAdapter(adapter);

                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CircleChat> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
