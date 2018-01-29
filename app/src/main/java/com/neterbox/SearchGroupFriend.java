package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.jsonpojo.country.Country;
import com.neterbox.jsonpojo.country.CountryDatum;
import com.neterbox.jsonpojo.near_by_friend.Nearbyfriend;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.jsonpojo.state.StateDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.runner.Version.id;

public class SearchGroupFriend extends AppCompatActivity {
LinearLayout lnearby,laddtofollower;
    ImageView ileft,iright,inearbyback,inearby;
    TextView title,tnearby;
    Activity activity;
    int i;
    String id="",latitude="",longitude="";

    ArrayList<NearbyfriendDatum> nearbyfriendData = new ArrayList<>();

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_friend);

        idmapping();
     nearbyfriend(id,latitude,longitude);
        listner();


    }
    public void idmapping() {
        laddtofollower = (LinearLayout) findViewById(R.id.laddtofollower);
        lnearby = (LinearLayout) findViewById(R.id.lnearby);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Find Friends");
    }
    public void listner(){
        lnearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,FriendList.class);
                startActivity(i);
                finish();
            }
        });

        laddtofollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,search_followers.class);
                startActivity(i);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SearchGroupFriend.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void nearbyfriend(String id, String latitude ,String longitude)
    {

        Call<Nearbyfriend> nearbyfriendCall = apiInterface.Nerbyfriendpojo(id ,latitude ,longitude);

        nearbyfriendCall.enqueue(new Callback <Nearbyfriend>() {
            @Override
            public void onResponse(Call<Nearbyfriend> call, Response<Nearbyfriend> res) {
                if (res.body().getStatus().equals("Success"))
                {
                            Sessionmanager.setPreferenceBoolean(SearchGroupFriend.this, Constants.IS_LOGIN, true);
                            for (i=0;i<res.body().getData().size();i++)
                            new Sessionmanager(SearchGroupFriend.this).putSessionValue(Sessionmanager.Id, res.body().getData().get(i).getUsers().getId());

                        Intent it = new Intent(SearchGroupFriend.this, HomePage.class);
                        startActivity(it);
                        finish();

                    } else {
                            Toast.makeText(SearchGroupFriend.this, res.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }


            @Override
            public void onFailure(Call<Nearbyfriend> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(SearchGroupFriend.this,HomePage.class);
        startActivity(i);
        finish();
    }
}
