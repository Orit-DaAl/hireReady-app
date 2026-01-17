package com.hireReady.aiservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireReady.aiservice.model.InterviewPreparation;
import com.hireReady.aiservice.model.JobInterview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewAIService {
    
    private final GeminiService geminiService;

    public InterviewPreparation generatePreparation(JobInterview interview) {
        String prompt = createPromptForInterview(interview);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {} ", aiResponse);
        return processAiResponse(interview, aiResponse);
    }

    private InterviewPreparation processAiResponse(JobInterview interview, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();

            JsonNode analysisJson = mapper.readTree(jsonContent);
            

            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "roleOverview", "Role Overview: ");
            addAnalysisSection(fullAnalysis, analysisNode, "keyRequirements", "Key Requirements: ");
            addAnalysisSection(fullAnalysis, analysisNode, "companyCulture", "Company Culture: ");

            List<String> questions = extractList(analysisJson.path("predictedQuestions"));
            List<String> communityQuestions = extractList(analysisJson.path("communityQuestions"));
            List<String> focusAreas = extractList(analysisJson.path("focusAreas"));
            List<String> tips = extractList(analysisJson.path("specificTips"));

            return InterviewPreparation.builder()
                    .jobInterviewId(interview.getId())
                    .userId(interview.getUserId())
                    .jobTitle(interview.getJobTitle())
                    .companyName(interview.getCompanyName())
                    .generalAnalysis(fullAnalysis.toString().trim())
                    .predictedQuestions(questions)
                    .communityQuestions(communityQuestions)
                    .focusAreas(focusAreas)
                    .specificTips(tips)
                    .createdAt(LocalDateTime.now())
                    .build();
                    
        } catch (Exception e) {
            log.error("Error parsing AI response", e);
            return createDefaultPreparation(interview);
        }
    }

    private InterviewPreparation createDefaultPreparation(JobInterview interview) {
        return InterviewPreparation.builder()
                .jobInterviewId(interview.getId())
                .userId(interview.getUserId())
                .jobTitle(interview.getJobTitle())
                .companyName(interview.getCompanyName())
                .generalAnalysis("We are preparing your analysis. Please check back in a moment.")
                .predictedQuestions(Collections.singletonList("Tell me about yourself and your experience."))
                .focusAreas(Collections.singletonList("Review the job description and your resume."))
                .specificTips(Arrays.asList("Research the company", "Prepare your own questions", "Stay confident"))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractList(JsonNode node) {
        List<String> list = new ArrayList<>();
        if (node.isArray()) {
            node.forEach(item -> list.add(item.asText()));
        }
        return list;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForInterview(JobInterview interview) {
        return String.format("""
								You are an expert career coach and interview strategist. Your mission is to prepare candidates at the highest possible level by providing deep, accurate, and company-specific preparation guidelines.
								
								Analyze this job interview opportunity and provide professional preparation guidelines in the following EXACT JSON format:
								{
								  "analysis": {
								    "roleOverview": "Exactly two concise and focused paragraphs analyzing the role and the strategy for success.",
								    "keyRequirements": "The most important skills needed based on the description.",
								    "companyCulture": "Exactly two sentences about the company's culture and values."
								  },
								  "predictedQuestions": [
								    "A list of at least 10 highly relevant questions tailored to the tech stack and job description."
								  ],
								  "communityQuestions": [
								    "A list of up to 50 real-world questions found on community platforms (e.g., Glassdoor, Reddit, TheWorker, and similar career forums) specifically reported for THIS company. If no specific interview data for THIS company is found, return an empty array []."
								  ],
								  "focusAreas": [
								    "Specific technical or behavioral topics to review before the interview"
								  ],
								  "specificTips": [
								    "Actionable advice for this specific interview context"
								  ]
								}
								
								Interview Details:
								Company: %s
								Role: %s
								Interview Type: %s
								Job Description/URL: %s
								
								Instructions for 'communityQuestions' (Strict Authenticity):
								- Search your knowledge base for real candidate reports specifically for the company named above.
								- Look across all available career platforms, forums, and community sites (including but not limited to Glassdoor, Reddit, and TheWorker).
								- Provide up to 50 questions if available. 
								- CRITICAL: Do not provide generic questions or questions from other companies. If you cannot find specific data for THIS company, the 'communityQuestions' array MUST be empty.
								
								Instructions for 'predictedQuestions' (Tailor to the tech stack):
								First, identify all technologies, frameworks, and tools mentioned in the Job Description.
								1. If 'TECHNICAL': Deep-dive into internal workings and best practices of their tech stack.
								2. If 'DESIGN': Focus on architecture using their specific tools.
								3. If 'SCREENING': High-level background and technical baseline.
								4. If 'HR': Behavioral and cultural alignment.
								5. If 'MANAGER': Leadership and project delivery.
								
								General Constraints:
								- 'roleOverview': Exactly 2 paragraphs.
								- 'companyCulture': Exactly 2 sentences.
								- Return ONLY the JSON object.
                """,
                interview.getCompanyName(),
                interview.getJobTitle(),
                interview.getType(),
                interview.getJobDescription() != null ? interview.getJobDescription() : interview.getJobUrl()
        );
    }
}