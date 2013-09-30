package com.example.airportstatus;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.airportstatus.fragments.StatusFragment;
import com.example.airportstatus.models.TravelTimeEstimate;



public class StatusListActivity extends FragmentActivity implements TabListener {
	
	String code;
	Bundle intentData;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		intentData = getIntent().getBundleExtra("data");
		code = intentData.getString("airport_code").toUpperCase();
		setupNavigationTabs();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status_list, menu);
		return true;
	}
	private void setupNavigationTabs() {
		ActionBar actionBar= getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabStatus= actionBar.newTab().setText("Status")
				.setTag("StatusFragment")
				.setTabListener(this);
		/*Tab tabNearby= actionBar.newTab().setText("Nearby")
				.setTag("NearbyFragment")
				.setTabListener(this);*/
		Tab tabSaved = actionBar.newTab().setText("Saved")
				.setTag("SavedFragment")
				.setTabListener(this);
		Tab tabFind = actionBar.newTab().setText("Find")
				.setTag("FindFragment")
				.setTabListener(this);
		actionBar.addTab(tabStatus);
		//actionBar.addTab(tabNearby);
		actionBar.addTab(tabSaved);
		actionBar.addTab(tabFind);
		actionBar.selectTab(tabStatus);
		
	}
	


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    /*switch (item.getItemId()) {
	    case R.id.action_search:
	    	finish();
	    	return true;
	    case android.R.id.home:
	    	finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }*/
		return true;
	}
	
	
	
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "StatusFragment") {
			fts.replace(R.id.frame_container, new StatusFragment());
		} else {
			fts.replace(R.id.frame_container, new StatusFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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
}
