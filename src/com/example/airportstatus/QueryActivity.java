package com.example.airportstatus;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.airportstatus.models.TravelTimeEstimate;
import com.loopj.android.http.JsonHttpResponseHandler;

public class QueryActivity extends Activity implements Observer {
	private NetworkTaskCollection myTasks;
	private String airportCode, airportIndex;
	private Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		
		Intent current = getIntent();
		airportCode = current.getStringExtra("airport_code");
		airportIndex = current.getStringExtra("airport_index");
		try {
			currentLocation = LocationPreferences.getLastLocationPreferences(this);
		} catch (Exception e) {
			Log.e("LOCATION_MISSING", e.getMessage());
		}
		
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
							myTasks.finishWithResult(StatusKeys.TRAVEL_TIME_DRIVING, TravelTimeEstimate.getFormattedDuration(totalTripMins));
						} catch (Exception e) {
							myTasks.finishOneTask();
							Log.e("TASK", "Task failed 1");							
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						myTasks.finishOneTask();
					}
				};
			}
			
			@Override
			public void execute() {
				TravelTimeEstimate.getDrivingTime(TravelTimeEstimate.getCoordinates(currentLocation), airportCode, this.handler);
			}
		});
		
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						int totalTripMins;
						try {
							totalTripMins = TravelTimeEstimate.parseDirections(response);
							myTasks.finishWithResult(StatusKeys.TRAVEL_TIME_TRANSIT, TravelTimeEstimate.getFormattedDuration(totalTripMins));
						} catch (Exception e) {
							myTasks.finishOneTask();
							Log.e("TASK", "Task failed 2 ");							
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						myTasks.finishOneTask();
					}
				};
			}
			
			@Override
			public void execute() {
				TravelTimeEstimate.getTransitTime(TravelTimeEstimate.getCoordinates(currentLocation), airportCode, this.handler);
			}
		});
		
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						String currentWeatherAtAirport;
						try {
							currentWeatherAtAirport = FlightStatsClient.getWeatherString(response);
						} catch (Exception e) {
							currentWeatherAtAirport = "";
						}
						try {
							Double tempC = FlightStatsClient.getTempCelsius(response); // Result only comes back in degrees Celsius
							Double tempF = (tempC * 1.8) + 32;
							myTasks.addResult(StatusKeys.TEMPERATURE, String.valueOf(tempF));
						} catch (Exception e) {
							Log.e("WEATHER", e.getMessage() + " " + response.toString());
						}
						myTasks.finishWithResult(StatusKeys.WEATHER, currentWeatherAtAirport);
					}
					
					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						Log.e("WEATHER", error.getMessage());
						myTasks.finishOneTask();
					}
				};
			}
			
			@Override
			public void execute() {
				FlightStatsClient.getWeatherConditions(airportCode, this.handler);
			}
		});
		
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						Log.d("DELAYS", response.toString());
						String[] outcomes = getResources().getStringArray(R.array.txtDelayLabels);
						try {
							int delaySeverityIndex = FlightStatsClient.getDelayIndex(response);
							Log.d("DELAY SEVERITY", outcomes[delaySeverityIndex]);
							myTasks.finishWithResult(StatusKeys.DELAYS, outcomes[delaySeverityIndex]);
						} catch (Exception e) {
							myTasks.finishWithResult(StatusKeys.DELAYS, getResources().getString(R.string.txtDelaysError));
						}
					}
					
					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						Log.e("DELAYS", error.getMessage());
						myTasks.finishOneTask();
					}
				};
			}
			
			@Override
			public void execute() {
				FlightStatsClient.getDelayDegree(airportCode, this.handler);
			}
		});
		
		myTasks.startAll();
	} 

	@Override
	public void update(Observable observable, Object response) {
		Bundle bundle = (Bundle) response;
		// Test here that the value returned from the observed NetworkTaskCollection 
		// is the one that signals success
		if (bundle.containsKey("success")) {
			Intent i = new Intent(this, StatusListActivity.class);
			bundle.putString("origin", currentLocation.toString());
			bundle.putString("airport_code", airportCode);
			bundle.putString("airport_index", airportIndex);
			i.putExtra("data", bundle);
	    	startActivity(i);
	    	finish();
		} 
		
		// Otherwise, go back to the starting activity and show an error
		
	}
}
