/**
 * 
 */
package com.cg.tripplanner.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.Hotel;
import com.cg.tripplanner.dto.HotelBooking;
import com.cg.tripplanner.dto.Location;
import com.cg.tripplanner.dto.Transport;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.exception.TripException;
import com.cg.tripplanner.exception.TripExceptionMessage;
import com.cg.tripplanner.service.TripPlannerService;
import com.cg.tripplanner.view.PDFView;

/**
 * @author Piyush
 *Desscription: All the user links
 */

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private TripPlannerService tripPlannerService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	/*
	 * Description: Method to update user
	 * Input: user
	 * Output: Success Message
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@ModelAttribute User user){
		try {
			logger.info("Entered Update user method");
			User updatedUser = tripPlannerService.searchUser(user.getUserId());
			if(updatedUser != null) {
				updatedUser.setUserName(user.getUserName());
				updatedUser.setUserEmail(user.getUserEmail());
				updatedUser.setUserPassword(user.getUserPassword());
				tripPlannerService.updateUser(updatedUser);
				logger.info("User updated successfully");
				return new ResponseEntity<String>(JSONObject.quote("User Updated Successfully"), HttpStatus.OK);
			}
			else {
				logger.error(TripExceptionMessage.USERDOESNOTEXIST);
				return new ResponseEntity<String>(JSONObject.quote(TripExceptionMessage.USERDOESNOTEXIST), HttpStatus.OK);
			}
		}catch (TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * Description: Method to list all Locations
	 * Input: 
	 * Output: List of Locations
	 */
	@GetMapping("/locations")
	public ResponseEntity<?> listAllLocations(){
		logger.info("Entered List all locations");
		List<Location> locations = tripPlannerService.findAllLocation();
		List<Location> returnedList = new ArrayList<Location>();
		locations.forEach(loc ->{
			loc.setHotelList(null);
			returnedList.add(loc);
		});
		logger.info("Listed");
		return new ResponseEntity<List<Location>>(returnedList, HttpStatus.OK);
	}
	/*
	 * Description: Method to find a location based on its id
	 * Input: Location Id
	 * Output: Location Object
	 */
	@GetMapping("/location/find")
	public ResponseEntity<?> findLocation(@RequestParam("locationId") Long locationId){
		try{
			logger.info("Entered find Location method");
			Location location= tripPlannerService.searchLocation(locationId);
			location.setHotelList(null);
			logger.info("Location returned successfully");
			return new ResponseEntity<Location>(location, HttpStatus.OK); 
		}
		catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
	}
	/*
	 * Description: Method to list transports
	 * Input: departure arrival and mode
	 * Output: List of transports
	 */
	@GetMapping("/transports")
	public ResponseEntity<?> listTransport(@RequestParam("departure") String departureFrom, @RequestParam("arrival") String arrivalAt, @RequestParam("mode") String transportMode){
		try {
			logger.info("Entered list Transport method");
			return new ResponseEntity<List<Transport>>(tripPlannerService.findTransports(departureFrom, arrivalAt, transportMode), HttpStatus.OK);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 * Description: Method to list hotels in a location
	 * Input: Location Id
	 * Output: List of hotels
	 */
	@GetMapping("/hotels")	
	public ResponseEntity<?> listHotel(@RequestParam("locationId") Long locationId){
		try {
			logger.info("Entered list Hotel method");
			Location location = tripPlannerService.searchLocation(locationId);
			if(location.getHotelList()!= null) {
				List<Hotel> hotels = location.getHotelList();
				hotels.forEach(hotel->{
					hotel.setLocation(null);
				});
				logger.info("Listed Hotels successfully");
				return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
			}
			else {
				logger.error("No hotels in this location yet");
				return new ResponseEntity<String>(JSONObject.quote("No Hotels in this Location yet"), HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 * Description: Method to find a hotel
	 * Input: Hotel id 
	 * Output: Hotel Object
	 */
	@GetMapping("/hotel/find")	
	public ResponseEntity<?> findHotel(@RequestParam("hotelId") Long hotelId){
		try {
			logger.info("Entered find hotel method");
			Hotel hotel = tripPlannerService.searchHotel(hotelId);
			hotel.setLocation(null);
			logger.info("Hotel returned successfully");
			return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * Description:Method to book transport
	 * Input: UserId, location id, transport id and booking
	 * Output: Success Message
	 */
	@PostMapping("/booktrip")
	public ResponseEntity<?> bookTransport(@RequestParam("userId") Long userId,@RequestParam("locationId") Long locationId, @RequestParam("transportId") Long transportId, @ModelAttribute Booking booking){
		try {
			logger.info("Entered Book transport method");
			this.tripPlannerService.bookTransport(userId, booking, locationId, transportId);
			logger.info("Transport booked successfully");
			return new ResponseEntity<String>(JSONObject.quote("OK"),HttpStatus.OK);
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);

		}
	}
	/*
	 * Description: Method to download ticket
	 * Input: user id
	 * Output: pdf
	 */
	@GetMapping(value = "/ticket", produces = "application/pdf")
	public ResponseEntity<String> download( HttpServletRequest request,
            HttpServletResponse response, @RequestParam("userid")Long userId, @RequestParam("ticketNo") Integer ticketNo) {
		String filePath;
		try {
			logger.info("Entered Result Pdf");
			User user = tripPlannerService.searchUser(userId);
			Booking booking = user.getUserBooking().get(ticketNo);
			if(booking != null) {
				Map<String,Object> model = new HashMap<String, Object>();
				model.put("booking", booking);
				filePath=new PDFView().GetPdf(model);
		        ServletContext context = request.getServletContext();      
		        File downloadFile = new File(filePath);
		        FileInputStream inputStream = new FileInputStream(downloadFile);
		        String mimeType = context.getMimeType(filePath);
		        if (mimeType == null) {
		            mimeType = "application/octet-stream";
		        }
		        logger.info("MIME type: " + mimeType);
		        response.setContentType(mimeType);
		        response.setContentLength((int) downloadFile.length());
		        String headerKey = "Content-Disposition";
		        String headerValue = String.format("filename=\"%s\"",
		                downloadFile.getName());
		        response.setHeader(headerKey, headerValue);
		        ServletOutputStream outStream = response.getOutputStream();
		        byte[] buffer = new byte[4096];
		        int bytesRead = -1;
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        inputStream.close();
		        outStream.close();
			}
			else {
				return new ResponseEntity<String>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e){
			
			logger.error("Error Generating Result");
			return new ResponseEntity<String>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Returning result");
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	/*
	 * Description: Method to download hotel ticket
	 * Input: user id
	 * Output: Pdf
	 */
	@GetMapping("/hotel/ticket")
	public ResponseEntity<String> download( HttpServletRequest request,
            HttpServletResponse response, @RequestParam("userid")Long userId) {
		String filePath;
		try {
			logger.info("Entered Result Pdf");
			User user = tripPlannerService.searchUser(userId);
			Booking booking = user.getUserBooking().get(user.getUserBooking().size()-2);
			if(booking != null) {
				Map<String,Object> model = new HashMap<String, Object>();
				model.put("booking", booking);
				filePath=new PDFView().GetHotelPdf(model);
		        ServletContext context = request.getServletContext();      
		        File downloadFile = new File(filePath);
		        FileInputStream inputStream = new FileInputStream(downloadFile);
		        String mimeType = context.getMimeType(filePath);
		        if (mimeType == null) {
		            mimeType = "application/octet-stream";
		        }
		        logger.info("MIME type: " + mimeType);
		        response.setContentType(mimeType);
		        response.setContentLength((int) downloadFile.length());
		        String headerKey = "Content-Disposition";
		        String headerValue = String.format("filename=\"%s\"",
		                downloadFile.getName());
		        response.setHeader(headerKey, headerValue);
		        ServletOutputStream outStream = response.getOutputStream();
		        byte[] buffer = new byte[4096];
		        int bytesRead = -1;
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        inputStream.close();
		        outStream.close();
			}
			else {
				return new ResponseEntity<String>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e){
			
			logger.error("Error Generating Result");
			return new ResponseEntity<String>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Returning result");
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
	
	/*
	 * Description: Method to book hotel
	 * Input: Hotel and the location to which it is to be added
	 * Output: Success Message
	 */
	@PostMapping("/hotel/book")
	public ResponseEntity<?> bookHotel(@RequestParam("userId") Long userId, @RequestParam("hotelId") Long hotelId, @ModelAttribute HotelBooking booking){
		try {
			logger.info("Entered hotel book method");
			tripPlannerService.bookHotel(userId, booking, hotelId);
			logger.info("Hotel booked successfully");
			return new ResponseEntity<String>(JSONObject.quote("Successfully Booked Hotel"), HttpStatus.OK);
		}catch(TripException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
}
