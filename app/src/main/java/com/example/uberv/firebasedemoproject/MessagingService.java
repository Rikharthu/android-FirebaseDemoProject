package com.example.uberv.firebasedemoproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MessagingService extends FirebaseMessagingService {
    public static final String LOG_TAG=MessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(LOG_TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(LOG_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String message = remoteMessage.getNotification().getBody();
            showNotification(message);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        /*
        From: 130520863472
        Message data payload: {food=Hamburger}
        Message Notification Body: Lunch is ready
         */
    }

    private void showNotification(String msg){
        // Notification
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1488,intent,0);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Firebase notification!")
                .setContentText(msg)
                .setSmallIcon(R.drawable.common_ic_googleplayservices)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Chat",pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(666,notification);
    }

}
