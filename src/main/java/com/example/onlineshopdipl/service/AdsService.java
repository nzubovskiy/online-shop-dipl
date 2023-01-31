package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.dto.CreateAds;
import com.example.onlineshopdipl.dto.FullAds;
import com.example.onlineshopdipl.dto.ResponseWrapperAds;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.mapper.AdsMapper;
import com.example.onlineshopdipl.mapper.CreateAdsMapper;
import com.example.onlineshopdipl.mapper.FullAdsMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final CreateAdsMapper createAdsMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;

    public AdsService(AdsRepository adsRepository, UserService userService, CreateAdsMapper createAdsMapper, AdsMapper adsMapper, FullAdsMapper fullAdsMapper) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.createAdsMapper = createAdsMapper;
        this.adsMapper = adsMapper;
        this.fullAdsMapper = fullAdsMapper;
    }

    public ResponseWrapperAds getAllAds() {
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        List<Ads> adsList = adsRepository.findAll();
        wrapperAds.setResults(adsMapper.toAdsDtoList(adsList));
        wrapperAds.setCount(adsList.size());
        return wrapperAds;
    }
    public AdsDto createAds(String userLogin, CreateAds createAds) {
        User user = userService.getUserByLogin(userLogin);
        Ads ads = createAdsMapper.toEntity(createAds, user, createAds.getTitle());
        return adsMapper.toDTO(adsRepository.save(ads));
    }

    public FullAds getAds(Integer id) {
        Optional<Ads> optionalAds = adsRepository.findByPk(id);

        return optionalAds
                .map(fullAdsMapper::toDto)
                .orElse(null);
    }

    public void deleteAds(Integer id) {
        adsRepository.deleteById(id);
    }

    public AdsDto updateAds(Integer id, CreateAds ads) {
        Optional<Ads> optionalAds = adsRepository.findByPk(id);
        optionalAds.ifPresent(adsEntity ->{
            adsEntity.setTitle(ads.getTitle());
            adsEntity.setPrice(ads.getPrice());

            adsRepository.save(adsEntity);
        });
        return optionalAds
                .map(adsMapper::toDTO)
                .orElse(null);
    }

    public ResponseWrapperAds getMyAds(Boolean authenticated, String userLogin) {
        List<Ads> myAds = adsRepository.findByUserLogin(userLogin);
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        wrapperAds.setCount(myAds.size());
        wrapperAds.setResults(adsMapper.toAdsDtoList(myAds));
        return wrapperAds;
    }
}
