package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.ImageRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    public Integer saveImage(MultipartFile image){
        Image image1= new Image();
        try{
            byte[] bytes= image.getBytes();
            image1.setImage(bytes);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        image1.setId(Integer.valueOf(UUID.randomUUID().toString()));
        return imageRepository.save(image1).getId();
    }

    public byte[] getImage(Integer id){
        byte[] image= imageRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getImage();
    return new ByteArrayResource(image).getByteArray();
    }

    //Написано просто чтобы не было ошибки - переписать завтра
    public byte[] updateAdsImage(Integer id, MultipartFile image) {

        byte[] presentImage = getImage(id);
        return presentImage;
    }
    // переделать
    public void removeFile(String image) {

    }
}
