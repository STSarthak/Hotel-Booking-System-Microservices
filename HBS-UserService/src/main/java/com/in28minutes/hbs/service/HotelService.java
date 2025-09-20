package com.in28minutes.hbs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.bean.Location;
import com.in28minutes.hbs.repository.HotelJpaRepository;
import com.in28minutes.hbs.repository.LocationJpaRepository;

import jakarta.transaction.Transactional;


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
				locationJpaRepository.findById(m.getHotelId()).get()
				)
			)
		.collect(Collectors.toList());
		return hotelDetails;
	}

	public List<Hotels> findAll() {
		List<Hotels> hotelDetails = hotelJpaRepository.findAll();
		return hotelDetailsStream(hotelDetails);
	}

	public Hotels addNewHotel(Hotels hotel) {
		return hotelJpaRepository.save(hotel);
	}

	public Hotels getHotelById(int id) {
		return hotelJpaRepository.findById(id).get();
	}

	public List<Hotels> getHotelByLocation(String city,String country) {
		List<Location> hotelIds = locationJpaRepository.findByCityAndCountry(city,country);
		List<Hotels> hotelDetails = hotelIds.stream().map(m -> hotelJpaRepository.findById(m.getHotelId()).get()).collect(Collectors.toList());
		return hotelDetailsStream(hotelDetails);
	}
	
}
