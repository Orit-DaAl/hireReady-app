package com.hireReady.aiservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Jobinterview_preparations")
public class InterviewPreparation {
    
    @Id
    private String id;
    @Field("jobinterview_id")
    private String jobInterviewId;   
    @Field("user_id")
    private String userId;
    private String generalAnalysis;
    private List<String> predictedQuestions;
    private List<String> communityQuestions;
    private List<String> focusAreas;
    private List<String> specificTips;
    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;
    private String jobTitle;
    private String companyName;
}