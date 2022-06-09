package com.ratify.backend.configs;

import com.ratify.backend.models.UserResetPasswordToken;
import com.ratify.backend.repositories.UserResetPasswordTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ratify.backend.constants.TimeConstants.DELAY_DATABASE_PASSWORD_TOKEN_CLEAN_UP;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    UserResetPasswordTokenRepository userResetPasswordTokenRepository;

    @Scheduled(cron = DELAY_DATABASE_PASSWORD_TOKEN_CLEAN_UP)
    public void cleanUpUserPasswordTokens() {
        int count = 0;
        List<UserResetPasswordToken> userResetPasswordTokenList = userResetPasswordTokenRepository.findAll();
        for (UserResetPasswordToken userResetPasswordToken : userResetPasswordTokenList) {
            if(UtilityFunctions.isAtLeastFiveMinutesAgo(userResetPasswordToken.getCreatedAt())) {
                userResetPasswordTokenRepository.delete(userResetPasswordToken);
                log.info("[LOG - CLEAN UP DATABASE] Token with user email {} has been deleted!", userResetPasswordToken.getUser().getEmail());
                count++;
            }
        }
        log.info("[LOG - CLEAN UP DATABASE] Total {} records has been deleted!", count);
    }
}
