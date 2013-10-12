package com.example.airportstatus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LandingActivity extends Activity {
	int airportIndex;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		context = this;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		getLocation();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}
	
	private void getLocation() {
		if (isOnline()) {
			LocationResult locationResult = new LocationResult() {
				@Override
				public void receivedLocation(Location location) {
					Log.d("LOCATION_RECEIVED", location.toString());
					LocationPreferences.setLastLocationPreferences(context, location.getLatitude(), location.getLongitude());
					Airport airportDetails = findClosestAirport(location);
					if (airportDetails.code != null) {
				    	Intent i = new Intent(context, QueryActivity.class);
				    	i.putExtra("airport_code", airportDetails.code);
				    	i.putExtra("airport_index", String.valueOf(airportDetails.index));
				    	startActivity(i);
			    	} else {
			    		Log.d("DEBUG", "Did not find nearby airport");
			    	}
				}
			};
			LocationPreferences locPrefs = new LocationPreferences();
			Boolean ret_result = locPrefs.getCurrentLocation(this.getBaseContext(), locationResult);
			if (!ret_result) {
				((TextView)findViewById(R.id.tvMessage)).setText("Tap to Refresh");
				Toast.makeText(this, "Unable to locate your location. Please ensure gps and network connectivity is turned on and try again later.", 
						Toast.LENGTH_LONG).show();
			}
		} else {
			((TextView)findViewById(R.id.tvMessage)).setText("Tap to Refresh");
			Toast.makeText(this, "Unable to connect to Internet. Please try again later.", Toast.LENGTH_LONG).show();
		}
	}
	
	public void getLocation(View v){
		getLocation();
	}
	
	private Airport findClosestAirport(Location location) {
		Airport result = null;
		//set initial distance at 120 miles or 193121 meters
		Float minDistance = Float.parseFloat("193121");
		Integer index = 0;
		for (String[] airportLocation : Airport.LOCATIONS) {
			Location tempLocation = new Location("app");
			tempLocation.setLatitude(Double.parseDouble(airportLocation[0]));
			tempLocation.setLongitude(Double.parseDouble(airportLocation[1]));
			Float distanceToAirport = location.distanceTo(tempLocation);
			if (distanceToAirport < minDistance) {
				minDistance = distanceToAirport;
				result = new Airport(new ArrayList<String>(Airport.IATA_CODES.values()).get(index), index);
			}
			index++;
		}
		return result;
	}
	
	protected boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
}
