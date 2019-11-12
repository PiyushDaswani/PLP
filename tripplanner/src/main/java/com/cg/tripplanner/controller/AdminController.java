/**
 * 
 */
package com.cg.tripplanner.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
import org.springframework.web.multipart.MultipartFile;

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
	 * Description: Method to add a location
	 * Input:  the location which it is to be added
	 * Output: Success Message
	 */
	
	@PostMapping("/location/add")
	public ResponseEntity<?> addLocation(@ModelAttribute("location") Location location){
		try{
			logger.info("Add Location method");
			location.setHotelList(new ArrayList<Hotel>());
			tripPlannerService.addLocation(location);
			return new ResponseEntity<String>("Location Added Successfully", HttpStatus.OK);
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * Description: Method to add a transport 
	 * Input: Transport to be added
	 * Output: Success Message
	 */
	@PostMapping("/transport/add")
	public ResponseEntity<?> addTransport(@ModelAttribute("transport") Transport transport){
		try {logger.info("Add transport Method");
			tripPlannerService.addTransport(transport);
			return new ResponseEntity<Transport>(transport, HttpStatus.OK);
			
			
		}catch(TripException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 * Description: Method to add a hotel to a specific Location
	 * Input: Hotel and the location to which it is to be added
	 * Output: Success Message
	 */
	@PostMapping("/hotel/add")
	public ResponseEntity<?> add(@RequestParam("locationName") String locationName, @RequestParam("exfile") MultipartFile file) {
		try {
			logger.info("Entered add hotel method");
			String UPLOAD_DIRECTORY = System.getProperty("catalina.base")+ "\\Excel_Files";
			String fileName = file.getOriginalFilename();
			File pathFile = new File(UPLOAD_DIRECTORY);
			if (!pathFile.exists()) {  //If the given path does not exist then create the directory
				pathFile.mkdir();
			}

			long time = new Date().getTime();
			pathFile = new File(UPLOAD_DIRECTORY + "\\" + time + fileName); //appending time to filename so that files cannot have same name
			file.transferTo(pathFile);    //Transfer the file to the given path
			tripPlannerService.readFromExcel(locationName, fileName, time);
			logger.info("Question added successfully");
		} catch (TripException | IOException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(JSONObject.quote("Hotels Added Successfully!"), HttpStatus.OK);
	}
}
