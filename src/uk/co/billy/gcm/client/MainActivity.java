package uk.co.billy.gcm.client;

import java.util.ArrayList;
import java.util.Set;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private GoogleMap map;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
		
		populateMap();
		//map.setMyLocationEnabled(true);
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.mymenu, menu);
			return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.aboutUs:
			Intent intent = new Intent(MainActivity.this, AboutUs.class);
			startActivity(intent);
			
			break;
		case R.id.preferences:
			
			break;
		case R.id.exit:
			finish();
			break;
		}
	
		return super.onOptionsItemSelected(item);
	}
	
	private void populateMap() {		
		if(getIntent().hasExtra("locations")) { 
			 
			String msg = "";
            Bundle b = getIntent().getBundleExtra("locations");
            ArrayList<String> list = b.getStringArrayList("locations"); 
			for(int i =0; i < list.size(); i++) {
			
				String[] tempLocation = list.get(i).split(",");
				
				String[] Jlongitude = tempLocation[1].split(":");
				String actualLongitide = Jlongitude[1];
				actualLongitide = actualLongitide.replace("\"", "");
				
				String[] Jlatitude = tempLocation[2].split(":");
				String actualLatitide = Jlatitude[1];
				actualLatitide = actualLatitide.replace("\"", "");
				
				//Log.i("INFO", actualLongitide + " " + actualLatitide);
				
				double longitude = Double.valueOf(actualLongitide);
				double latitude = Double.valueOf(actualLatitide);
				
				LatLng myLocation = new LatLng(latitude, longitude);
				if (map!=null){
					 map.addMarker(new MarkerOptions()
				        .position(myLocation));
						 
						// Move the camera instantly to hamburg with a zoom of 15. 
						   
						    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

						    // Zoom in, animating the camera.
						    map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
				}
				
			}
			Log.i("INFO", "Added locations to map");                  
	}else{
		Log.e("Fail", "no bundle extra");
	}
		}
	                
}