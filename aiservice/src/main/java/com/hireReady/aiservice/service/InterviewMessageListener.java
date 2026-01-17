package com.hireReady.aiservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.hireReady.aiservice.model.InterviewPreparation;
import com.hireReady.aiservice.model.InterviewStatus;
import com.hireReady.aiservice.model.JobInterview; 
import com.hireReady.aiservice.repository.InterviewPreparationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterviewMessageListener {
    
    private final InterviewPreparationRepository repo;
    private final InterviewAIService interviewAIService;
    private final RabbitTemplate rabbitTemplate; 

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.statusUpdateKey}") 
    private String statusRoutingKey;
    
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processInterviewCreated(JobInterview interview) {
        log.info("Received interview for AI processing: {} for company: {}", 
                 interview.getId(), interview.getCompanyName());
        
        try {
            InterviewPreparation preparation = interviewAIService.generatePreparation(interview);
            
            preparation.setJobInterviewId(interview.getId());
            preparation.setUserId(interview.getUserId());
            preparation.setJobTitle(interview.getJobTitle());
            preparation.setCompanyName(interview.getCompanyName());

            log.info("Generated Preparation for interview ID: {}", preparation.getJobInterviewId());
            
            InterviewPreparation saved = repo.save(preparation);
            log.info("Saved Interview Preparation with ID: {}", saved.getId());
            
            interview.setStatus(InterviewStatus.READY_FOR_PREP);
            
            log.info("Sending status update (READY_FOR_PREP) for interview: {}", interview.getId());
            rabbitTemplate.convertAndSend(exchange, statusRoutingKey, interview);
            
        } catch (Exception e) {
            log.error("Error processing interview message: {}", e.getMessage(), e);
     
        }
    }
}