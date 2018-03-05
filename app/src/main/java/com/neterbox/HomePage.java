package com.neterbox;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.location.Criteria;
import android.location.Location;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import com.bumptech.glide.Glide;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.Login.LoginDatum;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.jsonpojo.register.RegistrationDatum;
import com.neterbox.jsonpojo.uploadpic.Uploadpic;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends Activity implements LocationListener {

    TextView taddfriend, tlogout,tprofilename;
    LinearLayout lph;
    RelativeLayout relative_following, relative_follower, relative_frnd, relative_settings;
    ImageView iback1, iback2, iback3, iback4, ichat, icircle, iplay;
    CircleImageView profile_image;
<<<<<<< HEAD

   String Loginname,index,user_id;
=======
    Double latitude,longitude;
   String Loginname;
>>>>>>> 7a229364e04a7f07edcebd5859c752759a0f714d

    public static final int GALLARY_REQUEST=2;
    public static final int CAMERA_REQUEST=1;
    public static final int MY_PERMISSIONS_REQUEST_GALLARY=11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA=12;

    Context context;
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        context = this;
        sessionmanager = new Sessionmanager(this);
        this.Loginname =Loginname;

        taddfriend = (TextView) findViewById(R.id.taddfriend);
        tprofilename = (TextView) findViewById(R.id.tprofilename);
        lph = (LinearLayout) findViewById(R.id.lph);
        iback1 = (ImageView) findViewById(R.id.iback1);
        iback2 = (ImageView) findViewById(R.id.iback2);
        iback3 = (ImageView) findViewById(R.id.iback3);
        iback4 = (ImageView) findViewById(R.id.iback4);
        tlogout = (TextView) findViewById(R.id.tlogout);
        relative_following = (RelativeLayout) findViewById(R.id.relative_following);
        relative_follower = (RelativeLayout) findViewById(R.id.relative_follower);
        relative_frnd = (RelativeLayout) findViewById(R.id.relative_frnd);
        relative_settings = (RelativeLayout) findViewById(R.id.relative_settings);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        ichat = (ImageView) findViewById(R.id.ichat);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);


       profile_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i = new Intent(HomePage.this, EditProfile.class);
                                                startActivity(i);
                                                finish();
                                            }
       });
        // set dummy profile if profile pic is not selected
        if(  new Sessionmanager(context).getValue(Sessionmanager.profile) != null)
        {
            Glide.with(context).load(new Sessionmanager(context).getValue(Sessionmanager.profile)).placeholder(R.drawable.dummy).into(profile_image);

        }
        // Store username in Homepage
        Loginname = sessionmanager.getValue(Sessionmanager.Name);
        if(!(tprofilename.getText().toString().equals(""))) {
            tprofilename.setText(Loginname);
        }

        lph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, UserProfile.class);
                startActivity(i);
                finish();
            }
        });
        taddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchGroupFriend.class);
                startActivity(i);
                finish();
            }
        });
        relative_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, FriendList.class);
                startActivity(i);
                finish();
            }
        });
       relative_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchFollowings.class);
                startActivity(i);
                finish();
            }
        });
        relative_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, search_followers.class);
                startActivity(i);
                finish();
            }
        });
        relative_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Settings.class);
                startActivity(i);
                finish();
            }
        });
        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionmanager.logoutUser();
                Intent i = new Intent(HomePage.this, LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();


            }
        });

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, ChatModule.class);
                startActivity(i);
                finish();
            }
        });
        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Circles.class);
                startActivity(i);
                finish();
            }
        });
        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, PlayGridview.class);
                startActivity(i);
                finish();
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        showPictureDialog();

                    } else {
                        //Request Location Permission
                        checkCameraPermission();
                        checkStoragePermission();
                    }
                } else {
                    showPictureDialog();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
       }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void checkStoragePermission() {


        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);
            }
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)
                    ) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLARY_REQUEST);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLARY_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

//                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile_image.setImageBitmap(bitmap);
                    File fileGallery=new File(path);

                    if(Helper.isConnectingToInternet(context)){
                        Uploadpic(new Sessionmanager(context).getValue(Sessionmanager.Id),fileGallery);
                    }
                    else {
                        Helper.showToastMessage(context,"No Internet Connection");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == CAMERA_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(thumbnail);
            String imagePath=saveImage(thumbnail);
            if (Helper.isConnectingToInternet(context)) {
                File fileCamera=new File(imagePath);

                Uploadpic(new Sessionmanager(context).getValue(Sessionmanager.Id),fileCamera);
            }
            else
            {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String saveImage(Bitmap thumbnail) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "IMAGE_DIRECTORY");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File f = new File(path, "DemoPicture.jpg");
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
// TO DO UPDATE PROFILE API
    public void Uploadpic(String Id, File fileCamera) {
        final ProgressDialog progressDialog = Helper.showProgressDialog(context);

        RequestBody loginIdReqBody = RequestBody.create(MediaType.parse("text/plain"), Id);
        Log.e("login_id", "" + Id);
        if (fileCamera != null) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileCamera);
            MultipartBody.Part userProfile = MultipartBody.Part.createFormData("profile_pic", fileCamera.getName(), requestFile);

            Call<Uploadpic> uploadProfileCall = apiInterface.uploadPic(loginIdReqBody, userProfile);
            uploadProfileCall.enqueue(new Callback<Uploadpic>() {
                @Override
                public void onResponse(Call<Uploadpic> uploadImageCall, Response<Uploadpic> response) {
                    if (response.body().getStatus().equals("Success")) {
                        new Sessionmanager(HomePage.this).putSessionValue(Sessionmanager.profile, response.body().getData().getUser().getProfilePic());

                        progressDialog.dismiss();

                        Glide.with(context).load(response.body().getData().getUser().getProfilePic()).into(profile_image);
                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Uploadpic> call, Throwable t) {
                    progressDialog.dismiss();

//                    Helper.showToastMessage(context,t.getMessage());
                }
            });
        }
    }

<<<<<<< HEAD
    public void getprofile(String index,String user_id)
    {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<GetProfile> getProfileCall = apiInterface.getprofilepojo(index, user_id);
        getProfileCall.enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response)
            {
                dialog.dismiss();
                if(response.body().getStatus().equalsIgnoreCase("Success")){
                    sessionmanager.createSession_userprofile((response.body().getData()));

                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                dialog.dismiss();
            }
        });
=======
    //TO DO FETCH LOCATION API
    public void fetchlocation()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        String mprovider = locationManager.getBestProvider(criteria, false);

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
                Toast.makeText(HomePage.this, "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
>>>>>>> 7a229364e04a7f07edcebd5859c752759a0f714d

    }
}