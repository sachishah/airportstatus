package com.example.airportstatus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SecurityWaitTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_wait_time);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.security_wait_time, menu);
		return true;
	}

}
