package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neterbox.customadapter.Followers_Adapter;
import com.neterbox.customadapter.Friend_RequestList_Adapter;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;
import com.neterbox.jsonpojo.followingadd.FollowingDatum;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search_followers extends AppCompatActivity {

    ListView list1;
    Activity activity;
    ImageView ileft, iright;
    TextView title;
    List<FollowerlistDatum> followerlistData;
    Sessionmanager sessionmanager;
    SwipeRefreshLayout swipelayout;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_followers);

        activity = this;
        idMappings();
        listener();
        sessionmanager = new Sessionmanager(this);
        followerlist(sessionmanager.getValue(Sessionmanager.Id));

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followerlist(sessionmanager.getValue(Sessionmanager.Id));
            }
        });
    }

    private void listener() {
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(search_followers.this, FollowerProfile.class);
                startActivity(it);
                finish();

            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(search_followers.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(search_followers.this, HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings() {

        list1 = (ListView) findViewById(R.id.list1);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        swipelayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Followers");
    }

    public void followerlist(String follower_id) {
        Call<Followerlist> followerlistCall = apiInterface.followerlistpojo(follower_id);

        followerlistCall.enqueue(new Callback<Followerlist>() {
            @Override
            public void onResponse(Call<Followerlist> call, Response<Followerlist> response) {
                if (response.body().getStatus().equals("Success")) {
                    Log.e("Followerlist REPONSE",new Gson().toJson(response.body().getData()));
                    followerlistData = new ArrayList<FollowerlistDatum>();
                    followerlistData = response.body().getData();
                    if(Constants.followerlistData!=null)
                    {
                        Constants.followerlistData.clear();
                    }
                    Constants.followerlistData.addAll(response.body().getData());

                    Followers_Adapter adapter = new Followers_Adapter(activity, followerlistData);
                    list1.setAdapter(adapter);

                    for(int i=0;i<followerlistData.size();i++)
                    {
                        sessionmanager.createSession_followerlist(followerlistData.get(i));
                    }

                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(search_followers.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Followerlist> call, Throwable t) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
