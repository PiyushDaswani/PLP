package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.HotelBooking;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {

}
