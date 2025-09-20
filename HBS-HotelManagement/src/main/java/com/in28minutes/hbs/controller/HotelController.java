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

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.exception.LocationNotFoundException;
import com.in28minutes.hbs.service.HotelService;

import jakarta.validation.Valid;

@RestController
public class HotelController {

	@Autowired
	private HotelService service;

	@GetMapping("/hotels")
	public ResponseEntity<List<Hotels>> retriveAllHotels() {
		List<Hotels> details = service.findAll();
		if(details.isEmpty()) {
			throw new LocationNotFoundException("No hotel found! Please try again later.");
		}
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/hotel/{id}")
	public ResponseEntity<Hotels> retriveOneHotel(@PathVariable int id) {
		Hotels hotelById = service.getHotelById(id).get();
		if(hotelById == null) {
			throw new LocationNotFoundException("Hotel not found with id " + id);
		}
		return new ResponseEntity<>(hotelById, HttpStatus.OK);
	}
	
	@GetMapping("/location/{data}")
	public ResponseEntity<List<String>> retriveOneHotel(@PathVariable String data) {
		List<String> hotelById = service.findAllDistict(data);
		if(hotelById.isEmpty()) {
			throw new LocationNotFoundException("Location not found with " + data);
		}
		return new ResponseEntity<>(hotelById, HttpStatus.OK);
	}

	@GetMapping("/hotel/{city}/{country}")
	public ResponseEntity<List<Hotels>> retriveByLocation(@PathVariable String city,@PathVariable String country) {
		List<Hotels> hotelByLocation = service.getHotelByLocation(city,country);

		if(hotelByLocation.isEmpty()) {
			throw new LocationNotFoundException("Hotel not found in " + city + ", " + country);
		}

		return new ResponseEntity<>(hotelByLocation,HttpStatus.OK);
	}


	@PostMapping("/hotel")
	public ResponseEntity<Hotels> insertHotel(@Valid @RequestBody Hotels hotel) {
		return new ResponseEntity<>(service.addNewHotel(hotel), HttpStatus.CREATED);
	}

	@PutMapping("hotel/{id}")
	public ResponseEntity<Hotels> modifyHotelDetails(@PathVariable int id,@Valid @RequestBody Hotels updatedDetails) {
		return new ResponseEntity<>(service.updateDetails(id,updatedDetails),HttpStatus.OK);
	}

	@DeleteMapping("/hotel/{id}")
	public ResponseEntity<Hotels> deleteHotelDetails(@PathVariable int id) {
		return new ResponseEntity<>( service.deleteHotel(id),HttpStatus.OK);
	}


}
