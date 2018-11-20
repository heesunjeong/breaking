package com.dkt.breaking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    public JavaMailSender emailSender;

    public boolean sendSimpleMessage(String to, String subject, String text) {
        String from = String.format("우리 오늘 맛집뿌셔? <%s>", fromMail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

        return true;
    }

}
