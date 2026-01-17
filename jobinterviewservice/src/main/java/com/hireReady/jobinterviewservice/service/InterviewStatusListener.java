package com.hireReady.jobinterviewservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.hireReady.jobinterviewservice.model.JobInterview;
import com.hireReady.jobinterviewservice.repository.JobInterviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewStatusListener {

	private final JobInterviewRepository repo;
	
	@RabbitListener(queues = "${rabbitmq.queue.statusUpdateName}")
    public void handleStatusUpdate(JobInterview updatedInterview) {
		repo.findById(updatedInterview.getId()).ifPresent(interview -> {
            interview.setStatus(updatedInterview.getStatus());
            repo.save(interview);
            log.info("Interview " + interview.getId() + " is now READY_FOR_PREP");
        });
    }
}
