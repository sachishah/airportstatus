package com.example.airportstatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FlightStatsClient {
	//curl -v  -X GET "https://api.flightstats.com/flex/weather/rest/v1/json/metar/ABQ?appId=41a9abe4&appKey=de9124c085c33b9b167b1b02e7c42ad2&codeType=IATA"
	private static final String appId = "41a9abe4";
	private static final String appKey = "de9124c085c33b9b167b1b02e7c42ad2";
	private static final String BASE_URL = "https://api.flightstats.com/flex/weather/rest/v1/json/";
	private static final String DELAYS_URL = "airports/";
	private static final String WEATHER_URL = "metar/"; // Use METAR weather request
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	
	public static void getWeatherConditions(String code, AsyncHttpResponseHandler handler) {
		client.get(getApiUrl(WEATHER_URL, code), makeParams(), handler);
	}
	
	public static String getWeatherString(JSONObject response) throws Exception {
		try {
			JSONArray tags = response.getJSONObject("metar").getJSONArray("tags");
			String conditionsTag = "Prevailing Conditions";
			for (int i = 0; i < tags.length(); i++) {
				JSONObject t = tags.getJSONObject(i);
				if (t.has("key") && t.getString("key").equals(conditionsTag)) {
					return t.getString("value");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("WEATHER", "Key not found");
			Log.e("WEATHER", e.getMessage());
		}
		
		// No result was found, so raise an exception
		throw new Exception("No weather value found");
	}
	
	private static RequestParams makeParams() {
		RequestParams params = new RequestParams();
		params.put("appId", appId);
		params.put("appKey", appKey);
		params.put("codeType", "IATA");
		return params;
	}
	
	protected static String getApiUrl(String url, String code) {
		return BASE_URL + url + code;
	}

}
