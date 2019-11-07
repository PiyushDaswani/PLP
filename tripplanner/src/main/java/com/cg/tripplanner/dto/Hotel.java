/**
 * 
 */
package com.cg.tripplanner.dto;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Piyush
 *
 */
@Entity
@Table(name = "hotel")
public class Hotel {
	@Id
	@Column(name = "hotel_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelId;
	@Column(name = "hotel_name")
	private String hotelName;
	@Column(name = "hotel_rating")
	private Float hotelRating;
	@Column(name = "hotel_cost")
	private Double hotelCost;
	@Column(name = "hotel_number_of_rooms")
	private Integer numberOfRooms;
	@Column(name = "images")
	private String[] images;
	@Column(name = "hotel_description")
	private String description;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "location")
	private Location location;
	@Column(name = "short_description")
	private String shortDescription;

	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	public Hotel(Long hotelId, String hotelName, Float hotelRating, Double hotelCost, Integer numberOfRooms,
			String[] images, String description, Location location) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelRating = hotelRating;
		this.hotelCost = hotelCost;
		this.numberOfRooms = numberOfRooms;
		this.images = images;
		this.description = description;
		this.location = location;
	}
	
	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Float getHotelRating() {
		return hotelRating;
	}

	public void setHotelRating(Float hotelRating) {
		this.hotelRating = hotelRating;
	}

	public Double getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(Double hotelCost) {
		this.hotelCost = hotelCost;
	}

	public Integer getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(Integer numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", hotelRating=" + hotelRating
				+ ", hotelCost=" + hotelCost + ", numberOfRooms=" + numberOfRooms + ", images="
				+ Arrays.toString(images) + ", description=" + description + ", location=" + location.getLocationName() + "]";
	}

	@Override
	public int hashCode() {
		return this.getHotelId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
}
