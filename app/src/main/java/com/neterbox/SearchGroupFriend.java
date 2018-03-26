package com.neterbox;


import android.Manifest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.runner.Version.id;


public class SearchGroupFriend extends AppCompatActivity implements LocationListener{

    LinearLayout lnearby,laddtofollower,lrequestfrndlist;
    ImageView ileft,iright,inearbyback,inearby;
    TextView title,tnearby;
    Activity activity;
    int i;

    RelativeLayout relative_nearby;
    LocationManager locationManager;
    String mprovider,id="";
    Sessionmanager sessionmanager;
    Context context;
    Double latitude,longitude;

    ArrayList<NearbyfriendDatum> nearbyfriendData = new ArrayList<>();
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_friend);
        sessionmanager=new Sessionmanager(this);
        context = this;

        idmapping();
        //nearbyfriend(id,latitude,longitude);
        listner();

        fetchlocation();
        Log.e("**-----ID-----**:",""+Sessionmanager.Id);

    }
    public void idmapping() {
        laddtofollower = (LinearLayout) findViewById(R.id.laddtofollower);
        lrequestfrndlist = (LinearLayout) findViewById(R.id.lrequestfrndlist);
        lnearby = (LinearLayout) findViewById(R.id.lnearby);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Find Friends");
        relative_nearby=(RelativeLayout)findViewById(R.id.relative_nearby);
    }
    public void listner(){
        relative_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nearbyfriend(new Sessionmanager(context).getValue(Sessionmanager.Id),latitude,longitude);
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

        lrequestfrndlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchGroupFriend.this,FriendRequestList.class);
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
    public void nearbyfriend(String id, Double latitude ,Double longitude)
    {
        Call<Nearbyfriend> nearbyfriendCall = apiInterface.Nerbyfriendpojo(id ,latitude ,longitude);

        nearbyfriendCall.enqueue(new Callback <Nearbyfriend>() {
            @Override
            public void onResponse(Call<Nearbyfriend> call, Response<Nearbyfriend> response) {
                if (response.body().getStatus().equals("Success"))
                {
                    for (i=0;i<response.body().getData().size();i++)
                    {
                        sessionmanager.createSession_nearbydata((response.body().getData().get(i)));
                    }
                    Intent it = new Intent(SearchGroupFriend.this,NearbyFriendlist.class);
                    it.putExtra("nearby_data", (Serializable) response.body().getData());
                    startActivity(it);
                    finish();
                } else {
                    Toast.makeText(SearchGroupFriend.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Nearbyfriend> call, Throwable t) {
            }
        });
    }

    public void fetchlocation()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(SearchGroupFriend.this, "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(SearchGroupFriend.this,HomePage.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
