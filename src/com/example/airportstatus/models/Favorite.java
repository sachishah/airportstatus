package com.example.airportstatus.models;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name="Favorites")
public class Favorite extends Model {
	@Column(name="airport_code")
	private String airportCode;
	
	public Favorite(String code) {
		if (code.length() < 3) {
			throw new IllegalArgumentException("Invalid airport code");
		}
		this.airportCode = code;
		this.save();
	}
	
	public static ArrayList<Favorite> getAll() {
		List<Favorite> all = new Select().from(Favorite.class)
				.execute();
		return (ArrayList<Favorite>) all;
	}
}
