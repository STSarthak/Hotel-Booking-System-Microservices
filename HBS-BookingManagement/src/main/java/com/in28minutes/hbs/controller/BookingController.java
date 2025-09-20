package com.in28minutes.hbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.hbs.entity.Booking;
import com.in28minutes.hbs.service.BookingService;

import jakarta.validation.Valid;



@RestController
public class BookingController {

	@Autowired
	private BookingService service;
	
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> retrieveAllBookings() {
		return new ResponseEntity<>(service.getAllBookings(),HttpStatus.OK);
	}
	
	@GetMapping("/hotel/{hotelId}/bookings")
	public ResponseEntity<List<Booking>> reteieveBookingByHotelId(@PathVariable int hotelId) {
		return new ResponseEntity<>(service.getBookingByHotelId(hotelId),HttpStatus.OK);
	}
	
	@PostMapping("/hotel/{hotelId}/room/{roomType}/bookings")
	public ResponseEntity<Booking> newBooking(@PathVariable int hotelId, @PathVariable String roomType,@Valid @RequestBody Booking booking) {
		return new ResponseEntity<Booking>(service.addBooking(hotelId,roomType,booking),HttpStatus.CREATED);
	}
	
	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> changeBooking(@PathVariable int bookingId, @RequestBody Booking booking) {
		
		return new ResponseEntity<Booking>(service.updateBooking(bookingId,booking),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> deleteBooking(@PathVariable int bookingId){
		return new ResponseEntity<Booking>(service.deleteBookingById(bookingId),HttpStatus.OK);
	}
}
