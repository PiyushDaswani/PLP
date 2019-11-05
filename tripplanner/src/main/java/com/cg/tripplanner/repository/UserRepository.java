package com.cg.tripplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
