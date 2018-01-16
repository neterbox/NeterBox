package com.neterbox;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class HomePage extends Activity {

    TextView taddfriend, tlogout;
    LinearLayout lph;
    RelativeLayout relative_following, relative_follower, relative_frnd, relative_settings;
    ImageView iback1, iback2, iback3, iback4, ichat, icircle, iplay, profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        taddfriend = (TextView) findViewById(R.id.taddfriend);
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
        profile_image = (ImageView) findViewById(R.id.profile_image);
        ichat = (ImageView) findViewById(R.id.ichat);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);

       profile_image.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i = new Intent(HomePage.this, EditProfile.class);
                                                startActivity(i);
                                            }
       });

        lph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, UserProfile.class);
                startActivity(i);
            }
        });

        taddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchGroupFriend.class);
                startActivity(i);
            }
        });


        relative_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, FriendList.class);
                startActivity(i);
            }
        });

        relative_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SearchFollowings.class);
                startActivity(i);
            }
        });

        relative_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, search_followers.class);
                startActivity(i);
            }
        });

        relative_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Settings.class);
                startActivity(i);
            }
        });

        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, LoginPage.class);
                startActivity(i);
            }
        });

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Contactsforoneononechat.class);
                startActivity(i);
            }
        });

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, Circles.class);
                startActivity(i);
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, PlayGridview.class);
                startActivity(i);
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
    }
}
//
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(HomePage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    profile_image.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(HomePage.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            profile_image.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
//            Toast.makeText(HomePage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//}