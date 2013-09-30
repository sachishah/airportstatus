package com.example.airportstatus.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.example.airportstatus.models.Favorite;

public class SavedFragment extends Fragment {
	ArrayList<Favorite> favorites;

	public SavedFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Toast.makeText(this.getActivity(), "Setting up saved list fragment", Toast.LENGTH_SHORT).show();
		
	}

}
