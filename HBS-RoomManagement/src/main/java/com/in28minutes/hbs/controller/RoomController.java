package com.in28minutes.hbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.hbs.entity.Room;
import com.in28minutes.hbs.service.RoomService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@GetMapping("/rooms")
	public ResponseEntity<List<Room>> retrieveAllRooms() {
		return new ResponseEntity<>(roomService.getAllRooms(),HttpStatus.OK);
	}
	
	@GetMapping("/hotel/{id}/rooms")
	public ResponseEntity<List<Room>> retrieveRoomByHotelId(@PathVariable int id) {
		return new ResponseEntity<>(roomService.getRoomByHotelId(id),HttpStatus.OK) ;
	}
	
	@GetMapping("/hotel/{id}/room/roomId/{roomId}")
	public ResponseEntity<Room> retrieveRoomByRoomType(@PathVariable int id, @PathVariable int roomId) {
		return new ResponseEntity<>(roomService.getByHotelIdAndRoomId(id,roomId),HttpStatus.OK) ;
	}
	
	@GetMapping("/hotel/{id}/room/roomType/{type}")
	public ResponseEntity<Room> retrieveRoomByRoomType(@PathVariable int id, @PathVariable String type) {
		return new ResponseEntity<>(roomService.getRoomByRoomType(id,type).get(0),HttpStatus.OK) ;
	}
	
	@PostMapping("/hotel/{id}/room")
	public ResponseEntity<Room> addNewRoom(@PathVariable int id,@Valid @RequestBody Room room) {
		return new ResponseEntity<>(roomService.addRoom(id,room),HttpStatus.CREATED) ;
	}
	
	@PutMapping("hotel/{id}/room")
	public ResponseEntity<Room> changeRoomDetails(@PathVariable int id,@Valid @RequestBody Room room) {
		return new ResponseEntity<Room>(roomService.updateRoomDetails(id,room),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("hotel/{id}/room/{type}/booking/{count}")
	public ResponseEntity<Room> updateAvailableRooms(@PathVariable int id,@PathVariable String type,@PathVariable int count) {
		return new ResponseEntity<Room>(roomService.updateRoomDetails(id,type,count),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("hotel/{id}/room/{roomId}")
	public ResponseEntity<Room> deleteRoomById(@PathVariable int id,@PathVariable int roomId) {
		return new ResponseEntity<Room>(roomService.deleteRoomById(id,roomId),HttpStatus.OK);
	}
}
