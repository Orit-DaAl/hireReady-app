package com.hireReady.jobinterviewservice.dto;

import java.time.LocalDateTime;
import com.hireReady.jobinterviewservice.model.InterviewType;
import com.hireReady.jobinterviewservice.model.InterviewStatus;
import lombok.Data;

@Data
public class JobInterviewResponseDTO {
    private String id;
    private String userId;
    private InterviewType type;
    private String companyName;
    private String jobTitle;
    private String jobUrl;
    private String jobDescription;
    private LocalDateTime interviewDate;
    private InterviewStatus status;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}