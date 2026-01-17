package com.hireReady.userservice.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.hireReady.userservice.dto.UserRequestDTO;
import com.hireReady.userservice.dto.UserResponseDTO;
import com.hireReady.userservice.mapper.UserMapper;
import com.hireReady.userservice.model.User;
import com.hireReady.userservice.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository repo;
	private final PasswordEncoder passwordEncoder;
	
	public UserResponseDTO register(UserRequestDTO requestDTO) {
		if(repo.existsByEmail(requestDTO.getEmail())) {
			User existingUser= repo.findByEmail(requestDTO.getEmail());
			return UserMapper.toDTO(existingUser);
		}
		
		User newUser = UserMapper.toModel(requestDTO);
		newUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		User savedUser = repo.save(newUser);
		return UserMapper.toDTO(savedUser);
		
	}


	public UserResponseDTO getUserProfile(String id) {
		User user = repo.findById(id).orElseThrow(()->
		new RuntimeException("User Not Found"));
		return UserMapper.toDTO(user);
	}


	public Boolean existsByKeycloakId(String id) {
		return repo.existsByKeycloakId(id);
	}

}
