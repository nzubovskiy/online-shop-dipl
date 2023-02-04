package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.FullAds;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.Ads;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FullAdsMapper {
    FullAds toDto(Ads entity);

}

