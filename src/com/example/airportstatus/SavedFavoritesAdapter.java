package com.example.airportstatus;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SavedFavoritesAdapter extends ArrayAdapter<String> {
	public SavedFavoritesAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public SavedFavoritesAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.saved_favorite, null);
		}
		
		String code = getItem(position);
		AirportClickListener listener = new AirportClickListener(getContext(), code);
		view.setOnClickListener(listener);
		
		String locator = null;
		for (String key : Airport.IATA_CODES.keySet()) {
			String airportTag = key;
			
			if (airportTag.contains(code)) {
				locator = airportTag;
				locator = locator.substring(0, locator.length() - 6); // Trim off code and hyphen
			}
		}
		
		TextView tvAirportName = (TextView) view.findViewById(R.id.tvFavoriteAirportCode);
		tvAirportName.setText(code);

		if (locator != null) {
			TextView tvAirportDetail = (TextView) view.findViewById(R.id.tvFavoriteAirportDetail);
			tvAirportDetail.setText(locator);
		}
		
		return view;
	}
	
	private class AirportClickListener implements OnClickListener {
		private String airportCode;
		private Context context;
		public AirportClickListener(Context context, String code) {
			this.context = context;
			this.airportCode = code;
		}
		
		@Override
		public void onClick(View v) {
			Toast.makeText(getContext(), this.airportCode, Toast.LENGTH_LONG).show();
			
			int index = new ArrayList<String>(Airport.IATA_CODES.values()).indexOf(this.airportCode);
	    	if (this.airportCode != null) {
		    	Intent i = new Intent(this.context, QueryActivity.class);
		    	i.putExtra("airport_code", this.airportCode);
		    	i.putExtra("airport_index", String.valueOf(index));
		    	this.context.startActivity(i);
	    	}
		}
	}
}
