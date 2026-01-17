package com.hireReady.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hireReady.userservice.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByEmail(String email);
	boolean existsByKeycloakId(String id);
	User findByEmail(String email);
}
