package com.example.airportstatus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class AirportStatus extends BaseModel {
	
	public String getName() {
		return getString("name");
	}
	
	public String getAvgDelay() {
    	return getStatusValue("avgDelay");
    }
	
    public String getTemp() {
    	return getWeatherValue("temp");
    }
	
	public String getWeather() {
    	return getWeatherValue("weather");
    }
	
    public String getIATA() {
        return getString("IATA");
    }

	public boolean getDelay() {
        return getBoolean("delay");
    }

    public String getDelayType() {
    	return getStatusValue("type");
    }
    
    public String getClosureBegin() {
    	return getStatusValue("closureBegin");
    }
    
    public String getClosureEnd() {
    	return getStatusValue("closureEnd");
    }
    
    public String getEndTime() {
    	return getStatusValue("endTime");
    }
    
    public String getMaxDelay() {
    	return getStatusValue("maxDelay");
    }
    
    public String getMinDelay() {
    	return getStatusValue("minDelay");
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