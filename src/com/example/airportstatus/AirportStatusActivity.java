package com.example.airportstatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
    	String code = tvAirportCode.getText().toString();
    	Toast.makeText(this, "Searching for " + code + "...", Toast.LENGTH_SHORT).show();
    	
    	Intent i = new Intent(this, QueryActivity.class);
    	i.putExtra("airport_code", code);
    	startActivity(i);
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