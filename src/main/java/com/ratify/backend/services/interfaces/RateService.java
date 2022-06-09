package com.ratify.backend.services.interfaces;

import org.springframework.http.ResponseEntity;

public interface RateService {
    ResponseEntity<Object> createRate(String username, String normalizedBusinessName, Integer estimate, String comment);
}
