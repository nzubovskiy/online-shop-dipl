package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewPasswordMapper {
    NewPassword toDto(User user);
    NewPassword toEntity(NewPassword newPassword);
}
