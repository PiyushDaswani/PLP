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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tripplanner.dto.Hotel;
import com.cg.tripplanner.dto.Location;
import com.cg.tripplanner.dto.Transport;
import com.cg.tripplanner.exception.TripException;
import com.cg.tripplanner.service.TripPlannerService;

/**
 * @author Piyush
 *
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	public TripPlannerService tripPlannerService;
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/*
	 * Description: Method to add a hotel to a specific Location
	 * Input: Hotel and the location to which it is to be added
	 * Output: Success Message
	 */
	@PostMapping("/hotel/add")
	public ResponseEntity<?> addHotel(@ModelAttribute("hotel") Hotel hotel, @RequestParam("locationId") Long locationId){
		try{
			logger.info("Entered Add Hotel Method");
			System.out.println(locationId);
			Location location = tripPlannerService.searchLocation(locationId);
			System.out.println(location);
			hotel.setLocation(location);
			tripPlannerService.addHotel(hotel);
			logger.info("Hotel added successfully");
			return new ResponseEntity<String>(JSONObject.quote("Hotel Added Successfully"), HttpStatus.OK);
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/location/add")
	public ResponseEntity<?> addLocation(@ModelAttribute("location") Location location){
		try{
			location.setHotelList(new ArrayList<Hotel>());
			tripPlannerService.addLocation(location);
			return new ResponseEntity<String>("Location Added Successfully", HttpStatus.OK);
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/transport/add")
	public ResponseEntity<?> addTransport(@ModelAttribute("transport") Transport transport){
		try {
			tripPlannerService.addTransport(transport);
			return new ResponseEntity<Transport>(transport, HttpStatus.OK);
			
			
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
