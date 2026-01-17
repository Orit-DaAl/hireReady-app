package com.hireReady.jobinterviewservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireReady.jobinterviewservice.dto.JobInterviewRequestDTO;
import com.hireReady.jobinterviewservice.dto.JobInterviewResponseDTO;
import com.hireReady.jobinterviewservice.service.JobInterviewService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/interviews")
public class JobInterviewController {
	private final JobInterviewService jobInterviewService;
	
	@PostMapping
	public ResponseEntity<JobInterviewResponseDTO> createInterview(@RequestBody JobInterviewRequestDTO request, @RequestHeader("X-User-ID") String userId){
		if(userId!=null) {
			log.info("userid234: " +userId);
			request.setUserId(userId);
		}
		
		return ResponseEntity.ok(jobInterviewService.createInterview(request));
		
	}
	
	 @GetMapping
	    public ResponseEntity<List<JobInterviewResponseDTO>> getUserJobInterviews(@RequestHeader("X-User-ID") String userId){
	        return ResponseEntity.ok(jobInterviewService.getUserJobInterviews(userId));
	    }


	    @GetMapping("/{activityId}")
	    public ResponseEntity<JobInterviewResponseDTO> getJobInterview(@PathVariable String activityId){
	        return ResponseEntity.ok(jobInterviewService.getJobInterviewById(activityId));
	    }
	
}
