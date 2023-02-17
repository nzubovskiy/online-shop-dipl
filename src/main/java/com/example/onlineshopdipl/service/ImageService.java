package com.example.onlineshopdipl.service;

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

    public byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication)  {
        Image presentImage = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        userService.checkUserHaveRights(authentication, presentImage.getAds().getUser().getUsername());
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

    public byte[] updateUserImage(Integer id, MultipartFile image, Authentication authentication)  {
        Image presentImage = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        userService.checkUserHaveRights(authentication, presentImage.getUser().getUsername());
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

}
