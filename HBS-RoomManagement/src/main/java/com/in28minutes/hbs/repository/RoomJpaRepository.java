package com.in28minutes.hbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.hbs.entity.Room;

@Repository
public interface RoomJpaRepository extends JpaRepository<Room, Integer>{
	public List<Room> findByHotelId(int hotelId);
	public List<Room> findByHotelIdAndRoomType(int id, String roomType);
	public List<Room> findByHotelIdAndRoomId(int id, int roomType);
}