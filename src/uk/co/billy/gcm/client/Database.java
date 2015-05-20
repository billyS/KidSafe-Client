package uk.co.billy.gcm.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
 
public class Database{
	
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	String toReturn=null;
	String msg;
	Map<String, String> locations=null;
 
    public Database(){ 
    	
    }

  
    public void insert(String longitude, String latitude, int radius)
    {
    	final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
    	nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
    	nameValuePairs.add(new BasicNameValuePair("latitude" , latitude));
    	nameValuePairs.add(new BasicNameValuePair("radius"   , String.valueOf(radius)));
    	
    	Runnable runnable = new Runnable() {
			public void run() {
		    	try {
						HttpClient httpclient = new DefaultHttpClient();
				        HttpPost httppost = new HttpPost("http://itsuite.it.brighton.ac.uk/ws52/postGeofence.php"); 
				        
				        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				        HttpResponse response = httpclient.execute(httppost);
				        HttpEntity entity = response.getEntity();
				        is = entity.getContent();
				        
				        Log.i("INFO", "pass 1 insert location connection success ");
				        
				        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		            	StringBuilder sb = new StringBuilder();
		            	while ((line = reader.readLine()) != null) {
		            		Log.e("INFO", "read responce from location insert: " + line);
		            		sb.append(line + "\n");
		            	}
		            	
		            	JSONObject json_data = new JSONObject(result);
		            	//code=(json_data.getInt("code"));
		            	is.close();
		            	result = sb.toString();
		            	Log.e("INFO", "convert to JSON  connection success ");
		            
		    	}catch(Exception e) {
		        	Log.e("Fail","failed to convert to JSON" + e.toString());
		    	}     
			}
		};
		new Thread(runnable).start();	
    }
    
    public void insert(String regId, String deviceImei) {
    	
	final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("regId",regId));
	nameValuePairs.add(new BasicNameValuePair("deviceImei",deviceImei));
	
	Runnable runnable = new Runnable() {
		
		public void run() {
	    	try {
					HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://itsuite.it.brighton.ac.uk/ws52/postRegId.php");
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        is = entity.getContent();
			        
			        Log.i("INFO", "sent regId to server");
			        
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            	StringBuilder sb = new StringBuilder();
	            	while ((line = reader.readLine()) != null) {
	            		Log.e("line read from regId insert resonce: ", line);
	            		sb.append(line + "\n");
	            	}
	            	
	            	JSONObject json_data =null;
	            	result = sb.toString();
	            	JSONArray jObj = new JSONArray(result);
	            	json_data = jObj.getJSONObject(0);
	            	msg = json_data.getString("reg_id");
		        
	            	is.close();
	            	Log.e("INFO", "pass 2 success. converted responce to JSON");
	            
	    	}catch(Exception e) {
	        	Log.e("Fail","failed to convert the insert regId responce to JSON: "+ e.toString());
	    	}     
		}
	};
	new Thread(runnable).start();	
    }
}