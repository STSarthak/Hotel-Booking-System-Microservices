package com.in28minutes.hbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.exception.LocationNotFoundException;
import com.in28minutes.hbs.service.HotelService;

import jakarta.validation.Valid;

@RestController
public class HotelController {
	
	@Autowired
	private HotelService hotelJpaRepository;
	
	@GetMapping("/hotels")
	public ResponseEntity<List<Hotels>> retriveAllHotels() {
		return new ResponseEntity<>(hotelJpaRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/hotel/{id}")
	public ResponseEntity<Hotels> retriveOneHotel(@PathVariable int id) {
		return new ResponseEntity<>(hotelJpaRepository.getHotelById(id), HttpStatus.OK);
	}
	
	@GetMapping("/hotel/{city}/{country}")
	public ResponseEntity<List<Hotels>> retriveByLocation(@PathVariable String city,@PathVariable String country) {
		List<Hotels> hotelByLocation = hotelJpaRepository.getHotelByLocation(city,country);
		
		if(hotelByLocation.isEmpty()) {
			throw new LocationNotFoundException("Hotel not found in this " + city + ", " + country);
		}
		
		return new ResponseEntity<>(hotelByLocation,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/hotel")
	public ResponseEntity<Hotels> insertHotel(@Valid @RequestBody Hotels hotel) {
		return new ResponseEntity<>(hotelJpaRepository.addNewHotel(hotel), HttpStatus.CREATED);
	}
	

}
