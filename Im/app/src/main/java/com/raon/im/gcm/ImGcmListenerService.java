package com.raon.im.gcm;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.raon.im.application.MainActivity;
import com.raon.im.application.TabMainActivity;
import com.raon.lee.im.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by JiSoo on 2016-02-05.
 */
public class ImGcmListenerService extends GcmListenerService {
    private static final String TAG = "ImGcmListenerService";

    /**
     *
     * @param from get SenderID
     * @param data set의 형태로 GCM으로부터 받은 데이타의 payload
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString("title");
        String message = data.getString("message");
        String reqData = data.getString("reqData");

        if(reqData == null) {
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Message: " + message);

            // GCM으로 받은 메세지를 디바이스에 알려주는 sendNotification()을 호출한다.
            sendNotification(title, message);
        } else {
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Message: " + message);
            Log.d(TAG, "reqData: " + reqData);

            sendNotification(title, message, reqData);
        }

        Intent intent = new Intent(this, GcmPopupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다.
     * @param title
     * @param message
     */
    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class); // 여기 수정 요망
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.im_logo) // Image setting!!
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(String title, String message, String reqData) {
        Intent intent = new Intent(this, TabMainActivity.class); // 여기 수정 요망
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        try {
            JSONObject jsonObject = new JSONObject(reqData);


            String name = jsonObject.getString("name");
            String age = jsonObject.getString("age");

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.im_logo) // Image setting!!
                    .setContentTitle(title)
                    .setContentText(message + reqData)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
