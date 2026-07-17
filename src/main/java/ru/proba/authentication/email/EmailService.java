package ru.proba.authentication.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailService {
    JavaMailSender mailSender;
    EmailConfig config;

    @Async
    public void sendActivationCode(String userEmail, String activationToken) {
        try {
            String activationLink = config.getBaseURL() +
                    ":" +
                    config.getPort() +
                    "/api/auth/activate?token=" +
                    activationToken;
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setFrom(config.getFrom());
            helper.setTo(userEmail);
            helper.setSubject("Активация аккаунта");
            String content = ActivationLetterContentBuilder.buildActivationLetterHTML(activationLink);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            ExceptionUtils.rethrow(e);
        }
    }

}
