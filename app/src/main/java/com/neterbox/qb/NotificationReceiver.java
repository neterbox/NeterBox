package com.neterbox.qb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.neterbox.HomePage;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.sample.core.utils.Toaster;


/**
 * Created by harshshah on 12/28/17.
 */

public class NotificationReceiver extends BroadcastReceiver {
    Sessionmanager sessionManager;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        sessionManager = new Sessionmanager(context);
        String message = intent.getStringExtra("message");

        Log.e("NOTIF", "Receiving message: " + message);
        if (sessionManager.getValue(Sessionmanager.Type).equalsIgnoreCase("user")) {
            Intent i = new Intent(context, HomePage.class);
            i.putExtra("message", message);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(context, Consultant_HomeActivity.class);
//            i.putExtra("message", message);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
        }
    }
}
