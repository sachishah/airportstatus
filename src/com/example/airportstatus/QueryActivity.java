package com.example.airportstatus;

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

public class QueryActivity extends Activity {
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
		NetworkTaskCollectionRunner n = new NetworkTaskCollectionRunner(this) {
			@Override
			public void handleResult(Bundle bundle) {
				// Test here that the value returned from the observed NetworkTaskCollection 
				// is the one that signals success
				if (bundle.containsKey("success")) {
					Intent i = new Intent(this.context, StatusListActivity.class);
					bundle.putString("origin", TravelTimeEstimate.getCoordinates(this.location));
					bundle.putString("airport_code", airportCode);
					bundle.putString("airport_index", airportIndex);
					i.putExtra("data", bundle);
					startActivity(i);
					finish();
				} 
			}
		};
		n.setData(airportCode, currentLocation);
		n.run();
	}
}
