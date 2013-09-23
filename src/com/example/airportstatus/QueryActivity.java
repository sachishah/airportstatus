package com.example.airportstatus;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

import com.example.airportstatus.models.AirportStatusLocation;
import com.example.airportstatus.models.TravelTimeEstimate;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class QueryActivity extends Activity implements Observer {
	private NetworkTaskCollection myTasks;
	private String airportCode;
	private Bundle result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		airportCode = getIntent().getStringExtra("airport_code");
		
		// Show the Up button in the action bar.
		setupActionBar();
		setupNetworkQueries();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.query, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void setupNetworkQueries() {
		// NetworkTaskCollection becomes ArrayList of NetworkTasks
		// NetworkTaskCollection is Observable
		// NetworkTask has success handler that pushes data to bundle
		
		
		
		
		myTasks = new NetworkTaskCollection();
		myTasks.addObserver(this);
		
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						int totalTripMins;
						try {
							totalTripMins = TravelTimeEstimate.parseDirections(response);
							myTasks.setResult(StatusKeys.KEY_TRAVEL_TIME_DRIVING, TravelTimeEstimate.getFormattedDuration(totalTripMins));
							myTasks.markTaskComplete();
							myTasks.checkTaskStatus();
						} catch (Exception e) {
							// !!!
							Log.e("NETWORK_TASKS", "what the crap");
							e.printStackTrace();
						}
					}
				};
			}
			
			@Override
			public void execute() {
				String origin = getCurrentLocation().toString();
				TravelTimeEstimate.getDrivingTime(origin, airportCode, this.handler);
			}
		});
		
		
/*
		myTasks.addTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				Log.d("TASK", "Task is running");
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				Log.d("TASK", "Second task completed with result " + result.toString());
				myTasks.markTaskComplete();
				myTasks.checkTaskStatus();
			}
		});
		*/
		myTasks.startAll();
	} 

	@Override
	public void update(Observable observable, Object response) {
		Log.d("TASK COMPLETE", response.toString());
		Bundle bundle = (Bundle) response;
		// Test here that the value returned from the observed NetworkTaskCollection 
		// is the one that signals success
		if (bundle.containsKey("success")) {
			Intent i = new Intent(this, StatusListActivity.class);
			bundle.putString("airportCode", airportCode);
			i.putExtra("data", bundle);
	    	startActivity(i);
	    	finish();
		} 
		
		// Otherwise, go back to the starting activity and show an error
		
	}
	
	private AirportStatusLocation getCurrentLocation() {
		try {
			SharedPreferences settings = getSharedPreferences(AirportStatusActivity.PREFS_NAME, MODE_PRIVATE);
			double lat = (double) settings.getFloat(AirportStatusActivity.PREFS_LATITUDE, -1);
			double lon = (double) settings.getFloat(AirportStatusActivity.PREFS_LONGITUDE, -1);
				
			if (lat < 0 || lon < 0) {
				throw new Exception("No location preferences have been set");
			}
			return new AirportStatusLocation(lat, lon);
		} catch (Exception e) {
			Log.e("LOCATION_PREFERENCES_ERROR", e.getMessage());
			LocationManager m = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			Location current = m.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (current == null) {
				return new AirportStatusLocation(37.76030, -122.41051);
			}
			
			updateLastLocationPreferences(current.getLatitude(), current.getLongitude());
			return new AirportStatusLocation(current.getLatitude(), current.getLongitude());
		}	
	}
	
	private void updateLastLocationPreferences(double lat, double lon) {
		SharedPreferences locationPrefs = getSharedPreferences(AirportStatusActivity.PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = locationPrefs.edit();
		editor.putFloat(AirportStatusActivity.PREFS_LATITUDE, (float) lat);
		editor.putFloat(AirportStatusActivity.PREFS_LONGITUDE, (float) lon);
		editor.commit();
	}
	
	private class HttpResponseTask extends AsyncTask<Void, Void, Bundle> {

		@Override
		protected Bundle doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	} 
}
