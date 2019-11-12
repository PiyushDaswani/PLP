package com.cg.tripplanner.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
			throw new TripException(TripExceptionMessage.LOCATIONNOTEXIST);
		}
	}

	@Override
	public Booking bookTransport(Long userId, Booking booking,Long locationId, Long transportId) throws TripException {
		User user = userRepository.findByUserId(userId);
		Location location = locationRepository.findByLocationId(locationId);
		Transport transport = transportRepository.findByTransportId(transportId);
		if(user != null && transport != null && location != null) {
			user.setPlannedTrip(location);
			user.setTripCost(user.getTripCost() + transport.getTravelCost());
			booking.setBookingTransport(transport);
			if(transport.getTotalSeats()-booking.getBookingSeats()<0) {
				throw new TripException("Sorry there arent that many seats left");
			}
			transport.setTotalSeats(transport.getTotalSeats()-booking.getBookingSeats());
			booking.setBookingUser(user);
			booking.setHotelBooking(null);
			transportRepository.save(transport);
			bookingRepository.save(booking);
			userRepository.save(user);
			return booking;
		}
		else if(user == null){
			throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
		}
		else if(location == null){
			throw new TripException(TripExceptionMessage.LOCATIONNOTEXIST);
		}
		else {
			throw new TripException(TripExceptionMessage.TRANSPORTNOTEXIST);
		}
		
	}

	@Override
	public HotelBooking bookHotel(Long userId, HotelBooking booking, Long hotelId) throws TripException {
		User user = userRepository.findByUserId(userId);
		Hotel hotel = hotelRepository.findByHotelId(hotelId);
		if(user != null && hotel!= null) {
			Booking book = user.getUserBooking().get(user.getUserBooking().size()-2);
			booking.setBookHotel(hotel);
			booking.setBooking(book);
			booking.setDuration(user.getPlannedTrip().getTripDuration()-1);
			HotelBookingRepository.save(booking);
			user.setTripCost(user.getTripCost() + hotel.getHotelCost()*booking.getRooms());
			if(hotel.getNumberOfRooms() - booking.getRooms()<0) {
				throw new TripException("Sorry there are not that many rooms left");
			}
			hotel.setNumberOfRooms(hotel.getNumberOfRooms() - booking.getRooms());
			userRepository.save(user);
			hotelRepository.save(hotel);
			return booking;
		}
		else if(user == null) {
			throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
		}
		else {
			throw new TripException(TripExceptionMessage.HOTELNOTEXIST);
		}
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

	@Override
	public User findByEmail(String email) throws TripException {
		User user = userRepository.findByUserEmail(email).get();
	if(user != null) {
		return user;
	}
	else {
		throw new TripException(TripExceptionMessage.USERDOESNOTEXIST);
	}
		
	}

	@Override
	public List<Transport> findTransports(String departureFrom, String arrivalAt, String transportMode)
			throws TripException {
		return transportRepository.findByDepartureFromAndArrivalAtAndTransportMode(departureFrom, arrivalAt, transportMode);
	}
	
	@Override
	public void readFromExcel(String locationName, String fileName, long time) throws IOException, TripException {
		String UPLOAD_DIRECTORY = System.getProperty("catalina.base")+ "\\Excel_Files";
		File dataFile = new File(UPLOAD_DIRECTORY + "\\" + time + fileName);
		FileInputStream fis = new FileInputStream(dataFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);    //Read the first sheet
		Row row;
		Location location = locationRepository.findByLocationName(locationName);   //Find the Location
		if(location!= null) {
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = (Row) sheet.getRow(i);
			String hotelName;
			if (row.getCell(0) == null) {
				throw new TripException(TripExceptionMessage.NAMEMISSING);   //If Name is not present
			} else { 
				hotelName = row.getCell(0).toString();
			}
			String shortDescription;
			if (row.getCell(1) == null) {
				throw new TripException(TripExceptionMessage.SHORTDESCMISSING);  //If Short Description is not present
			} else {
				shortDescription = row.getCell(1).toString();	
			}
			String longDescription;
			if (row.getCell(2) == null) {
				throw new TripException(TripExceptionMessage.LONGDESCMISSING);   //If Long Description is not present
			} else {
				longDescription = row.getCell(2).toString();
			}
			String cost;
			if (row.getCell(3) == null) {
				throw new TripException(TripExceptionMessage.COSTMISSING);   //If Cost is not present
			} else {
				cost = row.getCell(3).toString();
			}
			String rooms;
			if (row.getCell(4) == null) {
				throw new TripException(TripExceptionMessage.ROOMMISSING);   //If Rooms is not present
			} else {
				rooms = row.getCell(4).toString();
			}
			String images;
			if (row.getCell(5) == null) {
				throw new TripException(TripExceptionMessage.IMAGESMISSING);   //If Images is not present
			} else {
				images = row.getCell(5).toString();
			}
			String hotelRating;
			if (row.getCell(6) == null) {
				throw new TripException(TripExceptionMessage.RATINGMISSING);   //If Rating is not present
			} else {
				hotelRating = row.getCell(6).toString();
			}


			Hotel hotel = new Hotel();
			
			if(location == null) {
				throw new TripException(TripExceptionMessage.LOCATIONNOTEXIST);
			}
			List<String> addedImages = new ArrayList<String>();
			hotel.setHotelName(hotelName);
			hotel.setShortDescription(shortDescription);
			hotel.setDescription(longDescription);
			hotel.setHotelRating(Float.parseFloat(hotelRating));
			hotel.setHotelCost(Double.parseDouble(cost));
			hotel.setLocation(location);
			Double noRooms = Double.parseDouble(rooms);
			hotel.setNumberOfRooms(noRooms.intValue());	
			StringTokenizer token = new StringTokenizer(images, ",");
			
			
			while (token.hasMoreTokens()) {    //separate the options by splitting with comma
				addedImages.add(token.nextToken());
			}
			String imageAdd[] = new String[addedImages.size()];
			addedImages.forEach(image ->{
				imageAdd[addedImages.indexOf(image)]=image;
			});
			hotel.setImages(imageAdd);
			hotelRepository.save(hotel);
		}
		}
		else {
			throw new TripException(TripExceptionMessage.LOCATIONNOTEXIST);
		}
		fis.close();
	}
	
}
