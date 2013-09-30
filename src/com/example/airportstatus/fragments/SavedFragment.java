package com.example.airportstatus.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.airportstatus.R;
import com.example.airportstatus.SavedFavoritesAdapter;
import com.example.airportstatus.models.Favorite;

public class SavedFragment extends Fragment {
	private ListView lv;
	private ArrayList<String> favorites;
	private ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		favorites = new ArrayList<String>();
		return inf.inflate(R.layout.fragment_saved, parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		favorites = Favorite.getAllCodes();
		lv = (ListView) getActivity().findViewById(R.id.lvSavedAirports);
		TextView emptyState = (TextView) getActivity().findViewById(R.id.tvErrorSavedEmpty);
		
		adapter = new SavedFavoritesAdapter(getActivity(), 0, favorites);
		lv.setAdapter(adapter);
		lv.setEmptyView(emptyState);
	}
}
