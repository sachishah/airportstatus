package com.example.airportstatus.fragments;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.airportstatus.R;
import com.example.airportstatus.models.AirportStatus;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class DelayFragment extends Fragment {
	String code;
	Bundle intentData;
	AirportStatus airportStatus;
	TextView tvDelays;
	Button delayButton;
	boolean delay;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_delay, parent, false);
	}

	@SuppressLint("DefaultLocale")
	public void onStart() {
		super.onStart();
		intentData = getActivity().getIntent().getBundleExtra("data");
		code = intentData.getString("airport_code").toUpperCase();
		setupAirportStatus(code);
	}

	public void setupAirportStatus(String code) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://services.faa.gov/airport/status/" + Uri.encode(code) + "?format=application/json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				airportStatus = AirportStatus.fromJson(response);
			}
		});
	}

	public void setupViews() {
		tvDelays = (TextView) getActivity().findViewById(R.id.tvDelays);
		tvDelays.setText(airportStatus.getDelayType());
	}
	
	@SuppressLint("ResourceAsColor")
	public void setDelayButton() {
		delayButton.setBackgroundColor(android.R.color.holo_green_light);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://services.faa.gov/airport/status/" + Uri.encode(code) + "?format=application/json", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				airportStatus = AirportStatus.fromJson(response);
				if (airportStatus.getDelay()) {
					delay = true;
					delayButton.setBackgroundColor(android.R.color.holo_red_light);
					delayButton.setText("Status: Delayed");
				}
			}
		});
	}


	public void onDelayButtonClick() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container, new DelayFragment());
		ft.addToBackStack(null);
		ft.commit();
	}
}