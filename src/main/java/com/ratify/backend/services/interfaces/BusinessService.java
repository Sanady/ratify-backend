package com.ratify.backend.services.interfaces;

import com.ratify.backend.payloads.requests.CreateBusinessRequest;
import com.ratify.backend.payloads.requests.SetBusinessStatusRequest;
import org.springframework.http.ResponseEntity;

public interface BusinessService {
    ResponseEntity<Object> createBusiness(CreateBusinessRequest request, String token);
    ResponseEntity<Object> getBusiness(String name);
    ResponseEntity<Object> setBusinessStatus(String name, SetBusinessStatusRequest request, String token);
    ResponseEntity<Object> deleteBusiness(String name);
}
