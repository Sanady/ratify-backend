package com.ratify.backend.services.implementations;

import com.ratify.backend.error_handlers.exceptions.ItemAlreadyExistsException;
import com.ratify.backend.models.BusinessType;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.BusinessTypeRepository;
import com.ratify.backend.services.interfaces.BusinessTypeService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_TYPE_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_TYPE_002;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {
    private final BusinessTypeRepository businessTypeRepository;

    public BusinessTypeServiceImpl(BusinessTypeRepository businessTypeRepository) {
        this.businessTypeRepository = businessTypeRepository;
    }

    @Override
    public ResponseEntity<Object> addBusinessType(String name) {
        if(Boolean.TRUE.equals(businessTypeRepository.existsByName(name))) {
            throw new ItemAlreadyExistsException(ERROR_BUSINESS_TYPE_001.getCode());
        }
        BusinessType businessType = new BusinessType(name);
        businessTypeRepository.save(businessType);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("You have successfully created business type with name " + name));
    }

    @Override
    public ResponseEntity<Object> removeBusinessType(String name) {
        BusinessType businessType = businessTypeRepository.getBusinessTypeByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_BUSINESS_TYPE_002.getCode()));
        businessTypeRepository.delete(businessType);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse("You have successfully removed business type with name " + name));
    }

    @Override
    public ResponseEntity<Object> getAllBusinessTypes() {
        List<String> names = new ArrayList<>();
        List<BusinessType> businessTypeList = businessTypeRepository.findAll(Sort.sort(this.getClass()).ascending());
        for (BusinessType businessType : businessTypeList) {
            names.add(businessType.getName());
        }
        return ResponseEntity.status(HttpStatus.OK).body(names);
    }

    @Override
    public ResponseEntity<Object> getBusinessType(String name) {
        BusinessType businessType = businessTypeRepository.getBusinessTypeByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_BUSINESS_TYPE_002.getCode()));
        return ResponseEntity.status(HttpStatus.OK).body(businessType.getName());
    }
}
