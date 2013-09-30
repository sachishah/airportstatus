package com.example.airportstatus.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.airportstatus.GoogleClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TravelTimeEstimate {
	
	private static String MODE_DRIVING = "driving";
	private static String MODE_TRANSIT = "transit";
	private static String ALTERNATIVE_ROUTES = "false";
	private static String DEVICE_SENSOR = "true";
	
	public static void getDrivingTime(String origin, String destination, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params = new RequestParams();
		params.put("alternatives", ALTERNATIVE_ROUTES);
		params.put("sensor", DEVICE_SENSOR);
		params.put("mode", MODE_DRIVING);
		params.put("origin", origin);
		params.put("destination", destination);
		GoogleClient.getDirections(params, handler);
	}
	
	public static void getTransitTime(String origin, String destination, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params = new RequestParams();
		params.put("alternatives", ALTERNATIVE_ROUTES);
		params.put("sensor", DEVICE_SENSOR);
		params.put("mode", MODE_TRANSIT);
		params.put("origin", origin);
		params.put("destination", destination);
		params.put("departure_time", getAugmentedCurrentTime(0)); 
		
		GoogleClient.getDirections(params, handler);
	}
	
	public static int parseDirections(JSONObject response) throws Exception {
		/**
		 * Parses the response for the first route.
		 * Adds up the durations of the legs in the route.
		 */
		int minutes = 0;
		try {
			if (response.getString("status").equals("OK")) {
				JSONArray routes = response.getJSONArray("routes");
				if (routes.length() > 0) {
					JSONObject bestRoute = routes.getJSONObject(0);
					JSONArray routeLegs = bestRoute.getJSONArray("legs");
					
					for (int i = 0; i < routeLegs.length(); i++) {
						try {
							long seconds = routeLegs.getJSONObject(i).getJSONObject("duration").getLong("value");
							if (seconds % 60 > 0) {
								// Round up seconds to match Google's error rounding
								seconds += 60 - (seconds % 60);
							}
							
							minutes += seconds / 60;
						} catch (JSONException keyError) {
							Log.e("DURATION", "Could not read duration from route leg");
						}
					}
				} 
			} else {
				throw new Exception("No route data");
			}
		} catch (JSONException e) {
			Log.e("JSONException", e.getMessage());
		}
		return minutes;
	}
	
	public static String getFormattedDuration(int minutes) {
		int hrs = minutes / 60;
		int mins = minutes % 60;
		if (hrs > 0) {
			String hourString = "hour";
			if (hrs != 1) {
				hourString += "s";
			}
			
			return hrs + " " + hourString + ", " + mins + " mins";
		}
		return mins + " mins";
	}
	
	public static String getAugmentedCurrentTime(int secondsAhead) {
		// Destination URL expects time in seconds,
		// so currentTimeMillis must be divided by 1000 
		// to provide the correct time precision
		return String.valueOf((System.currentTimeMillis() / 1000) + secondsAhead);
	}
	
	public static String getDrivingMapUrl(String origin, String destination) {
		return GoogleClient.getMapsUrl() +
				"saddr=" + Uri.encode(origin) +
				"&daddr=" + Uri.encode(destination);
	}
	
	public static String getTransitMapUrl(String origin, String destination) {
		return GoogleClient.getMapsUrl() +
				"dirflg=" + Uri.encode("r") +
				"&saddr=" + Uri.encode(origin) +
				"&daddr=" + Uri.encode(destination);
	}
	
	public static String getCoordinates(Location loc) {
		return String.valueOf(loc.getLatitude()) + "," + String.valueOf(loc.getLongitude());
	}
}
