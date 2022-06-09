package com.ratify.backend.repositories;

import com.ratify.backend.models.Rate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends MongoRepository<Rate, String> {
    Optional<Rate> getRateByUsername(String username);

    boolean existsByUsernameAndBusinessNormalizedName(String username, String businessNormalizedName);
}
