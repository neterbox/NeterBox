package com.neterbox.qb.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.neterbox.R;
import com.neterbox.utils.Constants;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.ServiceZone;
import com.quickblox.messages.services.SubscribeService;

/**
 * Created by harshshah on 12/28/17.
 */

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("token", "Refreshed token: " + refreshedToken);

        QBSettings.getInstance().setAccountKey(getResources().getString(R.string.account_key));
        QBSettings.getInstance().init(this, getResources().getString(R.string.application_id),
                getResources().getString(R.string.authorization_key), getResources().getString(R.string.authorization_secret));
        QBSettings.getInstance().setEndpoints("https://api.quickblox.com", "chat.quickblox.com", ServiceZone.PRODUCTION);
        QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);

        final QBChatService chatService= QBChatService.getInstance();
        chatService.setDebugEnabled(true); // enable chat logging


        QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
        chatServiceConfigurationBuilder.setSocketTimeout(Constants.SOCKET_TIMEOUT); //Sets chat socket's read timeout in seconds
        chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
        chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
        QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);

        SubscribeService.subscribeToPushes(this, false);


        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(refreshedToken);
    }

}



