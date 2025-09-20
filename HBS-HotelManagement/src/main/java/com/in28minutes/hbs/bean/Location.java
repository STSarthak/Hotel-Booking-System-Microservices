package com.in28minutes.hbs.bean;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private int locationId;

	@NotEmpty
	@Size(min = 3,message = "Line1 should have at least 3 characters")
	private String line1;

	@NotEmpty
	@Size(min = 3,message = "City should have at least 3 characters")
	private String city;

	@NotEmpty
	@Size(min = 3,message = "Country should have at least 3 characters")
	private String country;

	@Min(value = 100, message = "Pincode should be greater than 3 digits")
	@Max(value = 99999999,message = "Pincode should be less than 8 digits")
	@Column(name = "PINCODE")
	private int pincode;


	@OneToOne(mappedBy = "hotelLocation")
	private Hotels hotel;

	public Location() {
		super();
	}
	public Location(int id, String line1, String city, String country, int pincode) {
		super();
		this.locationId = id;
		this.line1 = line1;
		this.city = city;
		this.country = country;
		this.pincode = pincode;
	}


	public int getId() {
		return locationId;
	}


	public void setId(int id) {
		this.locationId = id;
	}


	public String getLine1() {
		return line1;
	}


	public void setLine1(String line1) {
		this.line1 = line1;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}



//	public Hotels getHotel() {
//		return hotel;
//	}
//
//
//	public void setHotel(Hotels hotel) {
//		this.hotel = hotel;
//	}


	@Override
	public String toString() {
		return "Location [Id=" + locationId + ", line1=" + line1 + ", city=" + city + ", country=" + country
				+ ", pincode=" + pincode + "]";
	}




}
