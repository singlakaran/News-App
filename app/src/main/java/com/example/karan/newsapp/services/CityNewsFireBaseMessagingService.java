package com.example.karan.newsapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import com.example.karan.newsapp.Activity.MainActivity;
import com.example.karan.newsapp.R;
import com.example.karan.newsapp.ReadNewsActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class CityNewsFireBaseMessagingService extends FirebaseMessagingService {

    TaskStackBuilder stackBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            String message = object.getString("message");
            String title = object.getString("title");
            String newsId = object.getString("news_id");

           navigateToReadNewsActivity(message, title, newsId);


        }  catch (Exception e) {
            System.out.println("exception");
        }
    }

    private void navigateToReadNewsActivity(String message, String title, String newsId) {

        Intent intent = new Intent(this, ReadNewsActivity.class);
        intent.putExtra("news_id",Integer.valueOf(newsId));
        stackBuilder.addNextIntent(intent);
        createPendingIntent(title, message);

    }

    private void addMainActivityToParent() {
        Intent parentIntent = new Intent(this, MainActivity.class);
        stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(parentIntent);

    }

    private void createPendingIntent(String title, String message) {

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.news_image);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_notif)
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.white))
                .setContentTitle(title)
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + random.nextInt(1000 - 900);
            notificationManager.notify(m
                    /*ID of notification */
                    , notificationBuilder.build());
        }


    }

    }
