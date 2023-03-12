package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.exception.ImageNotFoundException;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.ImageRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final AdsRepository adsRepository;

    private final UserService userService;

    public ImageService(ImageRepository imageRepository, AdsRepository adsRepository, UserService userService) {
        this.imageRepository=imageRepository;
        this.adsRepository=adsRepository;
        this.userService = userService;
    }

    public Image saveImage(MultipartFile image, Ads ads){
        Image image1= new Image();
        extractInfo(image, image1);
        image1.setAds(ads);
        return imageRepository.save(image1);
    }

    public byte[] getImage(Integer id){
        byte[] image= imageRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getImage();
    return new ByteArrayResource(image).getByteArray();
    }

    public byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication)  {
        Image presentImage = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        userService.checkUserHaveRights(authentication, presentImage.getAds().getUser().getUsername());
        extractInfo(image, presentImage);
        Image newImage = imageRepository.save(presentImage);
        return newImage.getImage();
    }

    private byte[] getBytes(MultipartFile image, Image presentImage) {
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        presentImage.setImage(imageBytes);
        Image savedImage = imageRepository.save(presentImage);
        return savedImage.getImage();
    }

    private void extractInfo(MultipartFile file, Image image) {
        if (file.isEmpty()) {
            throw new ImageNotFoundException();
        }
        byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("С загружаемым файлом возникла проблема. Загрузите другой файл.");
        }
        image.setId(Integer.valueOf(UUID.randomUUID().toString()));
        image.setImage(imageBytes);
    }

}
