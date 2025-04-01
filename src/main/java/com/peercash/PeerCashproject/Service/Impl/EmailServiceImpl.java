package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Service.IService.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    @Autowired
 private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private  String userEmail;

    @Override
    public void sendEmail(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
