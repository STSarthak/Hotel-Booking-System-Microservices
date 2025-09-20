package com.in28minutes.hbs.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROOM_ID")
	private int roomId; 
	
	@Column(name = "ROOM_TYPE")
	@Size(min = 2,message = "Atleast 2 characters")
	@NotEmpty
	private String roomType;
	
	@NotNull(message = "Price cannot be null")
	@DecimalMin(value = "0.0",inclusive = false,message = "Please enter the price greater than 0.")
	@Column(name = "PRICE_PER_NIGHT")
	private double pricePerNight;
	
	@NotNull
	@Column(name = "AVAILABILITY_STATUS")
	private boolean availabilityStatus;
	
	@NotNull
	@Column(name = "HOTEL_ID")
	private int hotelId;
	
	@NotNull
	@Column(name = "ROOMS_AVAILABLE")
	private int numberOfRoomsAvailable;



	public Room() {
		super();
	}
	
	public Room(int roomId, String roomType, Double pricePerNight, boolean availabilityStatus, int hotelId, int numberOfRoomsAvailable) {
		super();
		this.roomId = roomId;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.availabilityStatus = availabilityStatus;
		this.hotelId = hotelId;
		this.numberOfRoomsAvailable = numberOfRoomsAvailable;
	}

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

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public boolean getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	
	

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	public int getNumberOfRoomsAvailable() {
		return numberOfRoomsAvailable;
	}

	public void setNumberOfRoomsAvailable(int numberOfRoomsAvailable) {
		this.numberOfRoomsAvailable = numberOfRoomsAvailable;
	}


	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", " + (roomType != null ? "roomType=" + roomType + ", " : "")
				+ "pricePerNight=" + pricePerNight + ", availabilityStatus=" + availabilityStatus + ", hotelId="
				+ hotelId + "]";
	}
	
	



	
	
}
