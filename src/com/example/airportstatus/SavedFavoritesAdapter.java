package com.example.airportstatus;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
}
