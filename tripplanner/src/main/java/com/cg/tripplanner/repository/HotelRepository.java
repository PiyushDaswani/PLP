package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
