package com.ratify.backend.services.implementations;

import com.ratify.backend.configs.UtilityFunctions;
import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.models.User;
import com.ratify.backend.models.UserResetPasswordHistory;
import com.ratify.backend.models.UserResetPasswordToken;
import com.ratify.backend.models.enums.EResetPasswordMethod;
import com.ratify.backend.payloads.requests.ResetPasswordRequest;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.repositories.UserResetPasswordHistoryRepository;
import com.ratify.backend.repositories.UserResetPasswordTokenRepository;
import com.ratify.backend.services.interfaces.UserResetPasswordTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_005;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_006;

@Service
public class UserResetPasswordTokenServiceImpl implements UserResetPasswordTokenService {
    private static final Logger log = LoggerFactory.getLogger(UserResetPasswordTokenServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserResetPasswordTokenRepository userResetPasswordTokenRepository;

    @Autowired
    UserResetPasswordHistoryRepository userResetPasswordHistoryRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public ResponseEntity<Object> createResetPasswordTokenRequest(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_005.getCode()));
        if(userResetPasswordTokenRepository.countByUser(user) == 0) {
            String token = UUID.randomUUID().toString();
            UserResetPasswordToken userResetPassword = new UserResetPasswordToken(
                    user,
                    token,
                    EResetPasswordMethod.EMAIL,
                    false,
                    new Date()
            );
            userResetPasswordTokenRepository.insert(userResetPassword);
            log.info("[LOG - Forget password flow] Forget password flow has been created by email: {}", email);
            try {
                emailService.sendMail(user.getEmail(), "Reset password", emailService.generateResetPasswordContent(userResetPassword));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Please check your email inbox."));
    }

    @Override
    public ResponseEntity<Object> resetUserPassword(String token, ResetPasswordRequest request) {
        Optional<com.ratify.backend.models.UserResetPasswordToken> userResetPasswordToken = userResetPasswordTokenRepository.findByToken(token);
        if(Boolean.TRUE.equals(userResetPasswordToken.isEmpty()) ||
                Boolean.TRUE.equals(userResetPasswordToken.get().getUsedToken()) ||
                Boolean.TRUE.equals(UtilityFunctions.isAtLeastFiveMinutesAgo(userResetPasswordToken.get().getCreatedAt()))) {
            throw new InvalidInputException(ERROR_USER_006.getCode());
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())) throw new InvalidInputException(ERROR_USER_003.getCode());

        User user = userResetPasswordToken.get().getUser();
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);

        userResetPasswordToken.get().setUsedToken(true);
        userResetPasswordToken.get().setConsumedAt(new Date());
        userResetPasswordTokenRepository.save(userResetPasswordToken.get());

        UserResetPasswordHistory userResetPasswordHistory = new UserResetPasswordHistory(
                user,
                EResetPasswordMethod.EMAIL,
                new Date()
        );
        userResetPasswordHistoryRepository.save(userResetPasswordHistory);

        log.info("[LOG - Reset password flow] Reset has been finished! Username: {}, Email: {}", user.getUsername(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("You have successfully reset password, try log in with new password!"));

    }
}
