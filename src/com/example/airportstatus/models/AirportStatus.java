package com.example.airportstatus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class AirportStatus extends BaseModel {
	public boolean getDelay() {
        return getBoolean("delay");
    }

    public String getIATA() {
        return getString("IATA");
    }

    public String getName() {
        return getString("name");
    }
    
    public String getWeather() {
    	return getWeatherValue("weather");
    }
    
    public String getUpdateTime() {
    	try {
			return getWeatherObject().getJSONObject("meta").getString("updated");
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
    }
    
    public String getTemp() {
    	return getWeatherValue("temp");
    }
    
    public String getWind() {
    	return getWeatherValue("wind");
    }
    
    public String getReason() {
    	return getStatusValue("reason");
    }
    
    public String getClosureBegin() {
    	return getStatusValue("closureBegin");
    }
    
    public String getClosureEnd() {
    	return getStatusValue("closureEnd");
    }
    
    public String getAvgDelay() {
    	return getStatusValue("avgDelay");
    }
    
    public String getType() {
    	return getStatusValue("type");
    }

    public static AirportStatus fromJson(JSONObject json) {
        AirportStatus airport = new AirportStatus();

        try {
            airport.jsonObject = json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return airport;
    }
    
    private JSONObject getWeatherObject() {
    	return getJSON("weather");
    }
    
    private String getWeatherValue(String name) {
    	try {
    		return getWeatherObject().getString(name);
    	} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
    }
    
    private String getStatusValue(String name) {
    	try {
    		return getJSON("status").getString(name);
    	} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
    }
}