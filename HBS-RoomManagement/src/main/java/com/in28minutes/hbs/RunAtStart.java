package com.in28minutes.hbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.in28minutes.hbs.entity.Room;
import com.in28minutes.hbs.repository.RoomJpaRepository;

@Component
public class RunAtStart implements CommandLineRunner {

	@Autowired
	private RoomJpaRepository roomJpaRepository;
	
	@Override
	public void run(String... args) throws Exception {


		if(roomJpaRepository.findAll().isEmpty()) {
			List<Room> rooms = List.of(new Room(101,"Normal",1000.00,true,1,2),
									new Room(102,"Suite",5000.00,true,1,3),
									new Room(103,"Luxuary",15000.00,true,1,1),
									new Room(104,"Family",2000.00,false,2,0)
									);
			
			roomJpaRepository.saveAll(rooms);
		}
	}
}
