package com.hireReady.jobinterviewservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "jobinterviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JobInterview {
    @Id
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

