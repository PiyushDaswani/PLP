package com.cg.tripplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long>{
	public Transport findByTransportId(Long transportId);
	public List<Transport> findByDepartureFromAndArrivalAtAndTransportMode(String departureFrom, String arrivalAt, String transportMode);
}
