package com.ratify.backend.controllers;

import com.ratify.backend.payloads.requests.RateBusinessRequest;
import com.ratify.backend.services.implementations.RateServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ratify.backend.constants.ApplicationConstants.POST_RATE;
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
    @PostMapping(POST_RATE)
    public ResponseEntity<Object> postRate(@RequestBody RateBusinessRequest request) {
        return rateService.createRate(request.getUsername(),
                request.getBusinessName(),
                request.getEstimate(),
                request.getComment());
    }
}
