package com.cg.tripplanner.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "user_email", unique = true)
	private String userEmail;
	@Column(name = "user_password")
	private String userPassword;
	@Column(name = "is_admin")
	private Boolean isAdmin;
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "bookingUser")
	private List<Booking> userBooking;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "plannedTrip")
	private Location plannedTrip;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(Long userId, String userName, String userEmail, String userPassword, Boolean isAdmin,
			List<Booking> userBooking) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.isAdmin = isAdmin;
		this.userBooking = userBooking;
	}
	
	public Location getPlannedTrip() {
		return plannedTrip;
	}

	public void setPlannedTrip(Location plannedTrip) {
		this.plannedTrip = plannedTrip;
	}

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public Boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public List<Booking> getUserBooking() {
		return userBooking;
	}


	public void setUserBooking(List<Booking> userBooking) {
		this.userBooking = userBooking;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
				+ userPassword + ", isAdmin=" + isAdmin + ", userBooking=" + userBooking + ", plannedTrip="
				+ plannedTrip + "]";
	}

	@Override
	public int hashCode() {
		return this.getUserId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
