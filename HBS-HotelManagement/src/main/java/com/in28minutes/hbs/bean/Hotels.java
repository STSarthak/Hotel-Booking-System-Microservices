package com.in28minutes.hbs.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.in28minutes.hbs.dto.RoomDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Hotels {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HOTEL_ID")
	private int hotelId;

	@NotEmpty
	@Size(min = 3,message = "Name should have at least 3 characters")
	@Column(name = "HOTEL_NAME")
	private String hotelName;

	@Size(min = 3,message = "Description should have at least 3 characters")
	@Column(name = "HOTEL_DESCRIPTION")
	private String hotelDescription;

	@Min(value = 1,message = "Rating should be greater than 1")
	@Max(value = 5,message = "Rating should be less than 5")
	@Column(name = "HOTEL_RATING")
	private int hotelRating;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "locationId")
	@JsonIgnoreProperties(value = "hotelId")
	private Location hotelLocation;


	public Hotels() {
		super();
	}

	public Hotels(int hotelId, String hotelName, String hotelDescription, int rating) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelDescription = hotelDescription;
		this.hotelRating = rating;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Location getHotelLocation() {
		return hotelLocation;
	}

	public Location setHotelLocation(Location hotelLocation) {
		this.hotelLocation = hotelLocation;
		return hotelLocation;
	}

	public String getHotelDescription() {
		return hotelDescription;
	}

	public void setHotelDescription(String hotelDescription) {
		this.hotelDescription = hotelDescription;
	}


	public int getHotelRating() {
		return hotelRating;
	}

	public void setHotelRating(int hotelRating) {
		this.hotelRating = hotelRating;
	}

	@Override
	public String toString() {
		return "Hotels [hotelId=" + hotelId + ", " + (hotelName != null ? "hotelName=" + hotelName + ", " : "")
				+ (hotelDescription != null ? "hotelDescription=" + hotelDescription + ", " : "") + "hotelRating="
				+ hotelRating + ", " + (hotelLocation != null ? "hotelLocation=" + hotelLocation : "") + "]";
	}

}






