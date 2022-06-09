package com.ratify.backend.services.interfaces;

import com.ratify.backend.payloads.requests.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> changePassword(String token, ChangePasswordRequest changePasswordRequest);
    ResponseEntity<Object> getUser(String username);
    ResponseEntity<Object> deleteUser(String username);
}
