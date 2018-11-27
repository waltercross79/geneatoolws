package com.waltercross.geneatoolcore;

public class Location {
	
	public String houseNumber;
	public String streetName;
    public String city;
    public String country;

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(String country, String streetName, String houseNumber, String city) {		
		this.country = country;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.city = city;
	}

}
