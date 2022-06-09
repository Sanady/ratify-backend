package com.ratify.backend.repositories;

import com.ratify.backend.models.User;
import com.ratify.backend.models.UserResetPasswordToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserResetPasswordTokenRepository extends MongoRepository<UserResetPasswordToken, String> {
    Integer countByUser(User user);
    Optional<UserResetPasswordToken> findByToken(String token);
    List<UserResetPasswordToken> findAll();
}
