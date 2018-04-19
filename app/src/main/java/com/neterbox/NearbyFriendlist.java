package com.neterbox;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

/*TODO Location Implementation*/
        statusCheck();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGPS = new GPSTracker(activity);
                if (mGPS.canGetLocation) {
                    latitude1 = mGPS.getLatitude();
                    longitude1 = mGPS.getLongitude();
                    Constants.shareLoc = "http://maps.google.com/maps?saddr=" +latitude1+","+longitude1;

                }
            } else {
                checkLocationPermission();
            }
        }

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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

    /*btn_readallcontacks.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            List<User> userList = new ArrayList<User>();
            while (phones.moveToNext()) {
                User user = new User();
                user.setNamee(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                user.setPhone(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                userList.add(user);
                Log.d(TAG, "Name : " + user.getNamee().toString());
                Log.d(TAG, "Phone : " + user.getPhone().toString());
            }
            phones.close();
        }
    });*/

}