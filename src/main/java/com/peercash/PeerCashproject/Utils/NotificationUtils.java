package com.peercash.PeerCashproject.Utils;

import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationUtils {

    private final IEmailService emailService;
    private final UserRepository userRepository;


    private void sendRegistrationEmail(User user) {
        Map<String,Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("name", user.getName());
        data.put("document",user.getDocument());
        data.put("bankAccount",user.getBankAccount());
        data.put("registrationDate", LocalDateTime.now());

        emailService.sendEmail(user.getEmail(),"REGISTRO EXITOSO","register-user",data);

    }
    @Async
    public void sendRegistrationEmailAsync(Long userId){
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            sendRegistrationEmail(user);
        } catch (Exception e) {
            log.error("Error al enviar correo de registro para el usuario ID {}: {}", userId, e.getMessage(), e);
        }

    }
}
