package com.neterbox;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.NearbyfriendAdapter;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NearbyFriendlist extends Activity {
    ListView listview;
    LinearLayout lfrndprofile;
    Activity activity;
    ImageView ileft, iright;
    TextView title;
    List<NearbyfriendDatum> nearbyList = new ArrayList<>();

    private GPSTracker mGPS;
    public double latitude1;
    public double longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_friend_list);
        activity = this;

        nearbyList = (List<NearbyfriendDatum>) getIntent().getSerializableExtra("nearby_data");
        idMappings();
        listener();
        NearbyfriendAdapter adapter = new NearbyfriendAdapter(this, nearbyList);
        listview.setAdapter(adapter);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGPS = new GPSTracker(activity);
                if (mGPS.canGetLocation) {
                    latitude1 = mGPS.getLatitude();
                    longitude1 = mGPS.getLongitude();
                }
            } else {
                checkLocationPermission();
            }
        }

    }

    private void listener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(NearbyFriendlist.this, FollowerProfile.class);
                it.putExtra("profile", (Serializable)nearbyList.get(i));
//                it.putExtra("profile",(Serializable) response.body().getData().getReceiver());
                startActivity(it);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NearbyFriendlist.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(NearbyFriendlist.this, SearchGroupFriend.class);
        startActivity(i);
        finish();
    }

    private void idMappings() {
        listview = (ListView) findViewById(R.id.listview);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Nearby Friend");
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(activity)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) activity,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

}