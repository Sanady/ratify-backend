package com.ratify.backend.services.interfaces;

import org.springframework.http.ResponseEntity;

public interface BusinessTypeService {
    ResponseEntity<Object> addBusinessType(String name);
    ResponseEntity<Object> removeBusinessType(String name);
    ResponseEntity<Object> getAllBusinessTypes();
    ResponseEntity<Object> getBusinessType(String name);
}
