package com.islantraveller.Notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.R;
import com.islantraveller.SplashActivity;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by Maroof Ahmed Siddique on 12/13/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static String message = "";
    private static String value = "";

    // public static NotificationDTO notificationdto = null;

    Context ct = this;

    @Override
    public void handleIntent(Intent intent) {
        // sendNotification("hiii");
        super.handleIntent(intent);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {

            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("body");

            try {
                String reference_id = remoteMessage.getData().get("reference_id");
                if (reference_id != null && !reference_id.equalsIgnoreCase("")) {
                    AppSession.save(ct, Constants.DEAL_ID, reference_id);
                    AppSession.save(ct, Constants.FROM_NOTIFICATION, Constants.TRUE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            sendNotification("" + title, message);

        } catch (Exception e) {
            sendNotification("Island Traveller", "Island Traveller");
            e.printStackTrace();
        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String message) {
        try {
            Intent intent = null;

            intent = new Intent(this, SplashActivity.class);


            String strName = null;
            //  intent.putExtra(Constants.MESSAGE_VALUE, message);

            // intent.putExtra(Constants.FROMPUSH, user_type);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            String CHANNEL_ID = "my_channel_01";

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

            Notification notification = mBuilder.setSmallIcon(getNotificationIcon(mBuilder)).setTicker("LickR").setWhen(0)
                    .setAutoCancel(true)
                    .setSmallIcon(getNotificationIcon(mBuilder))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setChannelId(CHANNEL_ID)
                    .setContentText(message).build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int time = (int) System.currentTimeMillis();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // The id of the channel.
                CharSequence name = "Remedy";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(time, notification);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.app_logo;
        }
        return R.drawable.app_logo;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}