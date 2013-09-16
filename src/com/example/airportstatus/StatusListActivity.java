package com.example.airportstatus;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.airportstatus.models.AirportStatus;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class StatusListActivity extends Activity {
	
	TextView airportCode;
	TextView weather;
	ListView delays;
	Button securityWaitTimes;
	AirportStatus airportStatus;
	ArrayList<String> delayList = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		setupViews();
		String code = getIntent().getStringExtra("airport_code");
		airportCode.setText(code);
		//adapter
		loadResults(code);
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
	
	private void setupViews() {
		airportCode = (TextView) findViewById(R.id.tvAirportCode);
		weather = (TextView) findViewById(R.id.tvWeather);
		securityWaitTimes = (Button) findViewById(R.id.btnSecurityWaitTimes);
		delays = (ListView) findViewById(R.id.lvDelays);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, delayList);
		delays.setAdapter(adapter);
 	}
	
	private void loadResults(String code) {
		AsyncHttpClient client = new AsyncHttpClient();
	    client.get("http://services.faa.gov/airport/status/" + Uri.encode(code) + "?format=application/json", 
	    			new JsonHttpResponseHandler() {
	   		@Override
	   		public void onSuccess(JSONObject response) {
	    		airportStatus = AirportStatus.fromJson(response);
	    		weather.setText(airportStatus.getWeather());
	    		if(!airportStatus.getDelay())
	    			delayList.add("None reported");
	    		else {
	    			delayList.add("Average Delay: " + airportStatus.getAvgDelay());
	    			delayList.add("Delay Type: " + airportStatus.getDelayType());
	    			String closureBegin = airportStatus.getClosureBegin();
	    			if (closureBegin != "") {
	    				delayList.add("Closure Begin Time: " + closureBegin);
	    				delayList.add("Closure End Time: " + airportStatus.getClosureEnd());
	    			} else {
	    				delayList.add("End Time: " + airportStatus.getEndTime());
	    			}
	    		}
	   		}
	   	});
	}

}
