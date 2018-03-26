package com.neterbox.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neterbox.R;

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
    public static ProgressDialog showProgressDialog(Context context)
    {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        return dialog;
    }
    /**
     * @param message
     * @return
     * TODO for Display Toast Message
     */
    public static void showToastMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayPopupImageLarge(final Context context, String imageUrl,Activity activity)
    {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        LayoutInflater inflater = (LayoutInflater)context. getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.popup_image_large, null);

        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mPopupWindow.setFocusable(true);
        mPopupWindow.update();

        ImageView ivImage=(ImageView)customView.findViewById(R.id.ivImage);
        Button btn_ok=(Button)customView.findViewById(R.id.btn_ok);
        FrameLayout frameLayout=(FrameLayout)customView.findViewById(R.id.frameLayout);

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
                .fitCenter() .into(ivImage);


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
}
