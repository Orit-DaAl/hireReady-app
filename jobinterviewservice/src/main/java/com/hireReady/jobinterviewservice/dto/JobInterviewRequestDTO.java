package com.hireReady.jobinterviewservice.dto;

import java.time.LocalDateTime;
import com.hireReady.jobinterviewservice.model.InterviewType;
import lombok.Data;

@Data
public class JobInterviewRequestDTO {
    private String userId;
    private InterviewType type;
    private String companyName;
    private String jobTitle;
    private String jobUrl;
    private String jobDescription; 
    private LocalDateTime interviewDate;
    private String location;
}
