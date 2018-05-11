package com.neterbox;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.CirclePostAdapter;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddPojo;
import com.neterbox.jsonpojo.Unfollow.Unfollow;
import com.neterbox.jsonpojo.circlepost_list.CirclePostListDatum;
import com.neterbox.jsonpojo.circlepost_list.CirclePostListPojo;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followingadd.Following;
import com.neterbox.jsonpojo.followingadd.FollowingDatum;
import com.neterbox.jsonpojo.like.Like;
import com.neterbox.jsonpojo.unlike.Unlike;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CirclePost extends Activity implements LocationListener, CirclePostAdapter.viewclick {

    List<CirclePost> circlePostList;
    ListView li_circle_post;
    LinearLayout lpost_upload_option;
    LinearLayout lcirclestrip, lfrndprofile;
    TextView title;
    ImageView ichatgreen, ivActionImage, ichatyellow, icircle_video, iimage_upload, ileft, image;
    Activity activity;
    String Loginname, circle_id, index = "1", post_files, user_id, comments,selectpath,pic;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    CirclePostAdapter adapter;
    RequestBody requestFile;
    //    SwipeRefreshLayout swipelayout;
    public static final int GALLARY_REQUEST = 2;
    public static final int CAMERA_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_GALLARY = 11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    static final int VIDEO_CAPTURE = 3;
    private LayoutInflater inflater;
    File fileGallery;
    FollowingDatum followingDatum;
    public static String state_id, countries_id;
    List<CirclePostListDatum> circlepostlistdata = new ArrayList<>();
    String post_id, follower_id,followerdata_id;

    static int TAKE_PIC =1;
    Uri outPutfileUri;


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
        pic = sessionmanager.getValue(Sessionmanager.Files);
        if (!(title.getText().toString().equals(""))) {
            title.setText(Loginname);
        }
        if (new Sessionmanager(activity).getValue(Sessionmanager.Files) != null) {
            Glide.with(activity).load(pic).placeholder(R.drawable.dummy).into(image);
        }
        adapter = new CirclePostAdapter(activity, circlepostlistdata);
        adapter.setviewOnclick(this);
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

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording
        else if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
//            recordAudio();
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLARY_REQUEST);
    }

    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA_REQUEST);

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "MyPhoto.jpg");
        outPutfileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, TAKE_PIC);

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
                        popup(bitmap,"image");
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
        } else if (requestCode == CAMERA_REQUEST) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            icircle_post_pic.setImageBitmap(thumbnail);
//            post_files = saveImage(bitmap);
//            if (bitmap != null) {
//                popup(bitmap);
//            }
            Bitmap bitmap = null;
            String uri = outPutfileUri.toString();
            Log.e("uri-:", uri);
            Toast.makeText(this, outPutfileUri.toString(),Toast.LENGTH_LONG).show();

            //Bitmap myBitmap = BitmapFactory.decodeFile(uri);
            // mImageView.setImageURI(Uri.parse(uri));   OR drawable make image strechable so try bleow also

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                post_files = saveImage(bitmap);
            if (bitmap != null) {
                popup_camera(d,"image");
            }

//                ici.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    else if (requestCode == VIDEO_CAPTURE)
    {



//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            icircle_post_pic.setImageBitmap(thumbnail);
//            post_files = saveImage(bitmap);
//            if (bitmap != null) {
//                popup_video(bitmap);
            Uri selectedVideoUri = data.getData();
        selectpath = getPath(selectedVideoUri);
        popup_video(selectedVideoUri,"video",selectpath);
    }
        }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
    return filePath;
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
        ivActionImage = (ImageView) findViewById(R.id.ivActionImage);
