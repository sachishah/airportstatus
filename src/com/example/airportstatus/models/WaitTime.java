package com.example.airportstatus.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class WaitTime extends BaseModel{
	
	public Integer getCheckpoint(){
		return getInt("CheckpointIndex");
	}
	
	public String getWaitTime(){
		Integer waitIndex = getInt("WaitTimeIndex");
		String waitTimeStr = "na";
		switch (waitIndex) {
		case 1: waitTimeStr = "No wait";
			break;
		case 2: waitTimeStr = "1 - 10 mins";
			break;
		case 3: waitTimeStr = "11 - 20 mins";
			break;
		case 4: waitTimeStr = "21 - 20 mins";
			break;
		case 5: waitTimeStr = "31+ mins";
			break;
		}
		return waitTimeStr;
	}
	
	public String getTimestamp(){
		String mytime = getString("Created_Datetime");
		
		return mytime;
	}
	
	public static WaitTime fromJson(JSONObject jsonObject) {
        WaitTime waitTime = new WaitTime();
        waitTime.jsonObject = jsonObject;
        return waitTime;
    }
	
	public static ArrayList<WaitTime> fromJson(JSONArray jsonArray) {
		
		ArrayList<WaitTime> waitTimes = new ArrayList<WaitTime>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject waitTimeJson = null;
            try {
            	waitTimeJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            WaitTime waitTime = WaitTime.fromJson(waitTimeJson);
            if (waitTime != null) {
            	waitTimes.add(waitTime);
            }
        }

        return waitTimes;
    }

}
