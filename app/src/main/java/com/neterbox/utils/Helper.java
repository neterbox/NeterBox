package com.neterbox.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
