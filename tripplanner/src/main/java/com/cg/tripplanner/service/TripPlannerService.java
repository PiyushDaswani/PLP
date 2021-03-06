/**
 * 
 */
package com.cg.tripplanner.service;

import java.io.IOException;
import java.util.List;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.Hotel;
import com.cg.tripplanner.dto.HotelBooking;
import com.cg.tripplanner.dto.Location;
import com.cg.tripplanner.dto.Transport;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.exception.TripException;

/**
 * @author Piyush
 *
 */
public interface TripPlannerService {

	public User addUser(User user) throws TripException;
	public User updateUser(User user) throws TripException;
	public User searchUser(Long userId)throws TripException;
	public Hotel addHotel(Hotel hotel) throws TripException;
	public Hotel updateHotel(Hotel hotel) throws TripException;
	public Hotel searchHotel(Long hotelId) throws TripException;
	public List<Location> findAllLocation() throws TripException;
	public Location searchLocation(Long locationId) throws TripException;
	public Location addLocation(Location location) throws TripException;
	public Booking bookTransport(Long userId, Booking booking,Long locationId, Long transportId) throws TripException;
	public HotelBooking bookHotel(Long userId, HotelBooking booking, Long hotelId) throws TripException;
	public Transport addTransport(Transport transport) throws TripException;
	public User findByEmail(String email) throws TripException;
	public List<Transport> findTransports(String departureFrom,String arrivalAt, String transportMode )throws TripException;
	public void readFromExcel(String locationName, String fileName, long time) throws IOException, TripException;
}
