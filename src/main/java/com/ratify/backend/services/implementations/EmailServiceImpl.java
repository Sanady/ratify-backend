package com.ratify.backend.services.implementations;

import com.ratify.backend.models.UserResetPasswordToken;
import com.ratify.backend.services.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Service
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public String generateResetPasswordContent(UserResetPasswordToken userResetPasswordToken) {
        Context context = new Context();
        context.setVariable("email", userResetPasswordToken.getUser().getEmail());
        context.setVariable("token", userResetPasswordToken.getToken());
        return templateEngine.process("emails/reset-password", context);
    }

    public void sendMail(String receiver, String subject, String text) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setTo(receiver);
        javaMailSender.send(mimeMessage);
    }
}
