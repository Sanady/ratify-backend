package com.ratify.backend.controllers;

import com.ratify.backend.services.implementations.RoleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ratify.backend.constants.ApplicationConstants.ADD_ROLE;
import static com.ratify.backend.constants.ApplicationConstants.GET_ROLES_FROM_USER;
import static com.ratify.backend.constants.ApplicationConstants.REMOVE_ROLE;
import static com.ratify.backend.constants.ApplicationConstants.ROLE;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ROLE)
public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(GET_ROLES_FROM_USER)
    public ResponseEntity<Object> getRolesFromUser(@RequestParam String username) {
        return roleService.getAllUserRolesByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ADD_ROLE)
    public ResponseEntity<Object> addRoleToUser(@RequestParam String username, @RequestBody List<String> roles) {
        return roleService.addRoleToUser(username, roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(REMOVE_ROLE)
    public ResponseEntity<Object> removeRoleFromUser(@RequestParam String username, @RequestParam String role) {
        return roleService.deleteRoleFromUser(username, role);
    }
}
