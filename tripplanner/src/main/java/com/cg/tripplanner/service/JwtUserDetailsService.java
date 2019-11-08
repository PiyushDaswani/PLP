/**
 * 
 */
package com.cg.tripplanner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.tripplanner.dto.Booking;
import com.cg.tripplanner.dto.MyUserDetails;
import com.cg.tripplanner.dto.User;
import com.cg.tripplanner.repository.UserRepository;


/**
 * Author: Swanand Pande
 * Description: Loads the Data from the database and saves the data into the database
 */
@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> user = repository.findByUserEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with Email: " + email);
		}
		return  user.map(MyUserDetails::new).get();
	}
	
	public User save(User user) {
		User newUser = new User();
		newUser.setUserName(user.getUserName());
		newUser.setUserPassword(bcryptEncoder.encode(user.getUserPassword()));
		newUser.setIsAdmin(false);
		newUser.setUserEmail(user.getUserEmail());
		newUser.setUserBooking(new ArrayList<Booking>());
		newUser.setTripCost(0.0);
		return repository.save(newUser);
	}
}
