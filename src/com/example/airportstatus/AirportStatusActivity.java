package com.example.airportstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class AirportStatusActivity extends Activity {
	
	Button btnGo;
	AutoCompleteTextView tvAirportCode;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_status);
        setupButton();
        setupTextView();
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

    private void setupTextView() {
    	String [] codes = new String [] { "SFO", "SJC", "OAK" };
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, codes);
    	tvAirportCode = (AutoCompleteTextView) findViewById(R.id.tvAirportCode);
    	tvAirportCode.setAdapter(adapter);
    }
}