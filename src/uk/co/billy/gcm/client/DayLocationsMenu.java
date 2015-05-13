package uk.co.billy.gcm.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class DayLocationsMenu extends Activity{

		ExpandableListAdapter listAdapter;
	    ExpandableListView expListView;
	    List<String> listDataHeader;
	    HashMap<String, List<String>> listDataChild;
	    
	    private Bundle b = new Bundle(); 
		private String result= null;
		private String line = null;
		private JSONArray locations = null;
	    private ArrayList<String> list = null;
	    private InputStream is = null;
	    boolean toReturn = false;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.daylocation_menu);
	        
	        list = new ArrayList<String>();
			
	        prepareListData();
		
	        // get the listview
	        expListView = (ExpandableListView) findViewById(R.id.listView2);
	 
	        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	 
	        // setting list adapter
	        expListView.setAdapter(listAdapter);
	        
				
				expListView.setOnChildClickListener(new OnChildClickListener() {
		        	 
		            @Override
		            public boolean onChildClick(ExpandableListView parent, View v,
		                    int groupPosition, int childPosition, long id) {
		            	
		            	Intent intent = new Intent(DayLocationsMenu.this, MainActivity.class);
		            	
		            	switch (childPosition) {
		            	case 0:
		            		getLocations("mon");
							 while(locations==null) {
							
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
						    //Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
									
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 1:
							getLocations("tue");
							 while(locations==null) {
							
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
						    //Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
									
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 2:
							getLocations("wed");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
							
						case 3:
							getLocations("wed");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 4:
							getLocations("thu");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 5:
							getLocations("fri");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 6:
							getLocations("sat");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						case 7:
							getLocations("sun");
							 while(locations==null) {
									
							}
							//Log.i("INFO",locations.getJSONObject(0).toString());
							//Log.i("INFO",String.valueOf(locations.length()));
							for(int i =0;i<=locations.length();i++) {
								String location;
								try {
									location = locations.getJSONObject(i).toString();
									list.add(location);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										
							}
							b.putStringArrayList("locations", list);
							
			                intent.putExtra("locations", b);
			    		    startActivity(intent);
							break;
						}
		                return false;
		            }
		        });
	    }
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.mymenu, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case R.id.aboutUs:
				Intent intent = new Intent(this, AboutUs.class);
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
	 
	    /*
	     * Preparing the list data
	     */
	    private void prepareListData() {
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	 
	        // Adding child data
	        listDataHeader.add("Day to Show");
	        
	     // Adding child data
	        List<String> dayOptions = new ArrayList<String>();
	        dayOptions.add("Monday");
	        dayOptions.add("Tuesday");
	        dayOptions.add("Wednesday");
	        dayOptions.add("Thursday");
	        dayOptions.add("Friday");
	        dayOptions.add("Saturday");
	        dayOptions.add("Sunday");
	        listDataChild.put(listDataHeader.get(0), dayOptions); // Header, Child data
	    }
	    
	    public void getLocations(String timePeriod){
	    	
			 new AsyncTask<String, Integer, String>() {
				 
				 @Override
		            protected String doInBackground(String... params) {
		                String msg = "";
		                try {
		                	final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 	
		                		nameValuePairs.add(new BasicNameValuePair("day",params[0]));
				
		                		HttpClient httpclient = new DefaultHttpClient();
						        HttpPost httppost = new HttpPost("http://itsuite.it.brighton.ac.uk/ws52/getDailyLocations.php");
						        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						        HttpResponse response = httpclient.execute(httppost);
						        HttpEntity entity = response.getEntity();
						        is = entity.getContent();
						        Log.i("INFO", "susseccfully requested daily locations from the Database DayLocations menu");
						        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				            	StringBuilder sb = new StringBuilder();
				            	
				            	while ((line = reader.readLine()) != null) {
				            		//Log.i("INFO", "line read from get locations responce: " +line);
				            		sb.append(line + "\n");
				            	}
				            	
				            	result = sb.toString();
				            	locations = new JSONArray(result);
				            	is.close();
				            	Log.i("INFO", "successfully converted get locations responce to JSON" + locations.getString(0));
		                } catch (IOException ex) {
		                    msg = "Error :" + ex.getMessage();

		                } catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                return result;
		            }
		        }.execute(timePeriod, null, null);
			
			}
	}
