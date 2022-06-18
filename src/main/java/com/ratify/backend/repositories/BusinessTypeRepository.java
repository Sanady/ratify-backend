package com.ratify.backend.repositories;

import com.ratify.backend.models.BusinessType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessTypeRepository extends MongoRepository<BusinessType, String> {
    Optional<BusinessType> getBusinessTypeByName(String name);

    boolean existsByName(String name);
}
