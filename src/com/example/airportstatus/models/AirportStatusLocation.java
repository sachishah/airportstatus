package com.example.airportstatus.models;

public class AirportStatusLocation extends BaseModel {
	private double latitude;
	private double longitude;
	
	public AirportStatusLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return Double.toString(this.latitude) + "," + Double.toString(this.longitude);
	}
}
