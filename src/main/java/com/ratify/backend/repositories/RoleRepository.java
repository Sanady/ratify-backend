package com.ratify.backend.repositories;

import com.ratify.backend.models.Role;
import com.ratify.backend.models.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
        Optional<Role> findByName(ERole name);
}
