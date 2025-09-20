package com.in28minutes.hbs.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.hbs.bean.Location;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Integer>{
	List<Location> findByCityAndCountry(String city,String country);
}
