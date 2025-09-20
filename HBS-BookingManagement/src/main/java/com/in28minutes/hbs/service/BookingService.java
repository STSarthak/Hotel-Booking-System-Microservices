package com.in28minutes.hbs.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.in28minutes.hbs.dto.RoomDTO;
import com.in28minutes.hbs.entity.Booking;
import com.in28minutes.hbs.exception.DetailsNotFoundException;
import com.in28minutes.hbs.repository.BookingJpaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class BookingService {
	
	@Autowired
	private BookingJpaRepository bookingJpaRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Booking> getAllBookings() {
		List<Booking> all = bookingJpaRepository.findAll();
		if(all.isEmpty())
			throw new DetailsNotFoundException("There are no bookings currently.");
		return all;
	}

	public Booking addBooking(int hotelId, String roomType,@Valid Booking booking) {
		
		
		try {
			ResponseEntity<RoomDTO> roomDetails;
			roomDetails = restTemplate.getForEntity("http://localhost:8300/hotel/"+ hotelId +"/room/roomType/" + roomType, RoomDTO.class);
			if(booking.getCheckInDate().isAfter(booking.getCheckOutDate()))
				throw new DetailsNotFoundException("CheckOut date must be greater than checkIn date.");
			booking.setHotelId(hotelId);
			booking.setRoomType(roomType);
			booking.setRoomId(roomDetails.getBody().getRoomId());
			booking.setPrice(roomDetails.getBody().getPricePerNight());
			System.out.println(booking);
			restTemplate.put("http://localhost:8300/hotel/"+ hotelId +"/room/"+ roomType+"/booking/-1", new RoomDTO());
			if(roomDetails.getBody().getNumberOfRoomsAvailable()>1)
				booking.setMoreAvailable(true);
			else
				booking.setMoreAvailable(false);
				
			return bookingJpaRepository.save(booking);
		} catch (RestClientException e) {
			throw new DetailsNotFoundException("Failed to fetch room details. Please try again later");
		}
	}

	public List<Booking> getBookingByHotelId(int hotelId) {
		List<Booking> bookings = bookingJpaRepository.findByHotelId(hotelId);
		if (bookings.isEmpty()) 
			throw new DetailsNotFoundException("There are no bookings for this hotel");
		return bookings;
	}

	public Booking updateBooking(int bookingId, Booking booking) {
		Optional<Booking> bookingDetails = findBookingById(bookingId);
		if(booking.getCheckInDate().isAfter(booking.getCheckOutDate()))
			throw new DetailsNotFoundException("CheckOut date must be greater than checkIn date.");
		
		long noOfDays = ChronoUnit.DAYS.between(bookingDetails.get().getCheckOutDate(), bookingDetails.get().getCheckInDate()); 
		
		if(ChronoUnit.DAYS.between(booking.getCheckOutDate(), booking.getCheckInDate()) != noOfDays)
			throw new DetailsNotFoundException("Please select same number of days.");
		
		booking.setBookingId(bookingId);
		booking.setHotelId(bookingDetails.get().getHotelId());
		booking.setPrice(bookingDetails.get().getPrice());
		booking.setRoomId(bookingDetails.get().getRoomId());
		booking.setRoomType(bookingDetails.get().getRoomType());
		return bookingJpaRepository.save(booking);
	}

	private Optional<Booking> findBookingById(int bookingId) {
		Optional<Booking> bookingDetails = bookingJpaRepository.findById(bookingId);
		if(bookingDetails.isEmpty())
			throw new DetailsNotFoundException("Booking id is invalid. Please check it again.");
		return bookingDetails;
	}

	public Booking deleteBookingById(int bookingId) {
		Optional<Booking> bookingDetails = findBookingById(bookingId);
		restTemplate.put("http://localhost:8300/hotel/"+ bookingDetails.get().getHotelId() +"/room/"+ bookingDetails.get().getRoomType()+"/booking/1", new RoomDTO());
		bookingJpaRepository.deleteById(bookingId);;
		return bookingDetails.get();
	}
}
