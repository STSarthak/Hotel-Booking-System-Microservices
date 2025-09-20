package com.in28minutes.hbs.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Location {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@NotEmpty
	@Size(min = 3)
	private String line1;
	
	@NotEmpty
	@Size(min = 3)
	private String city;
	
	@NotEmpty
	@Size(min = 3)
	private String country;
	
	@NotEmpty
	@Size(min = 3)
	private int pincode;
	

	@OneToOne(mappedBy = "hotelLocation")
	private Hotels hotel;
	
	public Location() {
		super();
	}
	public Location(int hotelId, String line1, String city, String country, int pincode) {
		super();
		this.id = hotelId;
		this.line1 = line1;
		this.city = city;
		this.country = country;
		this.pincode = pincode;
	}


	public int getHotelId() {
		return id;
	}


	public void setHotelId(int hotelId) {
		this.id = hotelId;
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
		return "Location [hotelId=" + id + ", line1=" + line1 + ", city=" + city + ", country=" + country
				+ ", pincode=" + pincode + "]";
	}
	
	
	
	
}
