package com.peercash.PeerCashproject.Config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        log.info("api_key {}",apiKey);
        log.info("api_secret {}",secretKey);
        log.info("cloud_name {}", cloudName);

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name",cloudName);
        config.put("api_key",apiKey);
        config.put("api_secret", secretKey);
        return new Cloudinary(config);
    }
}
