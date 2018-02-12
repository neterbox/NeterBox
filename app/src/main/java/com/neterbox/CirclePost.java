package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddP;
import com.neterbox.jsonpojo.circle.CircleListDatum;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListPojo;
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
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.HomePage.CAMERA_REQUEST;
import static com.neterbox.HomePage.GALLARY_REQUEST;
import static com.neterbox.HomePage.MY_PERMISSIONS_REQUEST_CAMERA;
import static com.neterbox.HomePage.MY_PERMISSIONS_REQUEST_GALLARY;
import static com.neterbox.utils.Sessionmanager.index;
import static com.neterbox.utils.Sessionmanager.user_id;

public class CirclePost extends AppCompatActivity  implements LocationListener {

    private List<CirclePost> circlePostList;
    ListView li_circle_post;
    LinearLayout lcirclestrip,lfrndprofile,lpost_upload_option;
    TextView title;
    ImageView ichatgreen, iright, ichatyellow, icircle_video, iimage_upload;
    List<CircleListDatum> circleListData = new ArrayList<>();
    Activity activity;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_post);
        activity = this;
        circlePostList = new ArrayList<>();
        idMappings();
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    lpost_upload_option.setVisibility(View.VISIBLE);
                }

        });



//        listener();
        sessionmanager = new Sessionmanager(this);
        String countries_id = null;
        String circle_id = null;
        String state_id = null;
        CirclePostPage(index, circle_id, countries_id, state_id);

        String comments = null;
        MultipartBody.Part post_files = null;
  //      CirclePostAdd(user_id, circle_id, countries_id, state_id, comments, post_files);

        String id = null;
    //    CirclePostDelete(id);
     //  chatgreen =(ImageView)findViewById( R.id.chatgreen );
        ichatgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CirclePost.this, Circle_chat.class);
                startActivity(i);
                finish();
            }
        });


        li_circle_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
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


        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions((Activity) activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);
            }
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity,
                    android.Manifest.permission.CAMERA)
                    ) {
                ActivityCompat.requestPermissions((Activity) activity,
                        new String[]{android.Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) activity,
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
        ImageView profile_image = null;
        if (requestCode == GALLARY_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

//                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile_image.setImageBitmap(bitmap);
                    File fileGallery=new File(path);
                    Uploadpic(new Sessionmanager(activity).getValue(Sessionmanager.Id),fileGallery);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == CAMERA_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(thumbnail);
            String imagePath=saveImage(thumbnail);
            if (Helper.isConnectingToInternet(activity)) {
                File fileCamera=new File(imagePath);

                Uploadpic(new Sessionmanager(activity).getValue(Sessionmanager.Id),fileCamera);
            }
            else
            {
                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void Uploadpic(String value, File fileCamera) {
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

    private void idMappings() {
        ichatgreen = (ImageView) findViewById(R.id.ichatgreen);
        ichatyellow = (ImageView) findViewById(R.id.ichatyellow);
        icircle_video = (ImageView) findViewById(R.id.icircle_video);
        iimage_upload = (ImageView) findViewById(R.id.iimage_upload);
        li_circle_post = (ListView) findViewById(R.id.li_circle_post);
        lcirclestrip = (LinearLayout) findViewById(R.id.lcirclestrip);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        lpost_upload_option = (LinearLayout) findViewById(R.id.lpost_upload_option);
        lpost_upload_option.setVisibility(View.GONE);


        iright.setImageResource(R.drawable.pencile);
        title.setText("Travel");
    }


     /* TODO CIrcle PostList  API */
    public void CirclePostPage(final String index, String circle_id, String countries_id, String state_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        final Call<CirclePostListPojo> circlePostCall = apiInterface.circlepostlistpojocall(index, circle_id ,countries_id ,state_id);
        circlePostCall.enqueue(new Callback<CirclePostListPojo>() {
            @Override
            public void onResponse(Call<CirclePostListPojo> call, Response<CirclePostListPojo> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {
                    sessionmanager.createSession_circlepostlistdata(response.body());
                    Toast.makeText(CirclePost.this, "Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CirclePost.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CirclePostListPojo> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

//
//    /*  TODO CIrcle PostADD  API */
//
//    public void CirclePostAdd(final String user_id , String circle_id, String countries_id, String state_id, String comments, MultipartBody.Part post_files){
//        final ProgressDialog dialog = new ProgressDialog(activity);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("Please Wait...");
//        dialog.show();
//
//        final Call<CirclePostAddP> circlePostAddPCall = apiInterface.circlepostaddpojocall(user_id , circle_id, countries_id, state_id, comments, post_files);
//        circlePostAddPCall.enqueue(new Callback<CirclePostAddP>() {
//            @Override
//            public void onResponse(Call<CirclePostAddP> call, Response<CirclePostAddP> response) {
//                dialog.dismiss();
//               if (response.body().getStatus().equals("Success")) {
//                    sessionmanager.createSession_circlepostadddata(response.body());
//                    Sessionmanager.setPreferenceBoolean(CirclePost.this, Constants.IS_LOGIN, true);
//                   // Intent i = new Intent(CirclePost.this, CirclePost.class);
//                   // startActivity(i);
//                    finish();
//                } else {
//                    Toast.makeText(CirclePost.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//            @Override
//            public void onFailure(Call<CirclePostAddP> call, Throwable t) {
//               dialog.dismiss();
//              }
//        });
//
//    }
//
//    /*TODO CIrcle PostDELETE  API*/
//
//    public void CirclePostDelete(final String id) {
//        final ProgressDialog dialog = new ProgressDialog(activity);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("Please Wait...");
//        dialog.show();
//
//        final Call<CirclePostDeleteP> circlePostDeletePCall = apiInterface.circlepostdeletepojocall(id);
//        circlePostDeletePCall.enqueue(new Callback<CirclePostDeleteP>() {
//            @Override
//            public void onResponse(Call<CirclePostDeleteP> call, Response<CirclePostDeleteP> response) {
//              //  dialog.dismiss();
//                if (response.body().getStatus().equals("Success")) {
//                    sessionmanager.createSession_circlepostDeletdata(response.body());
//                    Sessionmanager.setPreferenceBoolean(CirclePost.this, Constants.IS_LOGIN, true);
//                //    Intent i = new Intent(CirclePost.this, Circles.class);
//
//                  //  startActivity(i);
//                    finish();
//                } else {
//                    Toast.makeText(CirclePost.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<CirclePostDeleteP> call, Throwable t) {
//                dialog.dismiss();
//            }
//        });
//
//    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(CirclePost.this, Circles.class);
        startActivity(i);
        finish();
    }

    public List<CirclePost> getCirclePostList() {
        return circlePostList;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

