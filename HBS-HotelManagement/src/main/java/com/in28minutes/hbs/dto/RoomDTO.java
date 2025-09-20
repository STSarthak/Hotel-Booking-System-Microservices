package com.in28minutes.hbs.dto;

public class RoomDTO {
	
	private int roomId; 
	private String roomType;
	private double pricePerNight;
	private boolean availabilityStatus;


	public int getRoomId() {
		return roomId;
	}


	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}


	public String getRoomType() {
		return roomType;
	}


	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}


	public double getPricePerNight() {
		return pricePerNight;
	}


	public void setPricePerNight(int pricePerNight) {
		this.pricePerNight = pricePerNight;
	}


	public boolean isAvailabilityStatus() {
		return availabilityStatus;
	}


	public void setAvailabilityStatus(boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
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
		return "Room [roomId=" + roomId + ", " + (roomType != null ? "roomType=" + roomType + ", " : "")
				+ "pricePerNight=" + pricePerNight + ", availabilityStatus=" + availabilityStatus + ", "
//				+ (hotel != null ? "hotel=" + hotel : "") 
				+ "]";
	}

	
	
	
}
