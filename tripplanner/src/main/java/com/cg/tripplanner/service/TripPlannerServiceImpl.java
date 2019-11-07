package com.cg.tripplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.Hotel;
import com.cg.tripplanner.dto.HotelBooking;
import com.cg.tripplanner.dto.Location;
import com.cg.tripplanner.dto.Transport;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.exception.TripException;
import com.cg.tripplanner.exception.TripExceptionMessage;
import com.cg.tripplanner.repository.BookingRepository;
import com.cg.tripplanner.repository.HotelBookingRepository;
import com.cg.tripplanner.repository.HotelRepository;
import com.cg.tripplanner.repository.LocationRepository;
import com.cg.tripplanner.repository.TransportRepository;
import com.cg.tripplanner.repository.UserRepository;

@Service("tripPlannerService")
public class TripPlannerServiceImpl implements TripPlannerService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HotelBookingRepository HotelBookingRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private TransportRepository transportRepository;
	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public User addUser(User user) throws TripException {
		// TODO Auto-generated method stub
		try {
			userRepository.save(user);
			return user;
		}catch(Exception e) {
			throw new TripException(TripExceptionMessage.DUPLICATEEMAIL);
		}
	}

	@Override
	public User updateUser(User user) throws TripException {
		try{
			User updateUser = userRepository.findByUserId(user.getUserId());
			if(updateUser != null) {
				userRepository.save(user);
				return user;
			}
			else {
				throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
			}
		}catch(Exception e) {
			throw new TripException(TripExceptionMessage.DUPLICATEEMAIL);
		}
	}

	@Override
	public User searchUser(Long userId) throws TripException {
		User user = userRepository.findByUserId(userId);
		if(user != null) {
			return user;
		}
		else {
			throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
		}
	}

	@Override
	public Hotel addHotel(Hotel hotel) throws TripException {
		try {
			hotelRepository.save(hotel);
			return hotel;
		}catch(Exception e) {
			throw new TripException(e.getMessage());
		}
	}

	@Override
	public Hotel updateHotel(Hotel hotel) throws TripException {
		Hotel updateHotel = hotelRepository.findByHotelId(hotel.getHotelId());
		if(updateHotel != null) {
			updateHotel.setHotelName(hotel.getHotelName());
			updateHotel.setHotelCost(hotel.getHotelCost());
			updateHotel.setDescription(hotel.getDescription());
			updateHotel.setNumberOfRooms(hotel.getNumberOfRooms());
			hotelRepository.save(updateHotel);
			return updateHotel;
		}else {
			throw new TripException(TripExceptionMessage.HOTELNOTEXIST);
		}
	}

	@Override
	public Hotel removeHotel(Long hotelId) throws TripException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hotel searchHotel(Long hotelId) throws TripException {
		Hotel hotel = hotelRepository.findByHotelId(hotelId);
		if(hotel != null) {
			return hotel;
		}
		else {
			throw new TripException(TripExceptionMessage.HOTELNOTEXIST);
		}
	}

	@Override
	public List<Location> findAllLocation() throws TripException {
		return locationRepository.findAll();
	}

	@Override
	public Location searchLocation(Long locationId) throws TripException {
		Location location = locationRepository.findByLocationId(locationId);
		if(location != null) {
			return location;
		}
		else {
			throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
		}
	}

	@Override
	public Booking bookTransport(Long userId, Booking booking, Long locationId, Transport transport) throws TripException {
		User user = userRepository.findByUserId(userId);
		Location location = locationRepository.findByLocationId(locationId);
		Transport foundTransport = transportRepository.findByTransportId(transport.getTransportId());
		if(user != null && location != null && foundTransport != null) {
			user.setPlannedTrip(location);
			user.setTripCost(user.getTripCost()+ booking.getBookingTransport().getTravelCost());
			booking.setBookingUser(user);
			booking.setBookingTransport(transport);			
			bookingRepository.save(booking);
			return booking;
		}
		else if(user == null){
			throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
		}
		else {
			throw new TripException(TripExceptionMessage.LOCATIONNOTEXIST);
		}
		
	}

	@Override
	public HotelBooking bookHotel(Long bookingId, HotelBooking hotelBooking) throws TripException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location addLocation(Location location) throws TripException {
		locationRepository.save(location);
		return location;
	}

	@Override
	public Transport addTransport(Transport transport) throws TripException {
		transportRepository.save(transport);
		return transport;
	}

	
}
