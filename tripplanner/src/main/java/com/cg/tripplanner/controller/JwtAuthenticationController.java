/**
 * 
 */
package com.cg.tripplanner.controller;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.tripplanner.config.JwtTokenUtil;
import com.cg.tripplanner.dto.JwtRequest;
import com.cg.tripplanner.dto.JwtResponse;
import com.cg.tripplanner.dto.Location;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.service.JwtUserDetailsService;
import com.cg.tripplanner.service.TripPlannerService;

/**
 * Author: Piyush Daswani
 * Description: Mappings for authenticating and registering the user
 */
@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private TripPlannerService tripPlannerService;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		logger.info("Token Creation method");
		authenticate(authenticationRequest.getUserEmail(), authenticationRequest.getUserPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUserEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		try {
			logger.info("Entered Registering method");
			System.out.println(user.toString());
			User returnedUser = userDetailsService.save(user);
			return new ResponseEntity<User>(returnedUser,HttpStatus.OK);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote("Same email not allowed"),HttpStatus.BAD_REQUEST);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>());
			authenticationManager.authenticate(token);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@RequestMapping(value = "/searchuser", method = RequestMethod.GET)
	public ResponseEntity<?> searchUser(@RequestParam("email") String email) throws Exception {
		try {
			logger.info("Search User method");
			User returnedUser = tripPlannerService.findByEmail(email);
			returnedUser.setUserBooking(null);
			returnedUser.setPlannedTrip(null);
			logger.info("User found successfully");
			return new ResponseEntity<User>(returnedUser,HttpStatus.OK);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote("Same email not allowed"),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/location/names")
	public ResponseEntity<?> locationNames(@RequestParam("locationName") String name) {
		try {
			logger.info("Entered location name finder method");
			List<Location> locationList = tripPlannerService.findAllLocation();
			List<String> locations = new ArrayList<>();
			locationList.forEach(loc ->{
				if(!loc.getLocationName().equals(name)) {
					locations.add(loc.getLocationName());
				}
			});
			logger.info("Returned list of location names successfully");
			return new ResponseEntity<List<String>>(locations, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(JSONObject.quote("Something went wrong!"),HttpStatus.BAD_REQUEST);
		}
	}
}

