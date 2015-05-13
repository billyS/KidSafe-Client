package uk.co.billy.gcm.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class MessageReceiver extends WakefulBroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
	  Log.i("INFO", "Added Wakefull brodcast reciever");
      ComponentName comp = new ComponentName(context.getPackageName(),
              MessageHandler.class.getName());

      // Start the service, keeping the device awake while it is launching.
      startWakefulService(context, (intent.setComponent(comp)));
      setResultCode(Activity.RESULT_OK);
  }

} 