package com.hireReady.aiservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.hireReady.aiservice.model.InterviewPreparation;
import com.hireReady.aiservice.repository.InterviewPreparationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewPreparationService {

    private final InterviewPreparationRepository repo;

    public List<InterviewPreparation> getUserInterviewPreparations(String userId) {
        return repo.findByUserId(userId);
    }

    /**
     * Fetches recommendation by activity ID. 
     * Returns Optional to avoid throwing immediate exceptions while AI is processing.
     */
    public Optional<InterviewPreparation> getInterviewPreparation(String jobInterviewId) {
        return repo.findByJobInterviewId(jobInterviewId);
    }
}