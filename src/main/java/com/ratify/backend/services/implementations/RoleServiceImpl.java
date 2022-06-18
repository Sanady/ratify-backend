package com.ratify.backend.services.implementations;

import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.models.Role;
import com.ratify.backend.models.User;
import com.ratify.backend.models.enums.ERole;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.RoleRepository;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.services.interfaces.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_ROLE_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_ROLE_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_ROLE_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_ROLE_004;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_001;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<Object> getAllUserRolesByUsername(String username) {
        List<String> roles = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));
        for(Role role : user.getRoles()) {
            roles.add(role.getName().toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    public ResponseEntity<Object> addRoleToUser(String username, List<String> roles) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(roles == null) {
            throw new InvalidInputException(ERROR_ROLE_002.getCode());
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                        if(user.getRoles().contains(adminRole)) {
                            break;
                        } else {
                            user.getRoles().add(adminRole);
                        }
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                        if(user.getRoles().contains(modRole)) {
                            break;
                        } else {
                            user.getRoles().add(modRole);
                        }
                        break;
                    default:
                        throw new InvalidInputException(ERROR_ROLE_003.getCode());
                }
            });
            userRepository.save(user);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse("Roles have been added!"));
    }

    @Override
    public ResponseEntity<Object> deleteRoleFromUser(String username, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(role.isEmpty()) {
            throw new InvalidInputException(ERROR_ROLE_004.getCode());
        }
        switch (role) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                if(user.getRoles().contains(adminRole)) {
                    user.getRoles().remove(adminRole);
                } else {
                    throw new InvalidInputException(ERROR_ROLE_001.getCode());
                }
                break;
            case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                if(user.getRoles().contains(modRole)) {
                    user.getRoles().remove(modRole);
                } else {
                    throw new InvalidInputException(ERROR_ROLE_001.getCode());
                }
                break;
            default:
                throw new InvalidInputException(ERROR_ROLE_001.getCode());
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse("User role has been removed!"));
    }
}
