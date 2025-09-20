package com.in28minutes.hbs.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOKING_ID")
	private int bookingId;
	
	@Column(name = "HOTEL_ID")
	private int hotelId;
	
	@Column(name = "ROOM_ID")
	private int roomId;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "ROOM_TYPE")
	private String roomType;
	
	@Column(name = "BOOKING_PRICE")
	private double price;
	
	@Column(name = "NUMBER_OF_GUEST")
	private int numberOfGuests;
	
	@NotNull(message = "CheckIn Date cannot be null")
	@FutureOrPresent(message = "CheckIn Date should be in future")
	@Column(name = "CHECK_IN_DATE")
	private LocalDate checkInDate;
	
	@NotNull(message = "CheckOut Date cannot be null")
	@FutureOrPresent(message = "CheckOut Date should be in future")
	@Column(name = "CHECK_OUT_DATE")
	private LocalDate checkOutDate;
	
	@Column(name = "MORE_AVAILABLE")
	private boolean moreAvailable;
	
	public Booking() {
		super();
		}

	public Booking(int bookingId, int hotelId, int roomId, String username,String roomType, double price, int numberOfGuests, LocalDate checkInDate,
			LocalDate checkOutDate,boolean moreAvailable) {
		super();
		this.bookingId = bookingId;
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.roomType = roomType;
		this.price = price;
		this.username = username;
		this.numberOfGuests = numberOfGuests;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.moreAvailable = moreAvailable;
	}

	
	
	public boolean isMoreAvailable() {
		return moreAvailable;
	}

	public void setMoreAvailable(boolean moreAvailable) {
		this.moreAvailable = moreAvailable;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", hotelId=" + hotelId + ", roomId=" + roomId + ", price=" + price
				+ ", " + (checkInDate != null ? "checkInDate=" + checkInDate + ", " : "")
				+ (checkOutDate != null ? "checkOutDate=" + checkOutDate : "") + "]";
	}
	
	

}
