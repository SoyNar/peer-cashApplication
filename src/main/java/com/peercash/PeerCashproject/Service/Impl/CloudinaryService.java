package com.peercash.PeerCashproject.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map uploadFile(MultipartFile multipartFile, String folder){
        try {
            long timestamp = System.currentTimeMillis() / 1000L; // Timestamp en segundos
            String publicId = folder + "/" + UUID.randomUUID().toString();

            // Parámetros requeridos
            Map<String, Object> params = new TreeMap<>();
            params.put("folder", folder);
            params.put("api_key", cloudinary.config.apiKey);
            params.put("public_id", publicId);
            params.put("timestamp", timestamp);

            // Generar firma
            String signature = generateSignature(params);
            params.put("signature", signature);

            // Subir archivo
            return cloudinary.uploader().upload(multipartFile.getBytes(), params);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo", e);
        }
    }

    private String generateSignature(Map<String, Object> params) {
        String apiSecret = cloudinary.config.apiSecret;
        StringBuilder signData = new StringBuilder();

        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Ordenar parámetros
                .forEach(entry -> signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));

        signData.setLength(signData.length() - 1); // Remover último "&"
        signData.append(apiSecret); // Agregar API Secret

        // Generar hash SHA-1
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(signData.toString().getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generando firma", e);
        }
    }

    public void deleteFile(String publicID) {
        try {
            cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar imagen", e);
        }
    }
}
