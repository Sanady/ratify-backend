package com.ratify.backend.controllers;

import com.ratify.backend.payloads.requests.CreateBusinessRequest;
import com.ratify.backend.payloads.requests.SetBusinessStatusRequest;
import com.ratify.backend.services.implementations.BusinessServiceImpl;
import com.ratify.backend.services.implementations.BusinessTypeServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ratify.backend.constants.ApplicationConstants.ADD_BUSINESS_TYPE;
import static com.ratify.backend.constants.ApplicationConstants.BUSINESS;
import static com.ratify.backend.constants.ApplicationConstants.BUSINESS_STATUS;
import static com.ratify.backend.constants.ApplicationConstants.CREATE_BUSINESS;
import static com.ratify.backend.constants.ApplicationConstants.DELETE_BUSINESS;
import static com.ratify.backend.constants.ApplicationConstants.GET_ALL_BUSINESS_TYPES;
import static com.ratify.backend.constants.ApplicationConstants.GET_BUSINESS;
import static com.ratify.backend.constants.ApplicationConstants.GET_BUSINESS_TYPE;
import static com.ratify.backend.constants.ApplicationConstants.REMOVE_BUSINESS_TYPE;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(BUSINESS)
public class BusinessController {
    private final BusinessServiceImpl businessService;
    private final BusinessTypeServiceImpl businessTypeService;

    public BusinessController(BusinessServiceImpl businessService, BusinessTypeServiceImpl businessTypeService) {
        this.businessService = businessService;
        this.businessTypeService = businessTypeService;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(CREATE_BUSINESS)
    public ResponseEntity<Object> createBusiness(@Valid @RequestBody CreateBusinessRequest request, @Parameter(hidden = true) @RequestHeader(name="Authorization") String token) {
        return businessService.createBusiness(request, token);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(GET_BUSINESS)
    public ResponseEntity<Object> getBusiness(@RequestParam String name) {
        return businessService.getBusiness(name);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping(BUSINESS_STATUS)
    public ResponseEntity<Object> setBusinessStatus(@RequestParam String name, @Valid @RequestBody SetBusinessStatusRequest request, @Parameter(hidden = true) @RequestHeader (name="Authorization") String token) {
        return businessService.setBusinessStatus(name, request, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(DELETE_BUSINESS)
    public ResponseEntity<Object> deleteBusiness(@RequestParam String name) {
        return businessService.deleteBusiness(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ADD_BUSINESS_TYPE)
    public ResponseEntity<Object> addBusinessType(@RequestParam String name) {
        return businessTypeService.addBusinessType(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(REMOVE_BUSINESS_TYPE)
    public ResponseEntity<Object> removeBusinessType(@RequestParam String name) {
        return businessTypeService.removeBusinessType(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_ALL_BUSINESS_TYPES)
    public ResponseEntity<Object> getAllBusinessTypes() {
        return businessTypeService.getAllBusinessTypes();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(GET_BUSINESS_TYPE)
    public ResponseEntity<Object> getBusinessType(@RequestParam String name) {
        return businessTypeService.getBusinessType(name);
    }
}
