package com.in28minutes.hbs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.hbs.bean.Hotels;
import com.in28minutes.hbs.bean.Location;

@Repository
public interface HotelJpaRepository extends JpaRepository<Hotels, Integer>{

}
