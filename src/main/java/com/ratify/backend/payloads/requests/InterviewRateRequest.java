package com.ratify.backend.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class InterviewRateRequest {
    @NotBlank
    private String username;

    @JsonProperty("business_name")
    @NotBlank
    private String businessName;

    @NotBlank
    private Integer estimate;

    @NotBlank
    @JsonProperty("no_offer")
    private Boolean noOffer;

    @NotBlank
    @JsonProperty("positive_experience")
    private Boolean positiveExperience;

    @NotBlank
    @Size(min = 32, max = 2048)
    private String application;

    @NotBlank
    @Size(min = 32, max = 2048)
    private String interview;

    @NotBlank
    @Size(min = 32, max = 2048)
    @JsonProperty("interview_questions")
    private String interviewQuestions;
}
