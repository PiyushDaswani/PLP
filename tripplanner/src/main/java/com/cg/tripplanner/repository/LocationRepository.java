/**
 * 
 */
package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.Location;

/**
 * @author Piyush
 *
 */
public interface LocationRepository extends JpaRepository<Location, Long>{
	public Location findByLocationId(Long locationId);
}
