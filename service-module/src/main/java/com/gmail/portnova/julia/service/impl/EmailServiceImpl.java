package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.gmail.portnova.julia.service.constant.EmailSendingConstant.*;
@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaEmailSender;

    public void sendSimpleMessage(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(String.format(TEXT, to, password));
        javaEmailSender.send(message);
    }
}
