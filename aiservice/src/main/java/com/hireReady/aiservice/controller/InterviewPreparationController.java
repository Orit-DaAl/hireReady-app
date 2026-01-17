package com.hireReady.aiservice.controller;
import com.hireReady.aiservice.model.InterviewPreparation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireReady.aiservice.service.InterviewPreparationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interviewPreparations")
public class InterviewPreparationController {
	
	private final InterviewPreparationService interviewPreparationService ;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<InterviewPreparation>> getUserInterviewPreparations(@PathVariable String userId){
		return ResponseEntity.ok(interviewPreparationService.getUserInterviewPreparations(userId));
	}
	
	
	@GetMapping("/interview/{jobInterviewId}")
    public ResponseEntity<?> InterviewPreparation(@PathVariable String jobInterviewId) {
        return interviewPreparationService.getInterviewPreparation(jobInterviewId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); 
                // Returns 404 instead of 500 error, allowing frontend to retry
    }
}
