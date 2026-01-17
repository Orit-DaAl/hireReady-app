package com.hireReady.jobinterviewservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hireReady.jobinterviewservice.model.JobInterview;

public interface JobInterviewRepository extends MongoRepository<JobInterview, String>{
	List <JobInterview> findByUserId(String userId);
}
