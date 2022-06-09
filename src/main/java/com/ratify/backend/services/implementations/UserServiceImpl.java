package com.ratify.backend.services.implementations;

import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.models.Role;
import com.ratify.backend.models.User;
import com.ratify.backend.payloads.requests.ChangePasswordRequest;
import com.ratify.backend.payloads.responses.GetUserResponse;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.security.JwtUtils;
import com.ratify.backend.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_004;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<Object> changePassword(String token, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token.substring(7)))
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(!encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword()))
            throw new InvalidInputException(ERROR_USER_002.getCode());

        else if(changePasswordRequest.getCurrentPassword().equals(changePasswordRequest.getNewPassword()))
            throw new InvalidInputException(ERROR_USER_003.getCode());

        else if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
            throw new InvalidInputException(ERROR_USER_004.getCode());

        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("Password has been changed for the user {}", user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Password has been successfully changed!"));
    }

    @Override
    public ResponseEntity<Object> getUser(String username) {
        List<String> roles = new ArrayList<>();
        User user = userRepository.findByNormalizedUsername(username.toUpperCase())
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        for(Role role : user.getRoles()) {
            roles.add(role.getName().toString());
        }

        GetUserResponse userResponse = new GetUserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                roles
        );

        log.info("Get request for user with username {}", user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Override
    public ResponseEntity<Object> deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        userRepository.delete(user);
        log.info("Delete user with username {}", user.getUsername());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageResponse("User with username " + username + " has been deleted!"));
    }
}
