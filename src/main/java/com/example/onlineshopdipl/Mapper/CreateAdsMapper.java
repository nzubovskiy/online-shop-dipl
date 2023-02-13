package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.CreateAds;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CreateAdsMapper {
    @Mapping(target = "pk", ignore = true)
    @Mapping(source = "image", target = "image")
    Ads toEntity(CreateAds createAds, User user, String image);

    CreateAds toDto(Ads ads);
}