//        swipelayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        ivActionImage.setImageResource(R.drawable.pencile);
        ileft = (ImageView) findViewById(R.id.ileft);
        title = (TextView) findViewById(R.id.title);
        lpost_upload_option = (LinearLayout) findViewById(R.id.lpost_upload_option);
        lpost_upload_option.setVisibility(View.GONE);
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
        ivActionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
                requestAudioPermissions();
                checkCameraPermission();
                lpost_upload_option.setVisibility(View.VISIBLE);
            }

        });

        ichatgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CirclePost.this, Circle_chat.class);
                i.putExtra("name",Loginname);
                i.putExtra("pic",pic);
                i.putExtra("circle_id",circle_id);
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
    }


    // TODO CIrcle PostList  API
    public void CirclePostPage(String index, String circle_id, String countries_id, String state_id) {
        Call<CirclePostListPojo> CirclePostListcall = apiInterface.circlepostlistpo(index, circle_id, countries_id, state_id);

        CirclePostListcall.enqueue(new Callback<CirclePostListPojo>() {
            @Override
            public void onResponse(Call<CirclePostListPojo> call, Response<CirclePostListPojo> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {

                    if (circlepostlistdata != null)
                        circlepostlistdata.clear();

                    circlepostlistdata.addAll(response.body().getData());
                    li_circle_post.setAdapter(adapter);
Log.e("POST LIST",new Gson().toJson(circlepostlistdata));
                    if (adapter != null)
                        adapter.notifyDataSetChanged();


                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CirclePostListPojo> call, Throwable t) {
            }
        });
    }


    // TODO CIrcle PostADD  API

    public void CirclePostAdd(String user_id, final String circle_id, final String countries_id, final String state_id, String comments, String Media_type, File post_files) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        RequestBody loginid = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody circleid = RequestBody.create(MediaType.parse("text/plain"), circle_id);
        RequestBody countriesid = RequestBody.create(MediaType.parse("text/plain"), countries_id);
        RequestBody stateid = RequestBody.create(MediaType.parse("text/plain"), state_id);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comments);
        RequestBody media_type = RequestBody.create(MediaType.parse("text/plain"), Media_type);
        if (post_files != null) {
            if(Media_type.equalsIgnoreCase("image"))
            {
                requestFile = RequestBody.create(MediaType.parse("image/*"), post_files);
            }
            else{
                requestFile = RequestBody.create(MediaType.parse("video/*"), post_files);
            }
            MultipartBody.Part userProfile = MultipartBody.Part.createFormData("post_files", post_files.getName(), requestFile);
            Call<CirclePostAddPojo> circlePostAddPCall = apiInterface.circlepostaddpojocall(loginid, circleid, countriesid, stateid, comment,media_type, userProfile);
            circlePostAddPCall.enqueue(new Callback<CirclePostAddPojo>() {
                @Override
                public void onResponse(Call<CirclePostAddPojo> uploadImageCall, Response<CirclePostAddPojo> response)
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        CirclePostPage(index, circle_id, countries_id,state_id);
                        dialog.dismiss();
                        sessionmanager.createSession_circlepostadddata(response.body());
                        Log.e("CIRCLEPOST LIST",":"+response.body().getData());
                        for (int i = 0; i < response.body().getData().getPostDetail().size(); i++) {
                            Glide.with(activity).load(response.body().getData().getPostDetail().get(i).getFiles());
                        }
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CirclePostAddPojo> call, Throwable t) {
                    dialog.dismiss();

//                    Helper.showToastMessage(context,t.getMessage());
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(CirclePost.this, Circles.class);
//        i.putExtra("Countries_id",countries_id);
//        i.putExtra("state_id",state_id);
//        startActivity(i);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakeVideoIntent();
            } else {
                checkCameraPermission();

            }
        }
    }

    public void popup(Bitmap bitmap,String media_type) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.circlepostcaption);

        final ImageView icircle_post_pic = (ImageView) dialog.findViewById(R.id.icircle_post_pic);
        final VideoView icircle_post_video = (VideoView) dialog.findViewById(R.id.icircle_post_video);
        ImageView button_chat_send = (ImageView) dialog.findViewById(R.id.button_caption_send);
        final EditText edit_caption = (EditText) dialog.findViewById(R.id.edit_caption);

        if(media_type.equalsIgnoreCase("image"))
        {
            icircle_post_video.setVisibility(View.GONE);
            icircle_post_pic.setVisibility(View.VISIBLE);
        }
        else
        {
            icircle_post_video.setVisibility(View.VISIBLE);
            icircle_post_pic.setVisibility(View.GONE);
        }
        icircle_post_pic.setImageBitmap(bitmap);
        fileGallery = new File(post_files);
        dialog.show();

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (icircle_post_pic != null) {
                    CirclePostAdd(user_id,
                            circle_id,
                            getIntent().getStringExtra("Country id"),
                            getIntent().getStringExtra("State id"),
                            edit_caption.getText().toString(),
                            "image",
                            fileGallery);
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    public void popup_video(Uri uri,String media_type,String selectpath) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.circlepostcaption);

        final VideoView icircle_post_video = (VideoView) dialog.findViewById(R.id.icircle_post_video);
        final ImageView icircle_post_pic = (ImageView) dialog.findViewById(R.id.icircle_post_pic);
        ImageView button_chat_send = (ImageView) dialog.findViewById(R.id.button_caption_send);
        final EditText edit_caption = (EditText) dialog.findViewById(R.id.edit_caption);

        if (media_type.equalsIgnoreCase("video"))
        {
            icircle_post_video.setVisibility(View.VISIBLE);
            icircle_post_pic.setVisibility(View.GONE);
        }
        else
        {
            icircle_post_video.setVisibility(View.GONE);
            icircle_post_pic.setVisibility(View.VISIBLE);
        }

        icircle_post_video.setVideoURI(uri);
        icircle_post_video.setMediaController(new MediaController(this));
        icircle_post_video.start();
        fileGallery = new File(selectpath);
        dialog.show();

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (icircle_post_video != null) {
                    CirclePostAdd(sessionmanager.getValue(Sessionmanager.Id),
                            sessionmanager.getValue(Sessionmanager.CircleId),
                            getIntent().getStringExtra("Country id"),
                            getIntent().getStringExtra("State id"),
                            edit_caption.getText().toString(),
                            "video",
                            fileGallery);
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    public void popup_camera(Drawable d,String media_type) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.circlepostcaption);

        final ImageView icircle_post_pic = (ImageView) dialog.findViewById(R.id.icircle_post_pic);
        final VideoView icircle_post_video = (VideoView) dialog.findViewById(R.id.icircle_post_video);
        ImageView button_chat_send = (ImageView) dialog.findViewById(R.id.button_caption_send);
        final EditText edit_caption = (EditText) dialog.findViewById(R.id.edit_caption);

        if(media_type.equalsIgnoreCase("image"))
        {
            icircle_post_video.setVisibility(View.GONE);
            icircle_post_pic.setVisibility(View.VISIBLE);
        }
        else
        {
            icircle_post_video.setVisibility(View.VISIBLE);
            icircle_post_pic.setVisibility(View.GONE);
        }
        icircle_post_pic.setImageDrawable(d);
