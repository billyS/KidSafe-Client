package uk.co.billy.gcm.client;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class GeofenceActivity extends Activity {

	private GoogleMap map;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private SeekBar slider = null;
	int radius =0;
	private Button saveGeofence = null;
	private LatLng geoFencePoint = null;
	private Database db = null;
	private Marker myMarker=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geofence_activity);
		
		saveGeofence = (Button) findViewById(R.id.saveGeofence);
		slider 		 = (SeekBar) findViewById(R.id.radiusSelector);
		map 		 = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		db 			 = new Database();
		LatLng roughLocation = new LatLng(50.8756, 0.0179);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(roughLocation, 15));
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		//map.setOnMapLongClickListener(MyListener);
		map.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng arg0) {
				geoFencePoint = arg0; 				//to remember the point for saveButton
				myMarker = map.addMarker(new MarkerOptions()
		        .position(arg0));
			}
		});
		
		
		slider.setMax(1000);
		slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
				int pointRadius= seekBar.getProgress();
				if(geoFencePoint==null) {
					Toast.makeText(getApplicationContext(), "Please Select a Location on the Map", Toast.LENGTH_SHORT).show();;
				}else{
					CircleOptions circleOptions3 = new CircleOptions()
					.center(geoFencePoint)
					.radius(pointRadius)
					.fillColor(0x40ff0000)
					.strokeColor(Color.TRANSPARENT)
					.strokeWidth(2);
					
					Circle circle = map.addCircle(circleOptions3);
					myMarker.setVisible(false);// try this instead of removing it so user can see what they previously chosen
					//myMarker.remove();
				}	
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				radius = progress;
				Log.i("INFO", "from progress changed"+String.valueOf(progress));
			}
		});
		
		saveGeofence.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.insert(String.valueOf(geoFencePoint.longitude) , String.valueOf(geoFencePoint.latitude), radius);
				Toast.makeText(getApplicationContext(), "Set Restricted Zone: "+ geoFencePoint,Toast.LENGTH_SHORT).show();
			}
		});		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.aboutUs:
			Intent intent = new Intent(GeofenceActivity.this, AboutUs.class);
			startActivity(intent);
			break;
		case R.id.preferences:
			break;
		case R.id.exit:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}