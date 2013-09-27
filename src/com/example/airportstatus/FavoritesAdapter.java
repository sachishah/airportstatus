package com.example.airportstatus;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FavoritesAdapter extends ArrayAdapter<String> {
	protected Context mContext;
	public FavoritesAdapter(Context context, List<String> favorites) {
		super(context, android.R.layout.simple_spinner_item, android.R.id.text1, favorites);
		mContext = context;
	}
	
	@Override
	public View getDropDownView(int position, View customView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View view = inflater.inflate(R.layout.null_option_spinner, null);

		String itemText = (String) getItem(position);
		if (itemText.equals("Favorites")) {
			return new View(mContext);
		}
		TextView tv = (TextView) view.findViewById(android.R.id.text1);
		tv.setText(itemText);
		return view;
	}
}
