package com.hireReady.userservice.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String keycloakId;
	@Column(unique = true, nullable = false)
	private String email;
	// Password stored only if not managed by external IdP (e.g. Keycloak)
	@Column(nullable =false)
	private String password;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;
	@CreationTimestamp
	private LocalDateTime createAt;
	@UpdateTimestamp
	private LocalDateTime updateAt;
}
