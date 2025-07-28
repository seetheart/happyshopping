package com.ecommerce.happyshopping.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
        String newFilePath = path + File.separator + newFileName;
        File folder = new File(newFilePath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(newFilePath));
        return newFilePath;
    }
}
