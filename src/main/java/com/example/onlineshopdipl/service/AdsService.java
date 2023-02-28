package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.dto.CreateAds;
import com.example.onlineshopdipl.dto.FullAds;
import com.example.onlineshopdipl.dto.ResponseWrapperAds;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.exception.AdsNotFoundException;
import com.example.onlineshopdipl.mapper.AdsMapper;
import com.example.onlineshopdipl.mapper.CreateAdsMapper;
import com.example.onlineshopdipl.mapper.FullAdsMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final CreateAdsMapper createAdsMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final ImageService imageService;

    public AdsService(AdsRepository adsRepository, UserService userService, CreateAdsMapper createAdsMapper, AdsMapper adsMapper, FullAdsMapper fullAdsMapper, ImageService imageService) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.createAdsMapper = createAdsMapper;
        this.adsMapper = adsMapper;
        this.fullAdsMapper = fullAdsMapper;
        this.imageService = imageService;
    }

    public ResponseWrapperAds getAllAds() {
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        List<Ads> adsList = adsRepository.findAll();
        if (!adsList.isEmpty()) {
            wrapperAds.setResults(adsMapper.toAdsDtoList(adsList));
            wrapperAds.setCount(adsList.size());
        }
        else wrapperAds.setResults(Collections.emptyList());
        return wrapperAds;
    }
    public AdsDto createAds(Authentication authentication, CreateAds createAds, MultipartFile image) {
        User user = userService.getUser(authentication.getName());
        boolean exist = adsRepository.findByTitleAndUserId(createAds.getTitle(), user.getId());
        if (exist) {
            return null;
        }
        Ads ads = createAdsMapper.toEntity(createAds, user, createAds.getTitle());
        return adsMapper.toDTO(adsRepository.save(ads));
    }

    public FullAds getAds(Integer pk) {
        Ads ads = adsRepository.findByPk(pk);
        if (ads == null) {
            throw new AdsNotFoundException("Объявление не найдено");
        }
        return fullAdsMapper.toDto(ads);
    }

    public void deleteAds(Integer pk, Authentication authentication) {
        Ads ads = adsRepository.findByPk(pk);
        userService.checkUserHaveRights(authentication, ads.getUser().getUsername());;
        adsRepository.deleteById(pk);
    }

    public AdsDto updateAds(Authentication authentication, Integer pk, CreateAds ads) {
        User user = userService.getUser(authentication.getName());
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserId(pk, user.getId());
        optionalAds.ifPresent(adsEntity ->{
            userService.checkUserHaveRights(authentication, adsEntity.getUser().getUsername());
            adsEntity.setTitle(ads.getTitle());
            adsEntity.setPrice(ads.getPrice());
            adsEntity.setDescription(ads.getDescription());
            adsRepository.save(adsEntity);
        });
        return optionalAds
                .map(adsMapper::toDTO)
                .orElse(null);
    }



    public ResponseWrapperAds getMyAds(String username) {
        List<Ads> myAds = adsRepository.findByUserUsername(username);
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        if (!myAds.isEmpty()) {
            wrapperAds.setCount(myAds.size());
            wrapperAds.setResults(adsMapper.toAdsDtoList(myAds));
        } else wrapperAds.setResults(Collections.emptyList());
        return wrapperAds;
    }

    public List<Ads> getAdsLike(String title) {
        return adsRepository.searchByTitle(title);
    }
}
