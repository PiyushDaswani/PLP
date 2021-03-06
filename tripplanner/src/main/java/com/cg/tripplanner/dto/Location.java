/**
 * 
 */
package com.cg.tripplanner.dto;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Piyush
 *
 */
@Entity
@Table(name = "location")
public class Location {
	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;
	@Column(name = "location_name")
	private String locationName;
	@Column(name = "trip_duration")
	private Integer tripDuration;
	@OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
	private List<Hotel> hotelList;
	@Column(name = "location_description", length = 1500)
	private String locationDescription;
	@Column(name = "location_images")
	private String[] locationImages;
	@Column(name = "shortDescription")
	private String shortDescription; 
	
	public Location() {
		super();
	}

	public Location(Long locationId, String locationName, Integer tripDuration, List<Hotel> hotelList,
			String locationDescription, String[] locationImages) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.tripDuration = tripDuration;
		this.hotelList = hotelList;
		this.locationDescription = locationDescription;
		this.locationImages = locationImages;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(Integer tripDuration) {
		this.tripDuration = tripDuration;
	}

	public List<Hotel> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<Hotel> hotelList) {
		this.hotelList = hotelList;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String[] getLocationImages() {
		return locationImages;
	}

	public void setLocationImages(String[] locationImages) {
		this.locationImages = locationImages;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", tripDuration="
				+ tripDuration + ", hotelList=" + hotelList + ", LocationDescription=" + locationDescription
				+ ", locationImages=" + Arrays.toString(locationImages) + "]";
	}

	@Override
	public int hashCode() {
		return this.getLocationId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
}
