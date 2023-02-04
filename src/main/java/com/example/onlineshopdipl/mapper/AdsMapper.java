package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.entity.Ads;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsMapper {
    Ads toEntity(AdsDto adsDto);

    AdsDto toDTO(Ads ads);

    List<AdsDto> toAdsDtoList(List<Ads> adsList);

    List<Ads> toAdsEntityList(List<AdsDto> adsDtoList);

}
