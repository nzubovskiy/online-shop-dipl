package com.example.onlineshopdipl.Mapper;

import com.example.onlineshopdipl.dto.AdsDto;
import com.example.onlineshopdipl.entity.Ads;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsMapper {
AdsDto toDTO(Ads ads);
Ads toEntity(AdsDto adsDto);

}
