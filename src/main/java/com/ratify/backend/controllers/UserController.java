package com.ratify.backend.controllers;

import com.ratify.backend.payloads.requests.ChangePasswordRequest;
import com.ratify.backend.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ratify.backend.constants.ApplicationConstants.CHANGE_PASSWORD;
import static com.ratify.backend.constants.ApplicationConstants.DELETE_USER;
import static com.ratify.backend.constants.ApplicationConstants.GET_USER;
import static com.ratify.backend.constants.ApplicationConstants.USER;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(USER)
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(GET_USER)
    public ResponseEntity<Object> getUser(@RequestParam String username) {
        return userService.getUser(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<Object> deleteUser(@RequestParam String username) {
        return userService.deleteUser(username);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping(CHANGE_PASSWORD)
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @RequestHeader(name="Authorization") String token) {
        return userService.changePassword(token, changePasswordRequest);
    }
}
