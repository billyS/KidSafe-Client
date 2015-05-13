package uk.co.billy.gcm.client;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

public class MessageHandler extends IntentService {

    private String mes;
    private String mesT;
    private Handler handler;
    public static final int NOTIFICATION_ID = 1;
    private  NotificationManagerCompat mNotificationManager;
    NotificationCompat.Builder builder;
    
    public MessageHandler() {
        super("MessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
        mNotificationManager = NotificationManagerCompat.from(this);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        
       mesT = extras.getString("title");
       mes = extras.getString("message");
       
       sendNotification("Received: " + "\n"+ mesT + "\n"+ mes);
       
       showToast();
       Log.i("GCM", "Received : (" +messageType+")  "+extras.getString("title") + " " + extras.getString("message") + " " + extras.getString("action"));

       MessageReceiver.completeWakefulIntent(intent);

    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),mesT+" "+mes, Toast.LENGTH_LONG).show();
            }
         });

    }
    
    private void sendNotification(String msg) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyMenu.class), 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        
		Notification noti = new NotificationCompat.Builder(this)
       .setContentTitle("Proximity Alert!: ")
       .setContentText(msg)
       .setWhen(System.currentTimeMillis())
       .setDefaults(Notification.DEFAULT_VIBRATE)
       .setDefaults(Notification.DEFAULT_LIGHTS)
       .setLights(Color.WHITE, 1500, 1500)
       .setSmallIcon(R.drawable.kidsafe)
       .setAutoCancel(true)
       .setContentIntent(contentIntent)
       .build();
        
         /*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.kidsafe)
        .setContentTitle("KidSafe+ Proximity Alert!:")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);*/
		
        mNotificationManager.notify(NOTIFICATION_ID, noti);
    }
}