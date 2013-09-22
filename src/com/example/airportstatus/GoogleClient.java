package com.example.airportstatus;

import com.loopj.android.http.*;

public class GoogleClient {

	private static final String API_KEY = "AIzaSyBRKlPBW1FODa_e_lhHVBnxzqIDls-71xM";
	private static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
	private static final String DIRECTIONS_URL = "http://maps.googleapis.com/maps/api/directions/json?";
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void getDirections(RequestParams params, AsyncHttpResponseHandler handler) {
		client.get(DIRECTIONS_URL, params, handler);
	}

}
