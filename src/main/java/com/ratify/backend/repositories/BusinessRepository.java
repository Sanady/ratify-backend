package com.ratify.backend.repositories;

import com.ratify.backend.models.Business;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends MongoRepository<Business, String> {
    Optional<Business> findByName(String name);
    Optional<Business> findByNormalizedName(String normalizedName);
    Boolean existsByName(String name);
    Boolean existsByNormalizedName(String normalizedName);

    @Query(value = "{ 'addresses.normalized_address' : ?0 }", count = true)
    Long countAddressesByNormalizedAddress(String normalizedAddress);
}
