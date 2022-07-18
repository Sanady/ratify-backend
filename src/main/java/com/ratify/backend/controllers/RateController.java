package com.ratify.backend.controllers;

import com.ratify.backend.payloads.requests.BenefitRateRequest;
import com.ratify.backend.payloads.requests.InterviewRateRequest;
import com.ratify.backend.payloads.requests.ReviewRateRequest;
import com.ratify.backend.services.implementations.RateServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.ratify.backend.constants.ApplicationConstants.GET_REVIEW_RATES;
import static com.ratify.backend.constants.ApplicationConstants.POST_BENEFIT_RATE;
import static com.ratify.backend.constants.ApplicationConstants.POST_INTERVIEW_RATE;
import static com.ratify.backend.constants.ApplicationConstants.POST_REVIEW_RATE;
import static com.ratify.backend.constants.ApplicationConstants.RATE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(RATE)
public class RateController {
    private final RateServiceImpl rateService;

    public RateController(RateServiceImpl rateService) {
        this.rateService = rateService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(POST_BENEFIT_RATE)
    public ResponseEntity<Object> postBenefitRate(@RequestBody BenefitRateRequest request) {
        return rateService.createBenefitRate(request);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(POST_INTERVIEW_RATE)
    public ResponseEntity<Object> postInterviewRate(@RequestBody InterviewRateRequest request) {
        return rateService.createInterviewRate(request);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(POST_REVIEW_RATE)
    public ResponseEntity<Object> postReviewRate(@RequestBody ReviewRateRequest request) {
        return rateService.createReviewRate(request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(GET_REVIEW_RATES)
    public ResponseEntity<Object> getReviews(@RequestParam String type,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size) {
        return rateService.getAllReviewsByType(type, page, size);
    }
}
