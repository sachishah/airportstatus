package com.example.airportstatus;


import com.loopj.android.http.*;

public class TSAClient {
	
	
	private static final String BASE_URL = "http://apps.tsa.dhs.gov/mytsawebservice";
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void getCheckpoints(String airportCode, AsyncHttpResponseHandler handler) {
		client.get(BASE_URL + "/GetAirportCheckpoints.ashx?", new RequestParams("ap", airportCode), handler);
	}
	
	public static void getWaitTimes(String airportCode, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("ap", airportCode);
		params.put("output", "json");
		client.get(BASE_URL + "/GetWaitTimes.ashx?", params, handler);
	}
	
	

}
