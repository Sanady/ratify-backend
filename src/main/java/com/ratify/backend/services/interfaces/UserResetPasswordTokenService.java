package com.ratify.backend.services.interfaces;

import com.ratify.backend.payloads.requests.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface UserResetPasswordTokenService {
    ResponseEntity<Object> createResetPasswordTokenRequest(String email);
    ResponseEntity<Object> resetUserPassword(String token, ResetPasswordRequest request);
}
