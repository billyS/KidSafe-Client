package uk.co.billy.gcm.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
    private NotificationCompat.Builder builder;
    private JSONObject location = null;
    
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
       StringBuilder sb = new StringBuilder();
       try {
			sb.append(getCurrentLocation().getString("longitude"));
			sb.append(" ");
		    sb.append(getCurrentLocation().getString("latitude"));
		    sendNotification(sb.toString());
       } catch (JSONException e) {
		e.printStackTrace();
       }
      
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
    	Context context = getApplicationContext();
    	
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyMenu.class), 0);
        String test[] = msg.split(" ");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:" + test[0]+","+test[1]);
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent = PendingIntent.getActivity(this, 0, mapIntent, 0);
        
        
     // Create the action
        NotificationCompat.Action action =  new NotificationCompat.Action.Builder(R.drawable.kidsafe, getString(R.string.app_name), 
        		mapPendingIntent)
                .build();

        
        
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
       .extend(new NotificationCompat.WearableExtender())
       .addAction(action)
       .build();
       
        mNotificationManager.notify(NOTIFICATION_ID, noti);
    }
    
    public JSONObject getCurrentLocation(){
		 new AsyncTask<String, Integer, String>() { 
			 @Override
	            protected String doInBackground(String... params) {
	                String msg = "";
	                HttpClient httpclient =null;
	                HttpPost httppost = null;
	                HttpResponse response = null;
	                HttpEntity entity = null;
	                BufferedReader reader =null;
	                StringBuilder sb =null;
	                InputStream is = null;
	                String line="";
	                JSONArray locations = null;
	                try {
							httpclient = new DefaultHttpClient();
					        httppost = new HttpPost("http://itsuite.it.brighton.ac.uk/ws52/getCurrentLocation.php");
					        response = httpclient.execute(httppost);
					        entity = response.getEntity();
					        is = entity.getContent();
					        Log.i("INFO", "susseccfully requested locations from the Database MyMenu");
					        reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			            	sb = new StringBuilder();
			            	
			            	while ((line = reader.readLine()) != null) {
			            		sb.append(line + "\n");
			            	}
			            	
			            	msg = sb.toString();
			            	locations = new JSONArray(msg);
			            	location = locations.getJSONObject(0);
			            	is.close();
	                } catch (IOException ex) {
	                    msg = "Error :" + ex.getMessage();

	                } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                return msg;
	            }
	        }.execute(null, null, null);
		  return location;
		}
}