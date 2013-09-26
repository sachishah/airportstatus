package com.example.airportstatus;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airportstatus.models.AirportStatus;
import com.example.airportstatus.models.Favorite;
import com.example.airportstatus.models.TravelTimeEstimate;

public class StatusListActivity extends Activity {
	
	String code;
	TextView weather;
	TextView delays;
	TextView drivingTimeEstimate, transitTimeEstimate;
	Button securityWaitTimes;
	ImageView favoriteStatus;
	AirportStatus airportStatus;
	boolean isFavorited;
	Bundle intentData;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		intentData = getIntent().getBundleExtra("data");
		code = intentData.getString("airportCode").toUpperCase();
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
	
	@SuppressLint("InlinedApi")
	private void setupViews() {
		weather = (TextView) findViewById(R.id.tvWeather);
		securityWaitTimes = (Button) findViewById(R.id.btnSecurityWaitTimes);
		securityWaitTimes.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		delays = (TextView) findViewById(R.id.tvDelays);
		drivingTimeEstimate = (TextView) findViewById(R.id.tvTransitValue1);
		transitTimeEstimate = (TextView) findViewById(R.id.tvTransitValue2);
		favoriteStatus = (ImageView) findViewById(R.id.ivFavorite);
 	}
	
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
	
	private void setTemplateData(Intent intent) {
		try {
			Bundle data = intent.getBundleExtra("data");
			drivingTimeEstimate.setText(data.getString(StatusKeys.TRAVEL_TIME_DRIVING));
			transitTimeEstimate.setText(data.getString(StatusKeys.TRAVEL_TIME_TRANSIT));
			delays.setText(data.getString(StatusKeys.DELAYS));
			weather.setText(data.getString(StatusKeys.WEATHER));
			
			setFavoritedStatus();
		} catch (Exception e) {
			Log.e("INVALID_INTENT_EXTRA", e.getMessage());
		}
	}
	
	public void onClickDrivingMapButton(View v) {
		if (!intentData.containsKey("origin")) {
			Toast.makeText(getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = intentData.getString("origin");
		launchMapIntent(TravelTimeEstimate.getDrivingMapUrl(origin, code));
	}
	
	public void onClickTransitMapButton(View v) {
		if (!intentData.containsKey("origin")) {
			Toast.makeText(getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = intentData.getString("origin");
		launchMapIntent(TravelTimeEstimate.getTransitMapUrl(origin, code));
	}
	
	private void launchMapIntent(String url) {
		try {
			
		} catch (Exception e) {
			Log.e("MAP_LAUNCHER", e.getMessage());
			Toast.makeText(getApplicationContext(), R.string.txtRoutingError, Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(intent);
	}
	
	private void setFavoritedStatus() {
		this.isFavorited = Favorite.exists(this.code);
		if (this.isFavorited) {
			favoriteStatus.setImageResource(R.drawable.ic_star_filled);
		} else {
			favoriteStatus.setImageResource(R.drawable.ic_star_empty);
		}
	}
	
	public void onFavoriteAction(View v) {
		if (this.isFavorited == true) {
			Favorite.delete(code);
			// Favorite exists; delete it
		} else {
			// Set item as favorite
			Favorite newFavorite = new Favorite();
			newFavorite.setAirportCode(code);
			newFavorite.save();
		}
		this.setFavoritedStatus();
	}
}
