/**
 * 
 */
package com.cg.tripplanner.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author Piyush
 *
 */

@Entity(name = "booking")
@EntityListeners({AuditingEntityListener.class})
public class Booking {

	@Id
	@Column(name = "booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "booking_user")
	private User bookingUser;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "booking_transport")
	private Transport bookingTransport;
	@Column(name = "booking_seats")
	private Integer bookingSeats;
	@Column(name = "booking_date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate bookingDate;
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "booking")
	private HotelBooking hotelBooking;
	@Column(name = "booking_user_list")
	private String[] bookedUserList;
	@CreatedBy
	protected String createdBy;
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;
	@LastModifiedBy
	protected String lastModifiedBy;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;
	public Booking() {
		// TODO Auto-generated constructor stub
	}

	public Booking(Long bookingId, User bookingUser, Transport bookingTransport, Integer bookingSeats,
			LocalDate bookingDate, HotelBooking hotelBooking, String[] bookedUserList) {
		super();
		this.bookingId = bookingId;
		this.bookingUser = bookingUser;
		this.bookingTransport = bookingTransport;
		this.bookingSeats = bookingSeats;
		this.bookingDate = bookingDate;
		this.hotelBooking = hotelBooking;
		this.bookedUserList = bookedUserList;
	}
	
	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public User getBookingUser() {
		return bookingUser;
	}

	public void setBookingUser(User bookingUser) {
		this.bookingUser = bookingUser;
	}

	public Transport getBookingTransport() {
		return bookingTransport;
	}

	public void setBookingTransport(Transport bookingTransport) {
		this.bookingTransport = bookingTransport;
	}

	public Integer getBookingSeats() {
		return bookingSeats;
	}

	public void setBookingSeats(Integer bookingSeats) {
		this.bookingSeats = bookingSeats;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public HotelBooking getHotelBooking() {
		return hotelBooking;
	}

	public void setHotelBooking(HotelBooking hotelBooking) {
		this.hotelBooking = hotelBooking;
	}

	public String[] getBookedUserList() {
		return bookedUserList;
	}

	public void setBookedUserList(String[] bookedUserList) {
		this.bookedUserList = bookedUserList;
	}
	
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookingUser=" + bookingUser + ", bookingTransport="
				+ bookingTransport + ", bookingSeats=" + bookingSeats + ", bookingDate=" + bookingDate
				+ ", hotelBooking=" + hotelBooking + ", bookedUserList=" + Arrays.toString(bookedUserList) + "]";
	}

	@Override
	public int hashCode() {
		return this.getBookingId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
 	
}
