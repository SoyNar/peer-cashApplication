package com.peercash.PeerCashproject.Service.Impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map uploadFile(MultipartFile multipartFile, String folder){
        try{
            Map<String, String> params = new HashMap<>();
            params.put("folder",folder);
            params.put("allowed_format", "jpg,png,pdf");
            String publicId = folder + "/" + UUID.randomUUID().toString();
            params.put("publicId", publicId);

            return cloudinary.uploader().upload(multipartFile.getBytes(), params);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo",e);
        }
    }

    public  void deleteFile(String publicID){
    try{
        cloudinary.uploader().destroy(publicID,Map.of());
    } catch (IOException e) {
        throw new RuntimeException("Error el eliminar imagen",e);
    }
    }
}
