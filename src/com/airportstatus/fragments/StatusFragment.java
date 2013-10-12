package com.airportstatus.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airportstatus.R;
import com.airportstatus.helpers.LocationPreferences;
import com.airportstatus.helpers.LocationResult;
import com.airportstatus.helpers.NetworkTaskCollectionRunner;
import com.airportstatus.helpers.StatusKeys;
import com.airportstatus.models.Airport;
import com.airportstatus.models.Favorite;


public class StatusFragment extends Fragment {
	
	
	TextView delays, tvRefresh;
	TextView weather;
	String code;
	Integer airportIndex;
	Bundle intentData;
	ImageView favoriteStatus;
	boolean isFavorited;
	Button btnDrivingTime, btnTransitTime, btnDelays;
	ProgressBar pb;
	
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setHasOptionsMenu(true);

	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.fragment_status_detail, menu);
	}

	
	@SuppressLint({ "InlinedApi", "ResourceAsColor" })
	private void setupViews() {
		Activity activity = getActivity();
		pb = (ProgressBar) activity.findViewById(R.id.pbSmallSpinner);
		btnDrivingTime = (Button) activity.findViewById(R.id.btnDrivingTime);
		btnTransitTime = (Button) activity.findViewById(R.id.btnTransitTime);
		tvRefresh = (TextView) activity.findViewById(R.id.tvRefresh);
		btnDelays = (Button) activity.findViewById(R.id.btnDelays);
		btnDrivingTime.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		btnTransitTime.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		btnDelays.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
		String airportName = new ArrayList<String>(Airport.IATA_CODES.keySet()).get(airportIndex);
		((TextView)getActivity().findViewById(R.id.tvAirportName))
		  .setText(airportName);
		((TextView)getActivity().findViewById(R.id.tvBigAirportCode)).setText(code);
		String website = Airport.WEBSITES.get(airportIndex);
		String formattedWebsite = "<a href='http://"+website+"'>"+website+"</a>";
		TextView tvWebsite = ((TextView) activity.findViewById(R.id.tvWebsite));
		tvWebsite.setText(Html.fromHtml(formattedWebsite));
		tvWebsite.setMovementMethod(LinkMovementMethod.getInstance());
		weather = (TextView) activity.findViewById(R.id.tvWeather);
		delays = (TextView) activity.findViewById(R.id.tvDelays);
		favoriteStatus = (ImageView) activity.findViewById(R.id.ivFavorite);
 	}
	
	private void setTemplateData(Intent intent) {
		try {
			Bundle data = intent.getBundleExtra("data");
			updateDrivingButton(data.getString(StatusKeys.TRAVEL_TIME_DRIVING));
			updateTransitButton(data.getString(StatusKeys.TRAVEL_TIME_TRANSIT));
			updateDelaysButton(data.getString(StatusKeys.DELAYS));
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
	
	public void onClickRefresh(MenuItem item) {
		// Set loading spinner state
		toggleRefreshButton();
		pb.setVisibility(View.VISIBLE);
		
		// Get updated user location
		LocationResult locationResult = new LocationResult() {
			@Override
			public void receivedLocation(Location location) {
				Log.d("LOCATION_RECEIVED", location.toString());
				LocationPreferences.setLastLocationPreferences(getActivity(), location.getLatitude(), location.getLongitude());
				
				// Once user location has been refreshed, run the rest of the network tasks
				NetworkTaskCollectionRunner n = new NetworkTaskCollectionRunner(getActivity()) {
					@Override
					public void handleResult(Bundle bundle) {
						updateViews(bundle);
					}
				};
				n.setData(code, location);
				n.run();
			}
		};
		LocationPreferences locPrefs = new LocationPreferences();
		locPrefs.getCurrentLocation(getActivity(), locationResult);
	}
	
	private void updateViews(Bundle bundle) {
		// Test here that the value returned from the observed NetworkTaskCollection 
		// is the one that signals success
		if (bundle.containsKey("success")) {
			updateDrivingButton(bundle.getString(StatusKeys.TRAVEL_TIME_DRIVING));
			updateTransitButton(bundle.getString(StatusKeys.TRAVEL_TIME_TRANSIT));
			updateDelaysButton(bundle.getString(StatusKeys.DELAYS));
			weather.setText(bundle.getString(StatusKeys.WEATHER));
		} 
		
		toggleRefreshButton();
		pb.setVisibility(View.INVISIBLE);
	}
	
	private void updateDrivingButton(String time) {
		btnDrivingTime.setText("Driving Directions: " + time);
	}
	
	private void updateTransitButton(String time) {
		if (time == null)
			time = "n/a";
		btnTransitTime.setText("Transit Directions: " + time);
	}
	
	private void updateDelaysButton(String time) {
		if (time == null)
			time = "n/a";
		btnDelays.setText("Status: " + time);
	}
	
	private void toggleRefreshButton() {
		if (tvRefresh.getText() == "") {
			tvRefresh.setText(R.string.txtRefreshing);
		} else {
			tvRefresh.setText("");
		}

	}
}
