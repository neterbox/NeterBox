package com.neterbox.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neterbox.R;
import com.neterbox.qb.callback.QbEntityCallbackTwoTypeWrapper;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by DeLL on 18-01-2018.
 */

public class Helper {
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }


    public static ProgressDialog showProgressDialog(Context context) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        return dialog;
    }

    /**
     * @param message
     * @return TODO for Display Toast Message
     */
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showSnackBar(LinearLayout ll, String msg)
    {
        Snackbar snackbar = Snackbar
                .make(ll, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ef3026"));
        snackbar.show();
    }

    public static void displayPopupImageLarge(final Context context, String imageUrl, Activity activity) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.popup_image_large, null);

        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mPopupWindow.setFocusable(true);
        mPopupWindow.update();

//          LayoutInflater inflat = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//        final View customVideoView = inflat.inflate(R.layout.popup_image_large, null);
//        final PopupWindow mPopupWindow = new PopupWindow(
//                customView,
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//

        ImageView ivImage = (ImageView) customView.findViewById(R.id.ivImage);
        Button btn_ok = (Button) customView.findViewById(R.id.btn_ok);
        FrameLayout frameLayout = (FrameLayout) customView.findViewById(R.id.frameLayout);

        Glide.with(context).load(imageUrl).placeholder(R.drawable.bg)
//                .thumbnail(Glide.with(context).load(response.body().getData().get(0).getProfile_pic()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mProgressDialog.dismiss();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressDialog.dismiss();
                        return false;
                    }
                })
                .fitCenter().into(ivImage);


        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);
    }
     /* TODO for drawer screen height/width set*/

    public static DisplayMetrics getScreenWidthHieght(Context context) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.e("height", height + "");
        Log.e("width", width + "");

        return displaymetrics;
    }


    /*TODO For Video Attachment*/
    public void loadFileAsAttachmentVideo(File file, QBEntityCallback<QBAttachment> callback,
                                          QBProgressCallback progressCallback) {

        try {
            QBContent.uploadFileTask(file, true, null, progressCallback).performAsync(
                    new QbEntityCallbackTwoTypeWrapper<QBFile, QBAttachment>(callback) {
                        @Override
                        public void onSuccess(QBFile qbFile, Bundle bundle) {
                            QBAttachment attachment = new QBAttachment(QBAttachment.VIDEO_TYPE);
                            attachment.setId(qbFile.getId().toString());
                            attachment.setUrl(qbFile.getPublicUrl());
                            callback.onSuccess(attachment, bundle);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void checkStoragePermission(final Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Storage Permission Needed")
                        .setMessage("This app needs the Storage permission, please accept to use storage functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        Constants.MY_PERMISSIONS_REQUEST_GALLARY);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_GALLARY);
            }
        }
    }

    public static void checkCameraPermission(final Context context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)) {

                // Show an explanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Storage Permission Needed")
                        .setMessage("This app needs the Storage permission, please accept to use storage functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{android.Manifest.permission.CAMERA},
                                        Constants.MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA},
                        Constants.MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }


    public static void requestAudioPermissions(final Context context) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    android.Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(context, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        Constants.MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        Constants.MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission((Activity)context,
                android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
//            recordAudio();
        }
    }

    /**
     * returning image / video
     */
    public static File createImageFile() throws IOException {
        File myDir = null;
        File file = null;
        myDir = new File(Environment.getExternalStorageDirectory() + File.separator + "protalkUser" + File.separator + "images");

        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        file = new File(myDir, "temp_ " + System.currentTimeMillis() + ".jpeg");
        Log.e("file", file.getAbsolutePath() + "");
        return file;
    }

    /*public static boolean Fileaccept(File file) {
        for (String extension : Constants.cvFileExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }*/

    // Method that deocde uri into bitmap. This method is necessary to deocde
    // large size images to load over imageview
    public static Bitmap decodeUri(Context context, Uri uri,
                                   final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, o2);
    }
    public static boolean checkSignIn() {
        return QBSessionManager.getInstance().getSessionParameters() != null;
    }

    public static void hideSoftKeyboard(Context context) {
        if(((Activity)context).getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSnackBar(RelativeLayout ll, String msg)
    {
        Snackbar snackbar = Snackbar
                .make(ll, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ef3026"));
        snackbar.show();
    }

    private static boolean isPanelShown(RelativeLayout lpost_upload_option) {
        return lpost_upload_option.getVisibility() == View.VISIBLE;
    }

    public static void slideUpDown(Context context, RelativeLayout lpost_upload_option) {
        if (!isPanelShown(lpost_upload_option)) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(context,
                    R.anim.bottom_up);

            lpost_upload_option.startAnimation(bottomUp);
            lpost_upload_option.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(context,
                    R.anim.bottom_down);

            lpost_upload_option.startAnimation(bottomDown);
            lpost_upload_option.setVisibility(View.GONE);
        }
    }
}
