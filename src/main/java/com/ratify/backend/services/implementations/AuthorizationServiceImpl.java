package com.ratify.backend.services.implementations;

import com.ratify.backend.configs.UtilityFunctions;
import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.models.Role;
import com.ratify.backend.models.User;
import com.ratify.backend.models.enums.ERole;
import com.ratify.backend.payloads.requests.LoginRequest;
import com.ratify.backend.payloads.requests.SignupRequest;
import com.ratify.backend.payloads.responses.JwtResponse;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.RoleRepository;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.security.JwtUtils;
import com.ratify.backend.services.interfaces.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_AUTHENTICATION_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_AUTHENTICATION_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_ROLE_001;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailServiceImpl emailService;

    public AuthorizationServiceImpl(AuthenticationManager authenticationManager,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder encoder,
                                    JwtUtils jwtUtils,
                                    EmailServiceImpl emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<Object> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<Object> registerUser(SignupRequest signUpRequest) {
        String normalizedUsername = UtilityFunctions.normalizedName(signUpRequest.getUsername());
        if (Boolean.TRUE.equals(userRepository.existsByNormalizedUsername(normalizedUsername))) {
            throw new InvalidInputException(ERROR_AUTHENTICATION_001.getCode());
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new InvalidInputException(ERROR_AUTHENTICATION_002.getCode());
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                normalizedUsername,
                new Date(),
                new Date());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new InvalidInputException(ERROR_ROLE_001.getCode()));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        try {
            emailService.sendMail(user.getEmail(), "Welcome " + user.getUsername(), "welcome" + user.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully!"));
    }
}
