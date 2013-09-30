package com.example.airportstatus.models;

public class AirportStatusLocation extends BaseModel {
	private double latitude;
	private double longitude;
	
	public AirportStatusLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public AirportStatusLocation(String latitude, String longitude) {
		this.latitude = Double.valueOf(latitude);
		this.longitude = Double.valueOf(longitude);
	}
	
	@Override
	public String toString() {
		return Double.toString(this.latitude) + "," + Double.toString(this.longitude);
	}
}
