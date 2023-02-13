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

import javax.persistence.EntityNotFoundException;
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
    private final FileService fileService;

    public AdsService(AdsRepository adsRepository, UserService userService, CreateAdsMapper createAdsMapper, AdsMapper adsMapper, FullAdsMapper fullAdsMapper, FileService fileService) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.createAdsMapper = createAdsMapper;
        this.adsMapper = adsMapper;
        this.fullAdsMapper = fullAdsMapper;
        this.fileService = fileService;
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
    public AdsDto createAds(Authentication authentication, CreateAds createAds, String image) {
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
        checkPermissionAlterAds(authentication, ads);
        adsRepository.deleteById(pk);
    }

    public AdsDto updateAds(Authentication authentication, Integer pk, CreateAds ads) {
        User user = userService.getUserByLogin(authentication.getName());
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserId(pk, user.getId());
        optionalAds.ifPresent(adsEntity ->{
            adsEntity.setTitle(ads.getTitle());
            adsEntity.setPrice(ads.getPrice());
            adsEntity.setDescription(ads.getDescription());
            checkPermissionAlterAds(authentication, adsEntity);
            adsRepository.save(adsEntity);
        });
        return optionalAds
                .map(adsMapper::toDTO)
                .orElse(null);
    }

    private void checkPermissionAlterAds(Authentication authentication,Ads ads) {
        boolean userIsAdmin = userService.checkUserIsAdmin(authentication);

        if (!userIsAdmin) {
            throw new RuntimeException("403 Forbidden");
        }
    }

    public ResponseWrapperAds getMyAds(String userLogin) {
        List<Ads> myAds = adsRepository.findByUserLogin(userLogin);
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

    public boolean updateAdsImage(Integer pk, String userLogin, String filePath) {
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserLogin(pk, userLogin);

        Ads adsEntity = optionalAds.orElseThrow(EntityNotFoundException::new);
        String image = adsEntity.getImage();

        if (!image.isEmpty()) {
            fileService.removeFile(image);
        }
        adsEntity.setImage(filePath);
        adsRepository.save(adsEntity);
        return true;
    }
}
