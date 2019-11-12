/**
 * 
 */
package com.cg.tripplanner.dto;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Piyush
 *
 */
@Entity
@Table(name = "transport")
public class Transport {
	@Id
	@Column(name = "transport_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transportId;
	@Column(name = "transport_name")
	private String transportName;
	@Column(name = "transport_mode")
	private String transportMode;
	@Column(name = "departure_from")
	private String departureFrom;
	@Column(name = "arrival_at")
	private String arrivalAt;
	@Column(name = "transport_seats")
	private Integer totalSeats;
	@DateTimeFormat(pattern = "HH:mm:ss")
	@Column(name = "departure_time")
	private LocalTime departureTime;
	@Column(name = "transport_duration")
	private Float travelDuration;
	@Column(name = "transport_cost")
	private Double travelCost;
	
	public Transport() {
		// TODO Auto-generated constructor stub
	}

	public Transport(Long transportId, String transportName, String departureFrom, String arrivalAt, Integer totalSeats,
			LocalTime departureTime, Float travelDuration, Double travelCost) {
		super();
		this.transportId = transportId;
		this.transportName = transportName;
		this.departureFrom = departureFrom;
		this.arrivalAt = arrivalAt;
		this.totalSeats = totalSeats;
		this.departureTime = departureTime;
		this.travelDuration = travelDuration;
		this.travelCost = travelCost;
	}

	public Long getTransportId() {
		return transportId;
	}

	public void setTransportId(Long transportId) {
		this.transportId = transportId;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public String getDepartureFrom() {
		return departureFrom;
	}

	public void setDepartureFrom(String departureFrom) {
		this.departureFrom = departureFrom;
	}

	public String getArrivalAt() {
		return arrivalAt;
	}

	public void setArrivalAt(String arrivalAt) {
		this.arrivalAt = arrivalAt;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public Float getTravelDuration() {
		return travelDuration;
	}

	public void setTravelDuration(Float travelDuration) {
		this.travelDuration = travelDuration;
	}

	public Double getTravelCost() {
		return travelCost;
	}

	public void setTravelCost(Double travelCost) {
		this.travelCost = travelCost;
	}
	
	
	
	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	@Override
	public String toString() {
		return "Transport [transportId=" + transportId + ", transportName=" + transportName + ", departureFrom="
				+ departureFrom + ", arrivalAt=" + arrivalAt + ", totalSeats=" + totalSeats + ", departureTime="
				+ departureTime + ", travelDuration=" + travelDuration + ", travelCost=" + travelCost + "]";
	}

	@Override
	public int hashCode() {
		return this.getTransportId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
