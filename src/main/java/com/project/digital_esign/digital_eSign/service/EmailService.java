package com.project.digital_esign.digital_eSign.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendEmail(String toEmail, String resetLink,Date expiryDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String dateExpireMail = formatter.format(new Date());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toEmail);
        message.setSubject("CONFIRM RESET PASSWORD");
        String content = String.format("\"You are requested to reset your password. "  +
                "\n Please click on this link to reset it: " +resetLink +
                "\n For security reasons, this link will expire in" + dateExpireMail);
        message.setText(content);
        mailSender.send(message);
    }
}
