package com.ratify.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class InterviewRate extends Rate {
    private Rate rate;

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

    public InterviewRate(Rate rate,
                         Boolean noOffer,
                         Boolean positiveExperience,
                         String application,
                         String interview,
                         String interviewQuestions) {
        this.rate = rate;
        this.noOffer = noOffer;
        this.positiveExperience = positiveExperience;
        this.application = application;
        this.interview = interview;
        this.interviewQuestions = interviewQuestions;
    }
}
