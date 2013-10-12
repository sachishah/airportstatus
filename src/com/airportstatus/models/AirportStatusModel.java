package com.airportstatus.models;

import org.json.JSONException;
import org.json.JSONObject;

public class AirportStatusModel extends BaseModel {
	
	public String getName() {
		return getString("name");
	}
	
	public String getAvgDelay() {
    	return getStatusValue("avgDelay");
    }

	public String getWeather() {
    	return getWeatherValue();
    }
	
	public String getVisibility() {
		return getVisibilityValue();
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

    public static AirportStatusModel fromJson(JSONObject json) {
        AirportStatusModel airport = new AirportStatusModel();

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
    
    private String getWeatherValue() {
    	try {
    		return getWeatherObject().getString("weather");
    	} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
    }
    
    private String getVisibilityValue() {
    	try {
    		return getWeatherObject().getString("visibility");
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