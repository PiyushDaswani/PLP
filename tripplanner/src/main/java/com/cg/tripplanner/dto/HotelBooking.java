/**
 * 
 */
package com.cg.tripplanner.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Piyush
 *
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "hotel_booking")
public class HotelBooking {
	@Id
	@Column(name = "hotel_booking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelBookingId;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "booking_id")
	private Booking booking;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "book_hotel")
	private Hotel bookHotel;
	@Column(name = "check_in")
	private String checkIn;
	@Column (name = "check_out")
	private String checkOut;
	@Column(name = "duration")
	private Integer duration;
	@Column(name = "rooms")
	private Integer rooms;
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
	public HotelBooking() {
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public String toString() {
		return "HotelBooking [hotelBookingId=" + hotelBookingId + ", booking=" + booking + ", bookHotel=" + bookHotel
				+ ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", duration=" + duration + ", rooms=" + rooms
				+ "]";
	}



	public Long getHotelBookingId() {
		return hotelBookingId;
	}



	public void setHotelBookingId(Long hotelBookingId) {
		this.hotelBookingId = hotelBookingId;
	}



	public Booking getBooking() {
		return booking;
	}



	public void setBooking(Booking booking) {
		this.booking = booking;
	}



	public Hotel getBookHotel() {
		return bookHotel;
	}



	public void setBookHotel(Hotel bookHotel) {
		this.bookHotel = bookHotel;
	}



	public String getCheckIn() {
		return checkIn;
	}



	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}



	public String getCheckOut() {
		return checkOut;
	}



	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}



	public Integer getDuration() {
		return duration;
	}



	public void setDuration(Integer duration) {
		this.duration = duration;
	}



	public Integer getRooms() {
		return rooms;
	}



	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}



	public HotelBooking(Long hotelBookingId, Booking booking, Hotel bookHotel, String checkIn, String checkOut,
			Integer duration, Integer rooms) {
		super();
		this.hotelBookingId = hotelBookingId;
		this.booking = booking;
		this.bookHotel = bookHotel;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.duration = duration;
		this.rooms = rooms;
	}



	@Override
	public int hashCode() {
		return this.getHotelBookingId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
