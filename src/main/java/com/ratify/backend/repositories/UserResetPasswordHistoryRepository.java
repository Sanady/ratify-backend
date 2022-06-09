package com.ratify.backend.repositories;

import com.ratify.backend.models.UserResetPasswordHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResetPasswordHistoryRepository extends MongoRepository<UserResetPasswordHistory, String> {
}
