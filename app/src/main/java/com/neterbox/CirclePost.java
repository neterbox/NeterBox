package com.neterbox;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.Circle_post_Adapter;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddPojo;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListDatum;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.utils.Sessionmanager.index;

public class CirclePost extends Activity implements LocationListener {

    private List<CirclePost> circlePostList;
    ListView li_circle_post;
    LinearLayout lcirclestrip, lfrndprofile, lpost_upload_option;
    TextView title;
    ImageView ichatgreen, iright, ichatyellow, icircle_video, iimage_upload, ileft, image;
    List<CirclePostListDatum> circlePostListPojos = new ArrayList<>();
    Activity activity;
    String Loginname, circle_id, state_id, countries_id, index = "1", post_files,user_id,comments;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    public static final int GALLARY_REQUEST = 2;
    public static final int CAMERA_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_GALLARY = 11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;
    static final int VIDEO_CAPTURE = 1;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_post);
        activity = this;
        circlePostList = new ArrayList<>();
        idMappings();
        Listner();
        sessionmanager = new Sessionmanager(this);
        circle_id = sessionmanager.getValue(Sessionmanager.CircleId);
        countries_id = getIntent().getStringExtra("Country id");
        state_id = getIntent().getStringExtra("State id");
        user_id = sessionmanager.getValue(Sessionmanager.Id);
        CirclePostPage(index, circle_id, countries_id, state_id);

        String comments = null;
        MultipartBody.Part post_files = null;
        String id = null;

        Loginname = sessionmanager.getValue(Sessionmanager.CircleName);
        if (!(title.getText().toString().equals(""))) {
            title.setText(Loginname);
        }
        if (new Sessionmanager(activity).getValue(Sessionmanager.Files) != null) {
            Glide.with(activity).load(new Sessionmanager(activity).getValue(Sessionmanager.Files)).placeholder(R.drawable.dummy).into(image);

        }
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
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) activity,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
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
            Toast.makeText(activity, "gallary request", Toast.LENGTH_LONG).show();
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    post_files = saveImage(bitmap);
                    if (bitmap != null) {
                        popup(bitmap);
                    }
//                    if (Helper.isConnectingToInternet(activity)) {
//                        popup(bitmap);
//                    } else {
//                        Helper.showToastMessage(activity, "No Internet Connection");
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
//        } else if (requestCode == CAMERA_REQUEST) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
////            icircle_post_pic.setImageBitmap(thumbnail);
//            String imagePath = saveImage(thumbnail);
//            if (Helper.isConnectingToInternet(activity)) {
//                File fileCamera = new File(imagePath);
//
////                Uploadpic(new Sessionmanager(activity).getValue(Sessionmanager.Id),fileCamera);
//            } else {
//                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
//            }
//
//        }
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

    public void idMappings() {
        ichatgreen = (ImageView) findViewById(R.id.ichatgreen);
        ichatyellow = (ImageView) findViewById(R.id.ichatyellow);
        icircle_video = (ImageView) findViewById(R.id.icircle_video);
        image = (ImageView) findViewById(R.id.image);
        iimage_upload = (ImageView) findViewById(R.id.iimage_upload);
        li_circle_post = (ListView) findViewById(R.id.li_circle_post);
        lcirclestrip = (LinearLayout) findViewById(R.id.lcirclestrip);
        iright = (ImageView) findViewById(R.id.iright);
        ileft = (ImageView) findViewById(R.id.ileft);
        title = (TextView) findViewById(R.id.title);
        lpost_upload_option = (LinearLayout) findViewById(R.id.lpost_upload_option);
        lpost_upload_option.setVisibility(View.GONE);
        iright.setImageResource(R.drawable.pencile);
    }

    public void Listner() {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CirclePost.this, Circles.class);
                startActivity(it);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lpost_upload_option.setVisibility(View.VISIBLE);
            }

        });

        ichatgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CirclePost.this, Circle_chat.class);
                startActivity(i);
                finish();
            }
        });

        ichatyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });
        iimage_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallary();
            }
        });
        icircle_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video();
            }
        });
        li_circle_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    /* TODO CIrcle PostList  API */
    public void CirclePostPage(final String index, String circle_id, String countries_id, String state_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        final Call<CirclePostListPojo> circlePostCall = apiInterface.circlepostlistpojocall(index, circle_id, countries_id, state_id);
        circlePostCall.enqueue(new Callback<CirclePostListPojo>() {
            @Override
            public void onResponse(Call<CirclePostListPojo> call, Response<CirclePostListPojo> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {
                    sessionmanager.createSession_circlepostlistdata(response.body());
                    Toast.makeText(CirclePost.this, "Successfully", Toast.LENGTH_SHORT).show();
                    circlePostListPojos = new ArrayList<CirclePostListDatum>();
                    circlePostListPojos = response.body().getData();
                    Circle_post_Adapter adapter = new Circle_post_Adapter(activity, circlePostListPojos);
                    li_circle_post.setAdapter(adapter);
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


    /*  TODO CIrcle PostADD  API */

    public void CirclePostAdd(String user_id, String circle_id, String countries_id, String state_id,String comments,File post_files) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        RequestBody loginid = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody circleid = RequestBody.create(MediaType.parse("text/plain"), circle_id);
        RequestBody countriesid = RequestBody.create(MediaType.parse("text/plain"), countries_id);
        RequestBody stateid = RequestBody.create(MediaType.parse("text/plain"), state_id);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comments);

        if (post_files!=null) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), post_files);
            MultipartBody.Part userProfile = MultipartBody.Part.createFormData("profile_pic", post_files.getName(), requestFile);


            final Call<CirclePostAddPojo> circlePostAddPCall = apiInterface.circlepostaddpojocall(loginid, circleid, countriesid, stateid, comment, userProfile);
            circlePostAddPCall.enqueue(new Callback<CirclePostAddPojo>() {
                @Override
                public void onResponse(Call<CirclePostAddPojo> call, Response<CirclePostAddPojo> response) {
                    dialog.dismiss();
                    Log.e("Circlradd",new Gson().toJson(response));
                    if (response.body().getStatus().equals("Success")) {
                        sessionmanager.createSession_circlepostadddata(response.body());
                        Toast.makeText(activity, "Successfully added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CirclePost.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<CirclePostAddPojo> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

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

    public void camera() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera();
            } else {
                checkCameraPermission();

            }
        }
    }

    public void gallary() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                choosePhotoFromGallary();
            } else {
                checkCameraPermission();
                checkStoragePermission();
            }
        }
    }

    public void video() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakeVideoIntent();
            } else {
                checkCameraPermission();

            }
        }
    }

    public void popup(Bitmap bitmap){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.circlepostcaption);

        final ImageView icircle_post_pic = (ImageView)dialog.findViewById(R.id.icircle_post_pic);
        ImageView button_chat_send = (ImageView) dialog.findViewById(R.id.button_caption_send);
        final EditText edit_caption = (EditText) dialog.findViewById(R.id.edit_caption);

        icircle_post_pic.setImageBitmap(bitmap);
        final File fileGallery=new File(post_files);
        dialog.show();

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(icircle_post_pic!=null ){
                    CirclePostAdd(sessionmanager.getValue(Sessionmanager.Id),
                            sessionmanager.getValue(Sessionmanager.CircleId),
                            getIntent().getStringExtra("Country id"),
                            getIntent().getStringExtra("State id"),
                            edit_caption.getText().toString(),
                            fileGallery);
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

}