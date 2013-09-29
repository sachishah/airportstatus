package com.example.airportstatus;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.example.airportstatus.models.Checkpoint;
import com.example.airportstatus.models.WaitTime;
import com.loopj.android.http.JsonHttpResponseHandler;



public class SecurityWaitTimeActivity extends Activity {
	String code;
	ArrayList<Checkpoint> checkpoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_wait_time);
		code = getIntent().getStringExtra("airport_code");
		TextView codeView = (TextView) findViewById(R.id.tvSWTAirportCode);
		codeView.setText(code);
		
		//should probably save checkpoints in db on first call
		TSAClient.getCheckpoints(code, new JsonHttpResponseHandler(){
			@Override
	   		public void onSuccess(JSONArray response) {
				checkpoints = Checkpoint.fromJson(response);
				getWaitTimes();
				
			}
			
			@Override
	   		public void onFailure(Throwable e, JSONObject response) {
				Log.d("DEBUG", "error" + response.toString());
			}
			
		});
	}

	protected void getWaitTimes() {
		TSAClient.getWaitTimes(code, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response) {
				try {
					ArrayList<WaitTime> waitTimes = WaitTime.fromJson(response.getJSONArray("WaitTimes"));
					ListView waitTimeList = (ListView) findViewById(R.id.lvSWTGates);
					WaitTimeAdapter adapter = new WaitTimeAdapter(getBaseContext(), waitTimes,checkpoints);
					waitTimeList.setAdapter(adapter);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			@Override
	   		public void onFailure(Throwable e, JSONObject response) {
				Log.d("DEBUG", "error" + response.toString());
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.security_wait_time, menu);
		return true;
	}

}
