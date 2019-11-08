package com.cg.tripplanner.dto;

import java.io.Serializable;
/**
 * Author: Piyush Daswani
 * Description: Take the User Details for authentication
 */
public class JwtRequest implements Serializable{

	private static final long serialVersionUID = 5926468583005150707L;
	private String userEmail;
	private String userPassword;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String useremail, String userpassword) {
		this.setUserEmail(useremail);
		this.setUserPassword(userpassword);
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
