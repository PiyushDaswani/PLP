package com.cg.tripplanner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.tripplanner.dto.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUserId(Long userId);
	public Optional<User> findByUserEmail(String userEmail);
}
