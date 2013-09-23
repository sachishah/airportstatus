package com.example.airportstatus.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class Checkpoint extends BaseModel {
	

	public String getShortName(){
		return getString("shortname");
	}
	
	public String getLongName(){
		return getString("longname");
	}
	
	public static Checkpoint fromJson(JSONObject jsonObject) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.jsonObject = jsonObject;
        return checkpoint;
    }
	
	public static ArrayList<Checkpoint> fromJson(JSONArray pjsonArray) {
		JSONArray jsonArray = null;
		try {
			jsonArray = pjsonArray.getJSONObject(0).getJSONObject("airport").getJSONArray("checkpoints");
		} catch (JSONException e1) {
			
			e1.printStackTrace();
		}
        ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject checkpointJson = null;
            try {
                checkpointJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Checkpoint checkpoint = Checkpoint.fromJson(checkpointJson);
            if (checkpoint != null) {
                checkpoints.add(checkpoint);
            }
        }

        return checkpoints;
    }

	@Override
	public String toString() {
		return getJSONString();
	}

}
