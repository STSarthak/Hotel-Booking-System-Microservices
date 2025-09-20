package com.in28minutes.hbs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.in28minutes.hbs.dto.HotelsDTO;
import com.in28minutes.hbs.entity.Room;
import com.in28minutes.hbs.exception.DetailsNotFoundException;
import com.in28minutes.hbs.repository.RoomJpaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class RoomService {
	
	@Autowired
	private RestTemplate restTemplate;
	

	@Autowired
	private RoomJpaRepository roomJpaRepository;

	public List<Room> getAllRooms() {
		List<Room> allRooms = roomJpaRepository.findAll();
		if(allRooms.isEmpty())
			throw new DetailsNotFoundException("No rooms are present");
		return allRooms;
	}


	public Room addRoom(int id,@Valid Room room) {
//		try {
			ResponseEntity<HotelsDTO> hotel = restTemplate.getForEntity("http://localhost:8200/hotel/" + id, HotelsDTO.class);
			if(hotel.getBody().getHotelId() == 0)
				 throw new DetailsNotFoundException("Failed to fetch hotel details ");
			List<Room> roomDetails = roomJpaRepository.findByHotelIdAndRoomType(id,room.getRoomType());
			if(!roomDetails.isEmpty()) {
				
					room.setNumberOfRoomsAvailable(roomDetails.get(0).getNumberOfRoomsAvailable()+1);
					room.setRoomId(roomDetails.get(0).getRoomId());
					
			}else {
				room.setNumberOfRoomsAvailable(1);
				room.setRoomId(room.getRoomId());
			}
			
			room.setHotelId(id);
			room.setRoomId(room.getRoomId());
			room.setAvailabilityStatus(true);
			return roomJpaRepository.save(room);
			

	}


	public List<Room> getRoomByHotelId(int id) {
		List<Room> hotelId = roomJpaRepository.findByHotelId(id);
		List<Room> filteredRooms = hotelId.stream().filter((data) -> data.getNumberOfRoomsAvailable()>0).collect(Collectors.toList());
		if (filteredRooms.isEmpty()) {
			throw new DetailsNotFoundException("No room found with the hotel id: " + id);
		}
		return filteredRooms;
	}


	public List<Room> getRoomByRoomType(int id, String roomType) {
			List<Room> rooms = roomJpaRepository.findByHotelIdAndRoomType(id,roomType);
			if(rooms.isEmpty())
				throw new DetailsNotFoundException("Rooms not available! Please select different room type.");
			
			return rooms;
	}
	

	public Room updateRoomDetails(int id,@Valid Room room ) {
		List<Room> findRoomId = getRoomByRoomType(id, room.getRoomType()); 
		if(findRoomId.isEmpty())
			throw new DetailsNotFoundException("No room found with room type: " + room.getRoomType());
		room.setHotelId(id);
		room.setRoomId(findRoomId.get(0).getRoomId());
		room.setNumberOfRoomsAvailable(findRoomId.get(0).getNumberOfRoomsAvailable());
		room.setAvailabilityStatus(true);
		return roomJpaRepository.save(room);
	}


	public Room deleteRoomById(int id,int roomId) {
		Room room = getByHotelIdAndRoomId( id, roomId);
		roomJpaRepository.deleteById(roomId);
		return room;
	}


	public Room getByHotelIdAndRoomId(int id, int roomId) {
		try {
			getRoomByHotelId(id);
			Room room = roomJpaRepository.findByHotelIdAndRoomId(id,roomId).get(0);
			return room;
		} catch (DetailsNotFoundException | IndexOutOfBoundsException e) {
			throw new DetailsNotFoundException("No room found with the id: " + roomId);
		}
	}

	public Room updateRoomDetails(int id, String type, int count) {
		List<Room> findRoomId = getRoomByRoomType(id, type); 
		if(findRoomId.isEmpty())
			throw new DetailsNotFoundException("No room found with room type: " + type);
		Room room = new Room();
		room.setHotelId(findRoomId.get(0).getHotelId());
		room.setRoomId(findRoomId.get(0).getRoomId());
		room.setPricePerNight(findRoomId.get(0).getPricePerNight());
		room.setRoomType(findRoomId.get(0).getRoomType());
		if(findRoomId.get(0).getNumberOfRoomsAvailable()+count <0)
			throw new DetailsNotFoundException("No room available");
		else
			room.setNumberOfRoomsAvailable(findRoomId.get(0).getNumberOfRoomsAvailable() + count);
		if(room.getNumberOfRoomsAvailable()>0)
			room.setAvailabilityStatus(true);
		else
			room.setAvailabilityStatus(false);
		return roomJpaRepository.save(room);
	}
	
}
