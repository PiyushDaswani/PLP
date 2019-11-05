package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long>{

}
