package com.hireReady.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hireReady.userservice.dto.UserRequestDTO;
import com.hireReady.userservice.dto.UserResponseDTO;
import com.hireReady.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/users")
@AllArgsConstructor

public class UserController {
	
	private final UserService userService;

	  @GetMapping("/{id}")
	  public ResponseEntity<UserResponseDTO> getUserProfile(@PathVariable String id){
		  return ResponseEntity.ok((userService.getUserProfile(id)));
	  }
	  
	  @PostMapping("/register")
	  public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request){
		  return ResponseEntity.ok(userService.register(request));
	  }
	  
	  @GetMapping("/{id}/validate")
	  public ResponseEntity<Boolean> validateUser(@PathVariable String id){
		  return ResponseEntity.ok((userService.existsByKeycloakId(id)));
	  }


}
