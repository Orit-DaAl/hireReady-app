package com.hireReady.userservice.mapper;



import com.hireReady.userservice.dto.UserRequestDTO;
import com.hireReady.userservice.dto.UserResponseDTO;
import com.hireReady.userservice.model.User;

public class UserMapper {
	
	public static UserResponseDTO toDTO(User user) {
		if (user == null) {
            return null;
        }
		UserResponseDTO UserDTO = new UserResponseDTO();
		UserDTO.setId(user.getId());
		UserDTO.setEmail(user.getEmail());
		UserDTO.setFirstName(user.getFirstName());
		UserDTO.setKeycloakId(user.getKeycloakId());
		UserDTO.setLastName(user.getLastName());
		UserDTO.setCreateAt(user.getCreateAt());
		UserDTO.setUpdateAt(user.getUpdateAt());
		return UserDTO;
	}
	
	public static User toModel(UserRequestDTO userRequestDTO) {
		if (userRequestDTO == null) {
            return null;
        }
		User user = new User();
		
		user.setEmail(userRequestDTO.getEmail());
		user.setKeycloakId(userRequestDTO.getKeycloakId());
		user.setPassword(userRequestDTO.getPassword());
		user.setFirstName(userRequestDTO.getFirstName());
		user.setLastName(userRequestDTO.getLastName());
	
		
		return user;
	}
}
