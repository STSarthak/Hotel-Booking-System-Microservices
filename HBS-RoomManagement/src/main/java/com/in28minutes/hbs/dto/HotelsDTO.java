package com.in28minutes.hbs.dto;

public class HotelsDTO {
	
	private int hotelId;
	private String hotelName;
	private String hotelDescription;
	private int hotelRating;


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
				+ hotelRating + "]";
	}



}






