package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.exception.FileSizeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private String uploadedDir;

    private static long SIZE_LIMIT;

    public String saveFile(String imagePath, MultipartFile image) throws IOException {
        if (!correctFileSize(image.getSize())) {
            throw new FileSizeException();
        }
        return saveFile(imagePath, image);
    }

    private boolean correctFileSize(long size) {
        return size < SIZE_LIMIT;
    }

    public void removeFileByPath(String filePath) {
    }

    public void removeFile(String image) {
    }
}
