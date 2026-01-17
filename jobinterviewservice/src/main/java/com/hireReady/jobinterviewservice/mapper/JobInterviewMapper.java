package com.hireReady.jobinterviewservice.mapper;

import com.hireReady.jobinterviewservice.dto.JobInterviewRequestDTO;
import com.hireReady.jobinterviewservice.dto.JobInterviewResponseDTO;
import com.hireReady.jobinterviewservice.model.JobInterview;
import com.hireReady.jobinterviewservice.model.InterviewStatus;

public class JobInterviewMapper {

    public static JobInterviewResponseDTO toDTO(JobInterview interview) {
        if (interview == null) {
            return null;
        }

        JobInterviewResponseDTO dto = new JobInterviewResponseDTO();
        
        dto.setId(interview.getId());
        dto.setUserId(interview.getUserId());
        dto.setType(interview.getType());
        dto.setCompanyName(interview.getCompanyName());
        dto.setJobTitle(interview.getJobTitle());
        dto.setJobUrl(interview.getJobUrl());
        dto.setJobDescription(interview.getJobDescription());
        dto.setInterviewDate(interview.getInterviewDate());
        dto.setStatus(interview.getStatus());
        dto.setLocation(interview.getLocation());
        dto.setCreatedAt(interview.getCreatedAt());
        dto.setUpdatedAt(interview.getUpdatedAt());
        
        return dto;
    }

    public static JobInterview toModel(JobInterviewRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        JobInterview interview = new JobInterview();
        
        interview.setUserId(requestDTO.getUserId());
        interview.setType(requestDTO.getType());
        interview.setCompanyName(requestDTO.getCompanyName());
        interview.setJobTitle(requestDTO.getJobTitle());
        interview.setJobUrl(requestDTO.getJobUrl());
        interview.setJobDescription(requestDTO.getJobDescription());
        interview.setInterviewDate(requestDTO.getInterviewDate());
        interview.setLocation(requestDTO.getLocation());
        
       
        interview.setStatus(InterviewStatus.PREP_IN_PROGRESS);
        
        return interview;
    }
}