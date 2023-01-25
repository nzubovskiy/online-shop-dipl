package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.repository.AdsRepository;
import org.springframework.stereotype.Service;

@Service
public class AdsService {
    private final AdsRepository adsRepository;

    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    public Ads createAds(Ads ads) {
        return adsRepository.save(ads);
    }

    public Ads findByPk(Integer pk) {
        return adsRepository.findByPk(pk).get();
    }

}
