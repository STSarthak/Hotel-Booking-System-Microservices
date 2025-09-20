package com.in28minutes.hbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.bean.Location;
import com.in28minutes.hbs.repository.HotelJpaRepository;

@Component
public class RunAtStart implements CommandLineRunner{

	@Autowired
	private HotelJpaRepository hotelJpaRepository;

	@Override
	public void run(String... args) throws Exception {
		Location location1 = new Location(1001,"Aerocity", "Delhi", "India", 110017);
		Location location2 = new Location(1002,"Bandra", "Mumbai", "India", 440048);
		Location location3 = new Location(1003,"Port Blair", "Andaman & Nicobar Islands", "India", 110017);

		Hotels entity = new Hotels(1001,"Oberoi","By Sarthak",5);
		//locationJpaRepository.save(location1);
		entity.setHotelLocation(location1);

		hotelJpaRepository.save(entity);
		Hotels entity2 = new Hotels(1002,"Hotel Taj","By Palak",5);
		//locationJpaRepository.save(location2);
		entity2.setHotelLocation(location2);
		hotelJpaRepository.save(entity2);
		Hotels entity3 = new Hotels(1003,"Lemon Hotel","By Anita",5);
		//locationJpaRepository.save(location3);
		entity3.setHotelLocation(location3);
		hotelJpaRepository.save(entity3);



	}

}
