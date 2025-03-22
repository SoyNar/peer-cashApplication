package com.peercash.PeerCashproject.Config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-secret}")
    private String secretKey;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",cloudName);
        config.put("apy_key",apiKey);
        config.put("secret_key", secretKey);
        return new Cloudinary(config);
    }
}
