package com.airportstatus.models;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name="Favorites")
public class Favorite extends Model {
	@Column(name="airport_code")
	private String airportCode;

	public void setAirportCode(String code) {
		this.airportCode = code;
	}
	
	public static ArrayList<String> getAllCodes() {
		List<Favorite> favorited = new Select().from(Favorite.class)
				.execute();
		ArrayList<String> codes = new ArrayList<String>();
		for (Favorite f : favorited) {
			codes.add(f.airportCode);
		}
		return codes;
	}
	
	public static boolean exists(String query) {
		List<Favorite> res = new Select().from(Favorite.class)
			.where("airport_code = ?", query)
			.execute();
		return (res.size() > 0);
	}
	
	public static void delete(String query) {
		new Delete().from(Favorite.class).where("airport_code = ?", query).execute();
	}
}
