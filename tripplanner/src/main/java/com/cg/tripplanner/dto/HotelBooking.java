/**
 * 
 */
package com.cg.tripplanner.dto;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Piyush
 *
 */
@Entity
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
	private LocalDateTime checkIn;
	@Column(name = "duration")
	private Integer duration;
	@Column(name = "rooms")
	private Integer rooms;
	
	public HotelBooking() {
		// TODO Auto-generated constructor stub
	}

	public HotelBooking(Long hotelBookingId, Booking booking, Hotel bookHotel, LocalDateTime checkIn,
			Integer duration, Integer rooms) {
		super();
		this.hotelBookingId = hotelBookingId;
		this.booking = booking;
		this.bookHotel = bookHotel;
		this.checkIn = checkIn;
		this.duration = duration;
		this.rooms = rooms;
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

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
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

	@Override
	public String toString() {
		return "booking [hotelBookingId=" + hotelBookingId + ", booking=" + booking + ", bookHotel="
				+ bookHotel + ", checkIn=" + checkIn + ", duration=" + duration + ", rooms=" + rooms + "]";
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
