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
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String tipo;
    Uri notificationSoundUri;
    String idPost;
    int notificationID;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        notificationID = new Random().nextInt(3000);
        tipo = remoteMessage.getData().get("tipo");


        idPost = remoteMessage.getData().get("idPost");
        Log.d("ofnsdonf",tipo);

        SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("s1","true");

        if(s1.equals("true")) {
            if (tipo.equals("Recensione")&& sharedPreferences.getString("s5","true").equals("true")) {
                final Intent intent = new Intent(this, MainActivity.class);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.review_icon);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationID))
                        .setSmallIcon(R.drawable.review_icon)
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

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                    Log.d("jdnasldna","dmoaslmkdk");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.follow_icon);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationID))
                        .setSmallIcon(R.drawable.follow_icon)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                notificationManager.notify(notificationID, notificationBuilder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                }
            }
            else if (tipo.equals("Coupon") && sharedPreferences.getString("s4","true").equals("true")) {
                Log.d("nfjn","djsfjs");
                final Intent intent = new Intent(this, MainActivity.class);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupChannels(notificationManager);
                    Log.d("jdnasldna","dmoaslmkdk");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.coupon_notifi_icon);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationID))
                        .setSmallIcon(R.drawable.coupon_notifi_icon)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                notificationManager.notify(notificationID, notificationBuilder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                }
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notificationzxczczxc";
        String adminChannelDescription = "Device to devie notificationzxczxcxz";
        SharedPreferences sharedPreferences = getSharedPreferences("notifiche",MODE_PRIVATE);
        String s2 = sharedPreferences.getString("s2","true");
        String s4 = sharedPreferences.getString("s4","true");
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(String.valueOf(notificationID), "tipo", NotificationManager.IMPORTANCE_HIGH);


        if(tipo.equals("Follower")){
        if(s2.equals("true")){
            adminChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            Log.d("kjanfda","Higjt");
        }else{
            adminChannel.setImportance(NotificationManager.IMPORTANCE_LOW);
            adminChannel.setSound(null,null);
            Log.d("kjanfda","Low");


        }
        }
        else if(tipo.equals("Coupon")){
            if(s4.equals("true")){
                adminChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
                Log.d("kjanfda","Higjt");
            }else{
                adminChannel.setImportance(NotificationManager.IMPORTANCE_LOW);
                Log.d("kjanfda","Low");
                adminChannel.setSound(null,null);



            }
        }
        else if(tipo.equals("Recensione")){
            if(s4.equals("true")){
                adminChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
                Log.d("kjanfda","Higjt");
            }else{
                adminChannel.setImportance(NotificationManager.IMPORTANCE_LOW);
                Log.d("kjanfda","Low");
                adminChannel.setSound(null,null);



            }
        }


        adminChannel.setShowBadge(true);
        adminChannel.enableLights(true);

        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
