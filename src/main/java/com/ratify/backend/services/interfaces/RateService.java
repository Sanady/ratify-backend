package com.ratify.backend.services.interfaces;

import com.ratify.backend.payloads.requests.BenefitRateRequest;
import com.ratify.backend.payloads.requests.InterviewRateRequest;
import com.ratify.backend.payloads.requests.ReviewRateRequest;
import org.springframework.http.ResponseEntity;

public interface RateService {
    ResponseEntity<Object> createBenefitRate(BenefitRateRequest benefitRateRequest);
    ResponseEntity<Object> createInterviewRate(InterviewRateRequest interviewRateRequest);
    ResponseEntity<Object> createReviewRate(ReviewRateRequest reviewRateRequest);
    ResponseEntity<Object> getAllReviewsByType(String type, int page, int size);
    ResponseEntity<Object> deleteRate(String businessName, String type, String token);
}