//        icircle_post_pic.setImageBitmap(bitmap);
        fileGallery = new File(post_files);
        dialog.show();

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (icircle_post_pic != null) {
                    CirclePostAdd(user_id,
                            circle_id,
                            getIntent().getStringExtra("Country id"),
                            getIntent().getStringExtra("State id"),
                            edit_caption.getText().toString(),
                            "image",
                            fileGallery);
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void OnClicked(Integer position, String cPostPhoto) {

        switch (cPostPhoto) {
            case "cPostphoto":
                startNewActivity(position);
                break;
            case "pLikes":
                likeActivity(position);
                break;
            case "pUnLikes":
                unlikeActivity(position);
                break;
            case "cPostchannel":
                channelActivity(position);
                break;
            case "pcomments":
                CommentsActivity(position);
                break;
            case "pfollow":
                followingActivity(position);
                break;
            case "pUnfollow":
                unfollowActivity(position);
        }
    }

    private void unfollowActivity(Integer position) {
        if(Constants.followerlistData != null && Constants.followerlistData.size() !=0 ){
            followerdata_id = Constants.followerlistData.get(position).getFollower().getId();
            Unfollow(followerdata_id);
        }
    }

    private void followingActivity(Integer position) {
        follower_id = circlepostlistdata.get(position).getUser().getId();
        following(user_id, follower_id);

    }

    private void CommentsActivity(Integer position) {
        Intent it = new Intent(activity, Circle_comment.class);
        it.putExtra("Likes", circlepostlistdata.get(position).getPostlike());
        activity.startActivity(it);
        activity.finish();
    }

    private void channelActivity(Integer position) {
        Intent it = new Intent(activity, PlayGridview.class);
        activity.startActivity(it);
        activity.finish();
    }

    private void unlikeActivity(Integer position) {
        post_id = circlepostlistdata.get(position).getPost().getId();
        unlike(user_id, post_id);
    }

    private void likeActivity(Integer position) {
        post_id = circlepostlistdata.get(position).getPost().getId();
        like(user_id, post_id);
    }

    private void startNewActivity(Integer position) {
        Intent it = new Intent(activity, Circleprofilepic.class);
        it.putExtra("pic", circlepostlistdata.get(position).getPostFile().get(0).getFiles());
        it.putExtra("like", circlepostlistdata.get(position).getPostlike());
        activity.startActivity(it);
    }

    // TODO : API CALLING LIKE
    public void like(String user_id, String post_id) {
        Call<Like> registration = apiInterface.likepojo(user_id, post_id);
        registration.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> registrationCall, Response<Like> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    Log.e("LIKE RESPONSE ", ":" + response.body());
                    CirclePostPage(index, circle_id, CirclePost.countries_id, CirclePost.state_id);
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Like> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //TODO Call Api For unLike
    public void unlike(String user_id, String post_id) {
        Call<Unlike> unfollowCall = apiInterface.unlikepojo(user_id, post_id);
        unfollowCall.enqueue(new Callback<Unlike>() {
            @Override
            public void onResponse(Call<Unlike> registrationCall, Response<Unlike> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    CirclePostPage(index, circle_id, CirclePost.countries_id, CirclePost.state_id);
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Unlike> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // TODO : CAll API For Following
    public void following(final String user_id, String follower_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<Following> followingCall = apiInterface.followingpojo(user_id, follower_id);
        followingCall.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> followingCall1, Response<Following> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        dialog.dismiss();
//                        sessionmanager.createSession_circlefollowinglist(response.body().getData());
                        Log.e("FOLLOOWWING REPONSE", new Gson().toJson(response.body().getData()));
                        followerlist(user_id);
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO : CAll API For UnFollow
    public void Unfollow(String followerdata_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        //TODO Call Api For unfollow
        Call<Unfollow> followingCall = apiInterface.unfollowpojo(followerdata_id);
        followingCall.enqueue(new Callback<Unfollow>() {
            @Override
            public void onResponse(Call<Unfollow> followingCall1, Response<Unfollow> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        dialog.dismiss();
                        followerlist(user_id);
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Unfollow> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // TODO : CAll ApI For Following
    public void followerlist(String follower_id) {
        Call<Followerlist> followerlistCall = apiInterface.followerlistpojo(follower_id);

        followerlistCall.enqueue(new Callback<Followerlist>() {
            @Override
            public void onResponse(Call<Followerlist> call, Response<Followerlist> response) {
                if (response.body().getStatus().equals("Success")) {
                    if (Constants.followerlistData != null) {
                        Constants.followerlistData.clear();
                    }
                    Constants.followerlistData.addAll(response.body().getData());
                    CirclePostPage(index, circle_id, CirclePost.countries_id, CirclePost.state_id);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Followerlist> call, Throwable t) {
            }
        });
    }
}