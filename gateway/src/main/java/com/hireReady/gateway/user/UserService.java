package com.hireReady.gateway.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import com.hireReady.gateway.dto.UserRequestDTO;
import com.hireReady.gateway.dto.UserResponseDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class, e -> {
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                            return Mono.error(new RuntimeException("User Not Found: " + userId));
                        else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                            return Mono.error(new RuntimeException("Invalid Request: " + userId));
                        return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                    });
        }

	public Mono<UserResponseDTO> registerUser(UserRequestDTO registerRequest) {
		log.info("Calling User registration API for email: {}", registerRequest.getEmail());
        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad request: " + e.getMessage()));
                    else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                        return Mono.error(new RuntimeException("Internal server error: " + e.getMessage()));
                    return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                });
	}

	
}
