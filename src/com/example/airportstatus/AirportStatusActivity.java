package com.example.airportstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AirportStatusActivity extends Activity {
	
	Button btnGo;
	Spinner spnAirport;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_status);
        setupButton();
        setupSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.airport_status, menu);
        return true;
    }
    
    public void onClick(View v) {
    	Toast.makeText(this, "Searching for ", Toast.LENGTH_SHORT).show();
    }
    
    @SuppressLint("InlinedApi")
	private void setupButton() {
    	btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }
    
    private void setupSpinner() {
    	spnAirport = (Spinner) findViewById(R.id.spnAirport);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add("SFO San Francisco");
		adapter.add("SJC San Jose");
		adapter.add("OAK Oakland");
		spnAirport.setAdapter(adapter);
		
		// spnAirport.setPrompt("Tap to enter..");
		// set background color on hover
		// show keyboard
		// correctly populate adapter
		// autocomplete ?
		// find option from list of airport
		
		spnAirport.setFocusable(true);
		spnAirport.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(spnAirport, InputMethodManager.SHOW_IMPLICIT);
                return false;
            }
        }) ;
		
		
    }
    
}
