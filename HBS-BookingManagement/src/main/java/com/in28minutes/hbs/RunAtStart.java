package com.in28minutes.hbs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.in28minutes.hbs.entity.Booking;
import com.in28minutes.hbs.repository.BookingJpaRepository;

@Component
public class RunAtStart implements CommandLineRunner {
	
	@Autowired
	private BookingJpaRepository repository;

	@Override
	public void run(String... args) throws Exception {
		
		if(repository.findAll().isEmpty()) {
			List<Booking> list = List.of(new Booking(1001,1,1,"admin","Normal",2000.0,3,LocalDate.now(),LocalDate.now().plusDays(2),true),
										new Booking(1001,1,2,"admin","Family",2000.0,2,LocalDate.now(),LocalDate.now().plusDays(3),false),
										new Booking(1001,1,1,"admin","Normal",2000.0,1,LocalDate.now(),LocalDate.now().plusDays(2),false)
										);
			repository.saveAll(list);
		}

	}

}
