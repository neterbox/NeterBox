package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neterbox.customadapter.Followers_Adapter;
import com.neterbox.customadapter.Search_Following_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;
import com.neterbox.jsonpojo.followinglist.Followinglist;
import com.neterbox.jsonpojo.followinglist.FollowinglistDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFollowings extends Activity {
    ListView list2;
    Activity activity;
    ImageView ileft, iright;
    TextView title;
    List<FollowinglistDatum> followinglistData;
    Sessionmanager sessionmanager;
    SwipeRefreshLayout swipelayout;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_followings);

        activity=this;
        idMappings();
        listener();

        sessionmanager = new Sessionmanager(this);
        followinglist(sessionmanager.getValue(Sessionmanager.Id));

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followinglist(sessionmanager.getValue(Sessionmanager.Id));
            }
        });

    }

    private void listener() {
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(SearchFollowings.this,FollowingProfile.class);
                startActivity(it);
                finish();

            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SearchFollowings.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(SearchFollowings.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings()
    {

        list2=(ListView)findViewById(R.id.list2);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        swipelayout=(SwipeRefreshLayout) findViewById(R.id.swipelayout);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Followings");
    }

    public void followinglist(String following_id) {
        Call<Followinglist> followinglistCall = apiInterface.followinglistpojo(following_id);

        followinglistCall.enqueue(new Callback<Followinglist>() {
            @Override
            public void onResponse(Call<Followinglist> call, Response<Followinglist> response) {
                if (response.body().getStatus().equals("Success")) {
                    Log.e("Followinglist REPONSE",new Gson().toJson(response.body().getData()));
                    followinglistData = new ArrayList<FollowinglistDatum>();
                    followinglistData = response.body().getData();

                    Search_Following_Adapter adapter = new Search_Following_Adapter(activity, followinglistData);
                    list2.setAdapter(adapter);

//                    for(int i=0;i<followinglistData.size();i++)
//                    {
//                        sessionmanager.createSession_followerlist(followinglistData.get(i));
//                    }

                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchFollowings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Followinglist> call, Throwable t) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
