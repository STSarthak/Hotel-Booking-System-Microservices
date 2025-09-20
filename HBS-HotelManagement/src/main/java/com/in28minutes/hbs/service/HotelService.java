package com.in28minutes.hbs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.bean.Location;
import com.in28minutes.hbs.exception.LocationNotFoundException;
import com.in28minutes.hbs.repository.HotelJpaRepository;
import com.in28minutes.hbs.repository.LocationJpaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
@Transactional
public class HotelService{

	@Autowired
	private HotelJpaRepository hotelJpaRepository;

	@Autowired
	private LocationJpaRepository locationJpaRepository;


	Logger logger = LoggerFactory.getLogger(HotelService.class);

	private List<Hotels> hotelDetailsStream(List<Hotels> hotelDetails){
		hotelDetails.stream()
		.map(m -> m.setHotelLocation(
				locationJpaRepository.findById(m.getHotelLocation().getId()).get()
				)
			)
		.collect(Collectors.toList());
		return hotelDetails;
	}



	public List<Hotels> findAll() {
		List<Hotels> hotelDetails = hotelJpaRepository.findAll();
		return hotelDetailsStream(hotelDetails);
	}
	
	public List<String> findAllDistict(String data) {
		List<Location> hotelDetails = locationJpaRepository.findAll();
		return hotelDetails.stream().map(location -> location.getCity().concat(", ").concat(location.getCountry())).filter(location -> location.contains(data)).distinct().collect(Collectors.toList());
	}

	public Hotels addNewHotel(@Valid Hotels hotel) {
		return hotelJpaRepository.save(hotel);
	}

	public Optional<Hotels> getHotelById(int id) {
		Optional<Hotels> byId = hotelJpaRepository.findById(id);
		if(byId.isEmpty()) {
			throw new LocationNotFoundException("Hotel details with id " + id + " can not found. Please check the entered details");
		}
		return byId;
	}

	public List<Hotels> getHotelByLocation(String city,String country) {
		List<Location> hotelIds = locationJpaRepository.findByCityAndCountry(city,country);
		List<Hotels> hotelDetails = hotelIds.stream().map(m -> hotelJpaRepository.findById(m.getId()).get()).collect(Collectors.toList());
		return hotelDetailsStream(hotelDetails);
	}


	public Hotels updateDetails(int id,@Valid Hotels updatedDetails) {
		Optional<Location> location = locationJpaRepository.findById(id);
		Optional<Hotels> hotel = hotelJpaRepository.findById(id);

		if(location.isEmpty() || hotel.isEmpty()) {
			throw new LocationNotFoundException("Hotel details not found. Please check the details");
		}
		updatedDetails.setHotelId(id);
		updatedDetails.getHotelLocation().setId(id);
		locationJpaRepository.save(updatedDetails.getHotelLocation());
		return addNewHotel(updatedDetails);
	}


	public Hotels deleteHotel(int id) {
		Optional<Hotels> checkDetails = getHotelById(id);
		hotelJpaRepository.deleteById(id);
		return checkDetails.get();
	}

}
