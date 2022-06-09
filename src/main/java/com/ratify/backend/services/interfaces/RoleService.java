package com.ratify.backend.services.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<Object> getAllUserRolesByUsername(String username);
    ResponseEntity<Object> addRoleToUser(String username, List<String> roles);
    ResponseEntity<Object> deleteRoleFromUser(String username, String role);
}
