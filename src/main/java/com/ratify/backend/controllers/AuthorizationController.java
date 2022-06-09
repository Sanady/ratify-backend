package com.ratify.backend.controllers;

import com.ratify.backend.payloads.requests.ResetPasswordRequest;
import com.ratify.backend.payloads.requests.LoginRequest;
import com.ratify.backend.payloads.requests.SignupRequest;
import com.ratify.backend.services.implementations.AuthorizationServiceImpl;
import com.ratify.backend.services.implementations.UserResetPasswordTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

import static com.ratify.backend.constants.ApplicationConstants.AUTHORIZATION;
import static com.ratify.backend.constants.ApplicationConstants.FORGET_PASSWORD;
import static com.ratify.backend.constants.ApplicationConstants.RESET_PASSWORD;
import static com.ratify.backend.constants.ApplicationConstants.SIGN_IN;
import static com.ratify.backend.constants.ApplicationConstants.SIGN_UP;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTHORIZATION)
public class AuthorizationController {
    @Autowired
    AuthorizationServiceImpl authenticationService;

    @Autowired
    UserResetPasswordTokenServiceImpl userResetPassword;

    @PostMapping(SIGN_IN)
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authenticationService.registerUser(signUpRequest);
    }

    @PostMapping(FORGET_PASSWORD)
    public ResponseEntity<Object> forgetPassword(@RequestParam String email) {
        return userResetPassword.createResetPasswordTokenRequest(email);
    }

    @PostMapping(RESET_PASSWORD)
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userResetPassword.resetUserPassword(token, resetPasswordRequest);
    }
}
