package com.in28minutes.hbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.hbs.entity.Booking;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, Integer>{

	List<Booking> findByHotelId(int hotelId);

}
