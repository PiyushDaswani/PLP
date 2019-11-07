/**
 * 
 */
package com.cg.tripplanner.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.Transport;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.exception.TripException;
import com.cg.tripplanner.exception.TripExceptionMessage;
import com.cg.tripplanner.service.TripPlannerService;

/**
 * @author Piyush
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private TripPlannerService tripPlannerService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@ModelAttribute User user){
		try {
			logger.info("Entered add user method");
			user.setPlannedTrip(null);
			user.setIsAdmin(false);
			user.setTripCost(0.0);
			user.setUserBooking(new ArrayList<Booking>());
			tripPlannerService.addUser(user);
			logger.info("User added successfully");
			return new ResponseEntity<String>(JSONObject.quote("User added successfully"),HttpStatus.OK);
		}catch(TripException e) {
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
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
				return new ResponseEntity<String>(JSONObject.quote(TripExceptionMessage.USERDOESNOTEXIST), HttpStatus.OK);
			}
		}catch (TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/transport/book")
	public ResponseEntity<?> bookTransport(@RequestParam("userId") Long userId,@ModelAttribute Transport transport, @RequestParam("locationId") Long locationId,@ModelAttribute Booking booking )
	{
		try {
			logger.info("Entered Book Transport method");
			tripPlannerService.bookTransport(userId, booking, locationId, transport);
			logger.info("Transport booked successfully");
			return new ResponseEntity<String>(JSONObject.quote("Ticket Booked Successfully"), HttpStatus.OK); 
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
