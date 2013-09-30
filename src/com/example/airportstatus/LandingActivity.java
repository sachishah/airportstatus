package com.example.airportstatus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class LandingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		getLocation();
		
	}
	
	@Override
	protected void onResume(){
		getLocation();
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}
	
	private void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = locationManager
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// Check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
		  //if gps is not enabled we should ask for it
		  Log.d("DEBUG", "not enabled");
		} 
		
		Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
		
		if (location != null ) {
			Log.d("DEBUG","locaton is " + location.toString());
			String airportCode = findClosestAirport(location);
			if (airportCode != null) {
		    	Toast.makeText(this, "Loading status for " + airportCode + "...", Toast.LENGTH_SHORT).show();
		    	Intent i = new Intent(this, QueryActivity.class);
		    	i.putExtra("airport_code", airportCode);
		    	startActivity(i);
	    	} else {
	    		Log.d("DEBUG", "Did not find nearby airport");
	    	}
			
		} else {
			Log.d("DEBUG", "location is null");
		}
		
	}
	
	private String findClosestAirport(Location location) {
	  String airportCode = null;
	  //set initial distance at 120 miles or 193121 meters
	  Float minDistance = Float.parseFloat("193121");
	  Integer index = 0;
	  for(String[] airportLocation : Airport.LOCATIONS ) {
		  Location tempLocation = new Location("app");
		  tempLocation.setLatitude(Double.parseDouble(airportLocation[0]));
		  tempLocation.setLongitude(Double.parseDouble(airportLocation[1]));
		  Float distanceToAirport = location.distanceTo(tempLocation);
		  if (distanceToAirport < minDistance) {
		        minDistance = distanceToAirport;
		        airportCode = new ArrayList<String>(Airport.IATA_CODES.values()).get(index);
		    }
		  index++;
		}
	  return airportCode;
	}

}
