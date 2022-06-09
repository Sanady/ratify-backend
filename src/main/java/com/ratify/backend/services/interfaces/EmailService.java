package com.ratify.backend.services.interfaces;

import com.ratify.backend.models.UserResetPasswordToken;

import javax.mail.MessagingException;

public interface EmailService {
    String generateResetPasswordContent(UserResetPasswordToken userResetPasswordToken);
    void sendMail(String receiver, String subject, String text) throws MessagingException;
}
