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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.exception.TripException;
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
			user.setUserBooking(new ArrayList<Booking>());
			tripPlannerService.addUser(user);
			logger.info("User added successfully");
			return new ResponseEntity<String>(JSONObject.quote("User added successfully"),HttpStatus.OK);
		}catch(TripException e) {
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()),HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@ModelAttribute User user){
		try {
			logger.info("Entered Update user method");
			tripPlannerService.updateUser(user);
			logger.info("User updated successfully");
			return new ResponseEntity<String>(JSONObject.quote("User Updated Successfully"), HttpStatus.OK);
		}catch (TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.OK);
		}
	}
	
}
