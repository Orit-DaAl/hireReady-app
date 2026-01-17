package com.hireReady.jobinterviewservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hireReady.jobinterviewservice.dto.JobInterviewRequestDTO;
import com.hireReady.jobinterviewservice.dto.JobInterviewResponseDTO;
import com.hireReady.jobinterviewservice.mapper.JobInterviewMapper;
import com.hireReady.jobinterviewservice.model.JobInterview;
import com.hireReady.jobinterviewservice.repository.JobInterviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobInterviewService {

	private final JobInterviewRepository repo;
	private final UserValidationService userValidationService;
	private final RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
	public JobInterviewResponseDTO createInterview(JobInterviewRequestDTO request) {
		boolean isValidate = userValidationService.validateUser(request.getUserId());
		if(!isValidate)
			throw new RuntimeException("Invalid user");
		else {
			JobInterview jobinterview = JobInterviewMapper.toModel(request);
			repo.save(jobinterview);
			
			 // Publish to RabbitMQ for AI Processing
	        try {
	            rabbitTemplate.convertAndSend(exchange, routingKey, jobinterview);
	        } catch(Exception e) {
	            log.error("Failed to publish activity to RabbitMQ : ", e);
	        }
			
			return JobInterviewMapper.toDTO(jobinterview);
		}
		
	}
	
	public List<JobInterviewResponseDTO> getUserJobInterviews(String id) {
		List<JobInterview> jobInterviews=  repo.findByUserId(id);
		
		return jobInterviews.stream()
	            .map(JobInterviewMapper::toDTO)  
	            .collect(Collectors.toList());
	}

	public JobInterviewResponseDTO getJobInterviewById(String id) {
		return  repo.findById(id)
				 .map(JobInterviewMapper::toDTO)
				.orElseThrow(() -> new RuntimeException("Job interview not found with id: " + id));
		
	}

}
