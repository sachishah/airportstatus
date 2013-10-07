package com.example.airportstatus;

import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.airportstatus.models.TravelTimeEstimate;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class NetworkTaskCollectionRunner implements Observer {
	
	NetworkTaskCollection myTasks;
	Context context;
	Location location; 
	String airportCode;
	
	public NetworkTaskCollectionRunner(Context c) {
		 this.context = c;
		 myTasks = new NetworkTaskCollection();
	}
	
	public void setData(String airportCode, Location location) {
		this.airportCode = airportCode;
		this.location = location;
	}
	
	public void run() {
		myTasks.addObserver(this);
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						int totalTripMins;
						try {
							totalTripMins = TravelTimeEstimate.parseDirections(response);
							myTasks.finishWithResult(StatusKeys.TRAVEL_TIME_DRIVING, TravelTimeEstimate.getFormattedDuration(totalTripMins));
						} catch (Exception e) {
							myTasks.finishOneTask();
							Log.e("TASK", "Task failed 1");							
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						myTasks.finishOneTask();
					}
				};
			}

			@Override
			public void execute() {
				TravelTimeEstimate.getDrivingTime(TravelTimeEstimate.getCoordinates(location), airportCode, this.handler);
			}
		});
		
		myTasks.addTask(new NetworkTask() {
			@Override
			public void setHandler() {
				this.handler = new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						int totalTripMins;
						try {
							totalTripMins = TravelTimeEstimate.parseDirections(response);
							myTasks.finishWithResult(StatusKeys.TRAVEL_TIME_TRANSIT, TravelTimeEstimate.getFormattedDuration(totalTripMins));
						} catch (Exception e) {
							myTasks.finishOneTask();
							Log.e("TASK", "Task failed 2 ");							
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable error, JSONObject obj) {
						myTasks.finishOneTask();
					}
				};
			}
			
			@Override
			public void execute() {
				TravelTimeEstimate.getTransitTime(TravelTimeEstimate.getCoordinates(location), airportCode, this.handler);
			}
		});
		
		myTasks.startAll();
		Log.d("NetworkTaskCollectionRunner", "got this far");
		/*
		// NetworkTaskCollection becomes ArrayList of NetworkTasks
		// NetworkTaskCollection is Observable
		// NetworkTask has success handler that pushes data to bundle
		
		myTasks.addObserver(this);
		
				
				
				
				myTasks.addTask(new NetworkTask() {
					@Override
					public void setHandler() {
						this.handler = new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject response) {
								String currentWeatherAtAirport;
								try {
									currentWeatherAtAirport = FlightStatsClient.getWeatherString(response);
								} catch (Exception e) {
									currentWeatherAtAirport = "";
								}
								try {
									Double tempC = FlightStatsClient.getTempCelsius(response); // Result only comes back in degrees Celsius
									Double tempF = (tempC * 1.8) + 32;
									myTasks.addResult(StatusKeys.TEMPERATURE, String.valueOf(tempF));
								} catch (Exception e) {
									Log.e("WEATHER", e.getMessage() + " " + response.toString());
								}
								myTasks.finishWithResult(StatusKeys.WEATHER, currentWeatherAtAirport);
							}
							
							@Override
							public void onFailure(Throwable error, JSONObject obj) {
								Log.e("WEATHER", error.getMessage());
								myTasks.finishOneTask();
							}
						};
					}
					
					@Override
					public void execute() {
						FlightStatsClient.getWeatherConditions(airportCode, this.handler);
					}
				});
				
				myTasks.addTask(new NetworkTask() {
					@Override
					public void setHandler() {
						this.handler = new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject response) {
								String[] outcomes = context.getResources().getStringArray(R.array.txtDelayLabels);
								try {
									int delaySeverityIndex = FlightStatsClient.getDelayIndex(response);
									Log.d("DELAY SEVERITY", outcomes[delaySeverityIndex]);
									myTasks.finishWithResult(StatusKeys.DELAYS, outcomes[delaySeverityIndex]);
								} catch (Exception e) {
									myTasks.finishWithResult(StatusKeys.DELAYS, context.getResources().getString(R.string.txtDelaysError));
								}
							}
							
							@Override
							public void onFailure(Throwable error, JSONObject obj) {
								Log.e("DELAYS", error.getMessage());
								myTasks.finishOneTask();
							}
						};
					}
					
					@Override
					public void execute() {
						FlightStatsClient.getDelayDegree(airportCode, this.handler);
					}
				});
				
				myTasks.startAll();
				*/
	} 

	protected abstract void handleResult(Bundle response);
	
	@Override
	public void update(Observable observable, Object response) {
		this.handleResult((Bundle) response);
	} 
}
