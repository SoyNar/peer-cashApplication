package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Service.IService.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String userEmail;

//    @Override
//    public void sendEmail(String to, String subject,String templatename, Map<String, Object> body) {
//
//        try {
//            String content = renderTemplate(templatename, body);
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(userEmail);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(templatename, true);
//
//            StringBuilder htmlBody = new StringBuilder("<html><body>");
//            for (Map.Entry<String, Object> entry : body.entrySet()) {
//                htmlBody.append("<p><b>").append(entry.getKey()).append(":</b> ")
//                        .append(entry.getValue().toString()).append("</p>");
//            }
//            htmlBody.append("</body></html>");
//
//            helper.setText(htmlBody.toString(), true);
//
//            mailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
@Override
    public void sendEmail(String to, String subject, String templatename,Map<String,Object> variables ) {
        try{
            String content = renderTemplate(templatename, variables);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            //Construcción del correo
            helper.setFrom(userEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e){
            throw new RuntimeException("Hubo un error al enviar el correo", e);
        }

    }
    private String renderTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }

}
