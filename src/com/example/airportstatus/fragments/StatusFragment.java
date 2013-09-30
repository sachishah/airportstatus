package com.example.airportstatus.fragments;



import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airportstatus.Airport;
import com.example.airportstatus.R;
import com.example.airportstatus.StatusKeys;
import com.example.airportstatus.models.Favorite;
import com.example.airportstatus.models.TravelTimeEstimate;


public class StatusFragment extends Fragment {
	
	TextView drivingTimeEstimate;
	TextView transitTimeEstimate;
	TextView delays;
	TextView weather;
	String code;
	Integer airportIndex;
	Bundle intentData;
	ImageView favoriteStatus;
	boolean isFavorited;
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_status, parent, false);
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		intentData = getActivity().getIntent().getBundleExtra("data");
		code = intentData.getString("airport_code").toUpperCase();
		airportIndex = Integer.parseInt(intentData.getString("airport_index"));
		setupViews();
		setTemplateData(getActivity().getIntent());
	}
	
	@SuppressLint("InlinedApi")
	private void setupViews() {
		String airportName = new ArrayList<String>(Airport.IATA_CODES.keySet()).get(airportIndex);
		((TextView)getActivity().findViewById(R.id.tvAirportName))
		  .setText(airportName);
		((TextView)getActivity().findViewById(R.id.tvBigAirportCode)).setText(code);
		((TextView)getActivity().findViewById(R.id.tvWebsite))
		  .setText(Airport.WEBSITES.get(airportIndex));
		weather = (TextView) getActivity().findViewById(R.id.tvWeather);
		delays = (TextView) getActivity().findViewById(R.id.tvDelays);
		drivingTimeEstimate = (TextView) getActivity().findViewById(R.id.tvTransitValue1);
		transitTimeEstimate = (TextView) getActivity().findViewById(R.id.tvTransitValue2);
		favoriteStatus = (ImageView) getActivity().findViewById(R.id.ivFavorite);
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
	
	private void setFavoritedStatus() {
		this.isFavorited = Favorite.exists(code);
		if (this.isFavorited) {
			favoriteStatus.setImageResource(R.drawable.ic_star_filled);
		} else {
			favoriteStatus.setImageResource(R.drawable.ic_star_empty);
		}
	}
	
    
    
    public void onClickDrivingMapButton(View v) {
		if (!intentData.containsKey("origin")) {
			Toast.makeText(getActivity().getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = intentData.getString("origin");
		launchMapIntent(TravelTimeEstimate.getDrivingMapUrl(origin, code));
	}
	
	public void onClickTransitMapButton(View v) {
		if (!intentData.containsKey("origin")) {
			Toast.makeText(getActivity().getApplicationContext(), "Missing origin data", Toast.LENGTH_SHORT).show();
			return;
		}
		String origin = intentData.getString("origin");
		launchMapIntent(TravelTimeEstimate.getTransitMapUrl(origin, code));
	}
	
	private void launchMapIntent(String url) {
		try {
			
		} catch (Exception e) {
			Log.e("MAP_LAUNCHER", e.getMessage());
			Toast.makeText(getActivity().getApplicationContext(), R.string.txtRoutingError, Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(intent);
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
