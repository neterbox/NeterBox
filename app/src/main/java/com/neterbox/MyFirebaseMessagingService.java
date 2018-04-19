/*
package com.neterbox.HyperTrack;

*/
/*public class MyFirebaseMessagingService {*//*


import com.google.firebase.messaging.RemoteMessage;
import com.neterbox.utils.Constants;

public class MyFirebaseMessagingService extends HyperTrackFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            String sdkNotification = remoteMessage.getData().get(Constants.HT_SDK_NOTIFICATION_KEY);
            if (sdkNotification != null && sdkNotification.equalsIgnoreCase("true")) {
                */
/**
                 * HyperTrack notifications are received here
                 * Dont handle these notifications. This might end up in a crash
                 *//*

                return;
            }
        }
        // Handle your notifications here.
    }
}
*/
