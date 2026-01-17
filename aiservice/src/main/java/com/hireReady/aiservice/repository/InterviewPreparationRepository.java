package com.hireReady.aiservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hireReady.aiservice.model.InterviewPreparation;

public interface InterviewPreparationRepository extends MongoRepository<InterviewPreparation, String>{
	List <InterviewPreparation> findByUserId(String userId);
	Optional<InterviewPreparation> findByJobInterviewId(String jobInterviewId);
}
