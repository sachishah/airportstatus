package com.example.airportstatus;

import java.util.List;

import android.content.Context;
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
		
		TextView tvAirportName = (TextView) view.findViewById(R.id.tvFavoriteAirportCode);
		tvAirportName.setText(code);
		
		return view;
	}
}
