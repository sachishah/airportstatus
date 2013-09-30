package com.example.airportstatus;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.airportstatus.fragments.StatusFragment;



public class StatusListActivity extends FragmentActivity implements TabListener {
	
	String code;
	Bundle intentData;
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_list);
		intentData = getIntent().getBundleExtra("data");
		code = intentData.getString("airportCode").toUpperCase();
		setupActionBar();
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
		Tab tabNearby= actionBar.newTab().setText("Nearby")
				.setTag("NearbyFragment")
				.setTabListener(this);
		Tab tabSaved = actionBar.newTab().setText("Saved")
				.setTag("SavedFragment")
				.setTabListener(this);
		Tab tabFind = actionBar.newTab().setText("Find")
				.setTag("FindFragment")
				.setTabListener(this);
		actionBar.addTab(tabStatus);
		actionBar.addTab(tabNearby);
		actionBar.addTab(tabSaved);
		actionBar.addTab(tabFind);
		actionBar.selectTab(tabStatus);
		
	}
	

	
	@SuppressLint("NewApi")
	private void setupActionBar() {
		ActionBar bar = getActionBar();
		bar.setTitle("Airport Status: " + code);
		bar.setDisplayHomeAsUpEnabled(true);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_search:
	    	finish();
	    	return true;
	    case android.R.id.home:
	    	finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
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
}
