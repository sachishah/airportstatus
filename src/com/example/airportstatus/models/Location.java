package com.example.airportstatus.models;

public class Location extends BaseModel {
	private double latitude;
	private double longitude;
	
	public Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return Double.toString(this.latitude) + "," + Double.toString(this.longitude);
	}
}
