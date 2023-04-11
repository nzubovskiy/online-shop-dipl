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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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

    /**
     * Receive all Ads.
     * The repository method {@link AdsRepository#findAll()} is used to get a List of all ads.
     *
     * @return {@link ResponseWrapperAds} instance with number of founded ads and List of {@link AdsDto}
     */

    public ResponseWrapperAds getAllAds() {
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        List<Ads> adsList = adsRepository.findAll();
        if (!adsList.isEmpty()) {
            wrapperAds.setResults(adsMapper.toAdsDtoList(adsList));
            wrapperAds.setCount(adsList.size());
        } else wrapperAds.setResults(Collections.emptyList());
        return wrapperAds;
    }

    /**
     * Creates new ad.
     *
     * @param createAds      {@link CreateAds} from a client
     * @param image          {@link MultipartFile} with image from a client
     * @param authentication {@link Authentication} instance from controller
     * @return {@link AdsDto} instance of the created ad
     */
    public AdsDto createAds(Authentication authentication, CreateAds createAds, MultipartFile image) {
        User user = userService.getUser(authentication.getName());

        Ads ads = createAdsMapper.toEntity(createAds, user);
        ads.setUser(user);

        Ads savedAds = adsRepository.save(ads);

        Image adsImage = imageService.saveImage(image, ads);
        List<Image> imageList = new ArrayList<>();
        imageList.add(adsImage);
        savedAds.setImages(imageList);

        return adsMapper.toDTO(savedAds);
    }

    /**
     * Search for a full ad in DB by ID.
     *
     * @param pk identification number of ad
     * @return {@link FullAds} instance for desired ad
     */
    public FullAds getAds(Integer pk) {
        Ads ads = adsRepository.findByPk(pk);
        if (ads == null) {
            throw new AdsNotFoundException("Объявление не найдено");
        }
        return fullAdsMapper.toDto(ads);
    }

    /**
     * Delete ad from DB by id.
     * The repository method {@link AdsRepository#delete(Object)} is used.
     *
     * @param pk             identification number of ad
     * @param authentication {@link Authentication} instance from controller
     */
    public void deleteAds(Integer pk, Authentication authentication) {
        Ads ads = adsRepository.findByPk(pk);
        userService.checkUserHaveRights(authentication, ads.getUser().getUsername());
        adsRepository.deleteById(pk);
    }

    /**
     * Update an ad by its ID.
     *
     * @param pk             identification number of ad
     * @param ads            {@link CreateAds} instance from a client
     * @param authentication {@link Authentication} instance from controller
     * @return {@link AdsDto} instance with updated information
     */

    public AdsDto updateAds(Authentication authentication, Integer pk, CreateAds ads) {

        Ads ads0 = adsRepository.findByPk(pk);
        User user = userService.getUser(authentication.getName());
        userService.checkUserHaveRights(authentication, ads0.getUser().getUsername());
        Ads update = createAdsMapper.toEntity(ads, user);

        ads0.setPrice(update.getPrice());
        ads0.setTitle(update.getTitle());
        ads0.setDescription(update.getDescription());

        Ads ads1 = adsRepository.save(ads0);
        return adsMapper.toDTO(ads1);
    }


    /**
     * Receive all ads for the particular user.
     *
     * @param username name of a user
     * @return {@link ResponseWrapperAds} instance with number of founded ads and List of {@link AdsDto}
     */
    public ResponseWrapperAds getMyAds(String username) {
        List<Ads> myAds = adsRepository.findByUserUsername(username);
        ResponseWrapperAds wrapperAds = new ResponseWrapperAds();
        if (!myAds.isEmpty()) {
            wrapperAds.setCount(myAds.size());
            wrapperAds.setResults(adsMapper.toAdsDtoList(myAds));
        } else wrapperAds.setResults(Collections.emptyList());
        return wrapperAds;
    }
}