package com.example.airportstatus.models;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="Favorites")
public class Favorite extends Model {
	@Column(name="airport_code")
	private String airportCode;

	public void setAirportCode(String code) {
		this.airportCode = code;
	}
	
	public static List<Favorite> getAll() {
		return new Select().from(Favorite.class)
				.execute();
	}
}
