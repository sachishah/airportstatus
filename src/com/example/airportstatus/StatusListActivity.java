package com.example.airportstatus;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airportstatus.models.AirportStatus;
import com.example.airportstatus.models.AirportStatusLocation;
import com.example.airportstatus.models.TravelTimeEstimate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class StatusListActivity extends Activity {
	
	String code;
	TextView weather;
	TextView delays;
	TextView drivingTimeEstimate, transitTimeEstimate;
	Button securityWaitTimes;
	AirportStatus airportStatus;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		code = getIntent().getBundleExtra("data").getString("airportCode").toUpperCase();
		setupActionBar();
		setupViews();
		if (isCodeValid()) {
			setTemplateData(getIntent());
			// loadResults();
		} else {
			Toast.makeText(this, "Airport code not found", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status_list, menu);
		return true;
	}
	
	public void onSecurityWaitTimeClick(View v) {
		startActivity(new Intent(this, SecurityWaitTimeActivity.class));
	}
	
	private boolean isCodeValid() {
		return true;
	}
	
	@SuppressLint("NewApi")
	private void setupActionBar() {
		ActionBar bar = getActionBar();
		bar.setTitle("Airport Status: " + code);
		bar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void setTemplateData(Intent intent) {
		try {
			Bundle data = intent.getBundleExtra("data");
			drivingTimeEstimate.setText(data.getString(StatusKeys.KEY_TRAVEL_TIME_DRIVING));
			transitTimeEstimate.setText(data.getString(StatusKeys.KEY_TRAVEL_TIME_TRANSIT));
		} catch (Exception e) {
			Log.e("INVALID_INTENT_EXTRA", e.getMessage());
		}
	}
	
	@SuppressLint("InlinedApi")
	private void setupViews() {
		weather = (TextView) findViewById(R.id.tvWeather);
		securityWaitTimes = (Button) findViewById(R.id.btnSecurityWaitTimes);
		securityWaitTimes.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		delays = (TextView) findViewById(R.id.tvDelays);
		drivingTimeEstimate = (TextView) findViewById(R.id.tvTransitValue1);
		transitTimeEstimate = (TextView) findViewById(R.id.tvTransitValue2);
 	}
	/*
	private void loadResults() {
		AsyncHttpClient client = new AsyncHttpClient();
	    client.get("http://services.faa.gov/airport/status/" + Uri.encode(code) + "?format=application/json", 
	    			new JsonHttpResponseHandler() {
	   		@Override
	   		public void onSuccess(JSONObject response) {
	    		airportStatus = AirportStatus.fromJson(response);
	    		weather.setText(airportStatus.getWeather() + " (visibility: " + airportStatus.getVisibility() + ")");
	    		if(!airportStatus.getDelay())
	    			delays.setText("None reported");
	    		else {
	    			String result = "Average Delay: " + airportStatus.getAvgDelay();
	    			result += "\nDelay Type: " + airportStatus.getDelayType();
	    			String closureBegin = airportStatus.getClosureBegin();
	    			if (closureBegin != "") {
	    				result += "\nClosure Begin Time: " + closureBegin;
	    				result += "\nClosure End Time: " + airportStatus.getClosureEnd();
	    			} else {
	    				result += "\nEnd Time: " + airportStatus.getEndTime();
	    			}
	    			delays.setText(result);
	    		
	    		}
	   		}
	   		
	   		@Override
			public void onFailure(Throwable e, JSONObject obj) {
	   			Toast.makeText(getParent(), obj.toString(), Toast.LENGTH_SHORT).show();
			}
	   	});
	    
	    AirportStatusLocation current = getCurrentLocation();
	    getDrivingTimeEstimate(current.toString(), code, new JsonHttpResponseHandler() {
	    	@Override
	    	public void onSuccess(JSONObject response) {
	    		displayResponse(drivingTimeEstimate, response);
	    	}
	    	
	    	@Override
	    	public void onFailure(Throwable error) {
	    		displayFailureResponse();
	    	}
		});
	    
	    getTransitTimeEstimate(current.toString(), code, new JsonHttpResponseHandler() {
	    	@Override
	    	public void onSuccess(JSONObject response) {
	    		displayResponse(transitTimeEstimate, response);
	    	}
	    	
	    	@Override
	    	public void onFailure(Throwable error) {
	    		displayFailureResponse();
	    	}
		});
	}
	*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
