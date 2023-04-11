package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.ImageDto;
import com.example.onlineshopdipl.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDto entityToDto(Image image);

    Image imageDtoToEntity(ImageDto imageDto);
}
