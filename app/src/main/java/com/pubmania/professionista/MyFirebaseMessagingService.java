package com.pubmania.professionista;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String tipo;
    Uri notificationSoundUri;
    String idPost;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        tipo = remoteMessage.getData().get("tipo");
        idPost = remoteMessage.getData().get("idPost");
        Log.d("ofnsdonf",tipo);
        SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("s1","true");
        String s2 = sharedPreferences.getString("s2","true");
        if(s2.equals("true")) {
            notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }else{
            notificationSoundUri = null;
        }
        if(s1.equals("true")) {
            if (tipo.equals("Recensione")&& sharedPreferences.getString("s5","true").equals("true")) {
                final Intent intent = new Intent(this, MainActivity.class);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationID = new Random().nextInt(3000);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.coupon_icon);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(12))
                        .setSmallIcon(R.drawable.coupon_icon)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent);

                //Set notification color to match your app color template
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setColor(getResources().getColor(R.color.gialloScuro));
                }
                notificationManager.notify(notificationID, notificationBuilder.build());
            }
            else if (tipo.equals("NuovoFollower") && sharedPreferences.getString("s3","true").equals("true")) {
                Log.d("nfjn","djsfjs");
                final Intent intent = new Intent(this, MainActivity.class);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationID = new Random().nextInt(3000);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                    Log.d("jdnasldna","dmoaslmkdk");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.follow_icon);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(12))
                        .setSmallIcon(R.drawable.follow_icon)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setSound(null)
                        .setVibrate(new long[]{Long.parseLong("0")})
                        .setContentIntent(pendingIntent);

                notificationManager.notify(notificationID, notificationBuilder.build());
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notificationzxczczxc";
        String adminChannelDescription = "Device to devie notificationzxczxcxz";
        SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
        String s2 = sharedPreferences.getString("s2","true");
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(idPost, tipo, NotificationManager.IMPORTANCE_DEFAULT);
        adminChannel.setShowBadge(false);
        adminChannel.enableLights(false);
        if(s2.equals("false")){
            adminChannel.setSound(null,null);
        }
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(false);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
