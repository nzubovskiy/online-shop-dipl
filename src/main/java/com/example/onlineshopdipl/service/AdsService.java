package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.dto.CreateAds;
import com.example.onlineshopdipl.dto.ResponseWrapperAds;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.User;
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
        User user = userService.getUserByLogin(authentication.getName());
        boolean exist = adsRepository.findByTitleAndUserId(createAds.getTitle(), user.getId());
        if (exist) {
            return null;
        }
        Ads ads = createAdsMapper.toEntity(createAds, user, createAds.getTitle());
        return adsMapper.toDTO(adsRepository.save(ads));
    }

    public Ads getAds(Integer pk) {
        return adsRepository.findByPk(pk);
    }

    public void deleteAds(Integer pk, Authentication authentication) {
        Ads ads = getAds(pk);
        userService.checkUserHaveRights(authentication, ads.getUser().getUsername());;
        adsRepository.deleteById(pk);
    }

    public AdsDto updateAds(Authentication authentication, Integer pk, CreateAds ads) {
        User user = userService.getUserByLogin(authentication.getName());
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserId(pk, user.getId());
        optionalAds.ifPresent(adsEntity ->{
            adsEntity.setTitle(ads.getTitle());
            adsEntity.setPrice(ads.getPrice());
            adsEntity.setDescription(ads.getDescription());
            userService.checkUserHaveRights(authentication, adsEntity.getUser().getUsername());
            adsRepository.save(adsEntity);
        });
        return optionalAds
                .map(adsMapper::toDTO)
                .orElse(null);
    }



    public ResponseWrapperAds getMyAds(String userLogin) {
        List<Ads> myAds = adsRepository.findByUserUsername(userLogin);
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
