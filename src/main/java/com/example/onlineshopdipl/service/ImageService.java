package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final AdsRepository adsRepository;

    public ImageService(ImageRepository imageRepository, AdsRepository adsRepository) {
        this.imageRepository=imageRepository;
        this.adsRepository=adsRepository;
    }

    public String saveImage(MultipartFile image){
        Image image1= new Image();
        try{
            byte[] bytes= image.getBytes();
            image1.setImage(bytes);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        image1.setId(UUID.randomUUID().toString());
        Image savedImage= imageRepository.saveAndFlush(image1);
        return savedImage.getId();
    }

    public byte[] getImage(Integer pk){
        adsRepository.findByPk(pk);
        return new byte[0];
    }
}
