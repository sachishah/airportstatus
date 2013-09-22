package com.example.airportstatus;

import java.util.Observable;
import java.util.Observer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class QueryActivity extends Activity implements Observer {
	private NetworkTaskCollection myTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
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
		myTasks = new NetworkTaskCollection();
		myTasks.addObserver(this);
		myTasks.addTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				Log.d("TASK", "Task is running");
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				Log.d("TASK", "Task completed");
				myTasks.markTaskComplete();
				myTasks.checkTaskStatus();
			}
		});
		

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
		
		myTasks.startAll();
	} 

	@Override
	public void update(Observable observable, Object bundleData) {
		Log.d("TASK COMPLETE", bundleData.toString());
		
		// Test here that the value returned from the observed NetworkTaskCollection 
		// is the one that signals success
		if (bundleData.toString().equals("SWEET")) {
			Intent i = new Intent(this, StatusListActivity.class);
	    	i.putExtra("airport_code", "SFO");
	    	startActivity(i);
			return;
		} 
		
		// Otherwise, go back to the starting activity and show an error
		
	}
}
