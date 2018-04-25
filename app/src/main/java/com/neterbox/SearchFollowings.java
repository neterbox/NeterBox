package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.customadapter.Followers_Adapter;
import com.neterbox.customadapter.Followingpro_Adapter;
import com.neterbox.customadapter.Search_Following_Adapter;
import com.neterbox.customadapter.Search_Friend_Adapter;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.FollowingProfile.adapter;

public class SearchFollowings extends Activity {
    ListView list2;
    LinearLayout lfollowinglist;
    ImageView ileft,iright;
    TextView title;
    Activity activity;

    String follower_id;
    /*String[] itemname ={
            "Charmis",
            "Camera",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,

    };*/

    List<FollowerlistDatum> followerlistData;
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_followings);

        activity=this;
        idMappings();
        listener();

        sessionmanager = new Sessionmanager(this);
        follower_id = sessionmanager.getValue(sessionmanager.follower_id);
        followerlist(follower_id);

    }

    private void listener() {
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it =new Intent(SearchFollowings.this,FollowingProfile.class);
                it.putExtra("name",followerlistData.get(i).getFollowerDetail().getName());
                it.putExtra("profile_pic",followerlistData.get(i).getFollowerDetail().getProfilePic());
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
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Followings");
    }

    public void followerlist(String follower_id) {
        Call<Followerlist> followinglistpojo = apiInterface.followinglistpojo(follower_id);

        followinglistpojo.enqueue(new Callback<Followerlist>() {
            @Override
            public void onResponse(Call<Followerlist> call, Response<Followerlist> response) {
                if (response.body().getStatus().equals("Success")) {

                    followerlistData = new ArrayList<FollowerlistDatum>();
                    followerlistData = response.body().getData();

                    Search_Following_Adapter adapter = new Search_Following_Adapter(activity, followerlistData);
                    list2.setAdapter(adapter);

                    for(int i=0;i<followerlistData.size();i++)
                    {
                        sessionmanager.createSession_followerlist(followerlistData.get(i));
                    }

                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchFollowings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Followerlist> call, Throwable t) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}