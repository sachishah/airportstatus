package com.example.airportstatus;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.airportstatus.models.AirportStatusLocation;

public class LocationPreferences {
	public static final String PREFS_NAME = "AirportStatusPrefs";
	public static final String PREFS_LATITUDE = "LAT";
	public static final String PREFS_LONGITUDE= "LON";
	public static final int TIMER_PERIOD = 20000;
	LocationResult locationResult;
	LocationManager manager;
	Timer timer;
	boolean gpsEnabled;
	boolean networkEnabled;
	
	public boolean getCurrentLocation(Context context, LocationResult result) {
		locationResult = result;
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try {
			gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			Log.e("LOCATION", "Could not test GPS availability");
		}
		
		try {
			networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception e) {
			Log.e("LOCATION", "Could not test network availability");
		}
		
		if (!gpsEnabled && !networkEnabled) {
			return false;
		}
		
		if (gpsEnabled) {
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}
		
		if (networkEnabled) {
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
		}
		
		timer = new Timer();
		timer.schedule(new GetLastLocation(), TIMER_PERIOD);
		return true;
	}
	
	class GetLastLocation extends TimerTask {
        @Override
        public void run() {
             manager.removeUpdates(locationListenerGps);
             manager.removeUpdates(locationListenerNetwork);

             Location net_loc = null, gps_loc = null;
             if (gpsEnabled)
                 gps_loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if (networkEnabled)
                 net_loc=manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

             //if there are both values use the latest one
             if (gps_loc != null && net_loc != null){
                 if (gps_loc.getTime() > net_loc.getTime())
                     locationResult.receivedLocation(gps_loc);
                 else
                     locationResult.receivedLocation(net_loc);
                 return;
             }

             if (gps_loc != null){
                 locationResult.receivedLocation(gps_loc);
                 return;
             }
             if (net_loc != null){
                 locationResult.receivedLocation(net_loc);
                 return;
             }
             locationResult.receivedLocation(null);
        }
    }
	
	LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.receivedLocation(location);
            manager.removeUpdates(this);
            manager.removeUpdates(locationListenerNetwork);
        }
        
        @Override
        public void onProviderDisabled(String provider) {}
        
        @Override
        public void onProviderEnabled(String provider) {}
        
        @Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
    };
    
    
    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.receivedLocation(location);
            manager.removeUpdates(this);
            manager.removeUpdates(locationListenerGps);
        }
        
        @Override
        public void onProviderDisabled(String provider) {}
        
        @Override
        public void onProviderEnabled(String provider) {}
        
        @Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
    };
    
    
	/*
	public static AirportStatusLocation getCurrentLocation(Context context, LocationResult result) {
		locationResult 
		try {
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
			double lat = settings.getFloat(PREFS_LATITUDE, -1);
			double lon = settings.getFloat(PREFS_LONGITUDE, -1);
				
			if (lat < 0 || lon < 0) {
				throw new Exception("No location preferences have been set");
			}
			return new AirportStatusLocation(lat, lon);
		} catch (Exception e) {
			Log.e("LOCATION_PREFERENCES_ERROR", e.getMessage());
			LocationManager m = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			Location current = m.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (current == null) {
				Log.e("LOCATION_FAIL", "Current location should not be null");
				return new AirportStatusLocation(37.76030, -122.41051);
			}
			
			updateLastLocationPreferences(context, current.getLatitude(), current.getLongitude());
			return new AirportStatusLocation(current.getLatitude(), current.getLongitude());
		}	
	}
	*/
  
	public static void setLastLocationPreferences(Context context, double lat, double lon) {
		SharedPreferences locationPrefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = locationPrefs.edit();
		editor.putString(PREFS_LATITUDE, String.valueOf(lat));
		editor.putString(PREFS_LONGITUDE, String.valueOf(lon));
		editor.commit();
	}
	
	public static AirportStatusLocation getLastLocationPreferences(Context context) throws Exception {
		SharedPreferences locationPrefs = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
		try {
			double lat = Double.valueOf(locationPrefs.getString(PREFS_LATITUDE, null));
			double lon = Double.valueOf(locationPrefs.getString(PREFS_LONGITUDE, null));
			return new AirportStatusLocation(lat, lon);
		} catch(Exception e) {
			throw new Exception("No location preferences have been set");
		}
	}
}
