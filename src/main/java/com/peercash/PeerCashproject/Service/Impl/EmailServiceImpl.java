package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Service.IService.IEmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String userEmail;

    @Override
    public void sendEmail(String to, String subject, Map<String, Object> body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(userEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            StringBuilder htmlBody = new StringBuilder("<html><body>");
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                htmlBody.append("<p><b>").append(entry.getKey()).append(":</b> ")
                        .append(entry.getValue().toString()).append("</p>");
            }
            htmlBody.append("</body></html>");

            helper.setText(htmlBody.toString(), true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
