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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class SplashActivity extends Activity {
	
	 private GoogleCloudMessaging gcm;
	 private String regid;
	 private String PROJECT_NUMBER = "135447264476";
	 String result= null;
	 String line = null;
	 boolean toReturn=false;
	 private InputStream is = null;
	 private Database db = null;
	 private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	 private TelephonyManager tm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);//TODO change layout
        
        db = new Database();
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        
        Thread timer = new Thread(){
	        	public void run(){
		        		if(checkGooglePlayService()) { 
		        			checkGCMRegistration();   
		        		}
		        		Intent intent = new Intent(SplashActivity.this, MyMenu.class);
	        			startActivity(intent);
	        			finish();
	        	}	
        	};
        	timer.start();	
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		checkGooglePlayService();
	}
	
	private void checkGCMRegistration() {		
		 new AsyncTask<Void, Integer, String>() {

			 @Override
	            protected String doInBackground(Void... params) {
	                String msg = "";
	                try {
						HttpClient httpclient = new DefaultHttpClient();
				        HttpPost httppost = new HttpPost("http://itsuite.it.brighton.ac.uk/ws52/getRegId.php");
				        HttpResponse response = httpclient.execute(httppost);
				        HttpEntity entity = response.getEntity();
				        is = entity.getContent();
				        
				        Log.i("INFO", "susseccfully requested the regId from Database");
				        
				        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		            	StringBuilder sb = new StringBuilder();
		            	
		            	while ((line = reader.readLine()) != null) {
		            		//Log.i("INFO", "line read from get regId responce: " +line);
		            		sb.append(line + "\n");
		            	}
		            	
		            	result = sb.toString();
		            	//Log.i("INFO", "Reg ID is: " + msg);
		            	is.close();
		            	Log.i("INFO", "successfully converted SelectRegId responce to JSON");

	                } catch (IOException ex) {
	                    result = "Error :" + ex.getMessage();

	                }
	                return result;
	            }
			 
	            @Override
	            protected void onPostExecute(String msg) {
	            	if(verifyDevice(msg)==false){
	            		getRegId();
	            	}
	            }
	        }.execute(null, null, null);      
	}
	
	protected boolean verifyDevice(String result) {
		
		JSONArray jObj;
		JSONObject json_data;
		String deviceImei = tm.getDeviceId();
		String deviceId;
		boolean toReturn = false;
		if(result.equals("")){
			return false;
		} else{
			try {
				
				jObj = new JSONArray(result);
				
				for(int i=0; i < jObj.length(); i++) {
					
		    		json_data = jObj.getJSONObject(i);
		    		deviceId = json_data.getString("device_imei");
					
					if(deviceImei.equals(deviceId)) {
						toReturn = true;
						return toReturn;	
					}
					
		    	}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return toReturn;
	}

	private boolean checkGooglePlayService() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("Fail", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
		
	}

	
    public void getRegId(){
    	
    	String deviceImei = tm.getDeviceId();
    	
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER); 					// saving regId to database
                    db.insert(regid, params[0]);			 		 		
                    msg = "Device registered, registration ID=" + regid + " Device IMEI: " + params[0];
                    Log.i("GCM",  msg);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }
            
            @Override
            protected void onPostExecute(String msg) {
            	//Intent intent = new Intent(SplashActivity.this, MyMenu.class);
    			//startActivity(intent);
    			//finish();
            }
        }.execute(deviceImei, null, null);
    }
} 