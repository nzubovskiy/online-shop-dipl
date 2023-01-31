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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    UserDto dtoToUserDto(FullAds fullAds);

    @Mapping(source = "pk", target = "pk")
    @Mapping(target = "user.password", ignore = true)
    Ads toModel(FullAds fullAds);

    @Mapping(source = "pk", target = "pk")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    FullAds toDto(Ads entity);
}

