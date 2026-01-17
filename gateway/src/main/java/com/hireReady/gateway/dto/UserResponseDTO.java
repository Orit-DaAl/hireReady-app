package com.hireReady.gateway.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDTO {
	
		private String id;
		private String keycloakId;
		private String email;
		private String firstName;
		private String lastName;
		private LocalDateTime createAt;
		private LocalDateTime updateAt;
	}



