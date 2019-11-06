/**
 * 
 */
package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.Booking;

/**
 * @author Piyush
 *
 */
public interface BookingRepository extends JpaRepository<Booking, Long>{
	public Booking findByBookingId(Long bookingId);
}
