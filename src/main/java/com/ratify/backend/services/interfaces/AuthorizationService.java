package com.ratify.backend.services.interfaces;

import com.ratify.backend.payloads.requests.LoginRequest;
import com.ratify.backend.payloads.requests.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthorizationService {
    ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
    ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signUpRequest);
}
