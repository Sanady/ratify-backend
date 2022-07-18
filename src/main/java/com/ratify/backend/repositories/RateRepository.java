package com.ratify.backend.repositories;

import com.ratify.backend.models.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends MongoRepository<Rate, String> {
    Optional<Rate> getRateByUsername(String username);
    Optional<Object> findByUsernameAndBusinessNormalizedNameAndType(String username, String businessName, String type);
    Page<Object> findAllByType(String type, Pageable pageable);

    boolean existsByUsernameAndBusinessNormalizedNameAndType(String username, String businessNormalizedName, String type);
}
