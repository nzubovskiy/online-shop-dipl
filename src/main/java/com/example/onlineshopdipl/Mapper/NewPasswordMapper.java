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
    @Mapping(source = "user.password", target = "currentPassword")
    NewPassword toDto(User user);
    @Mapping(source = "newPassword", target = "user.password")
    NewPassword toEntity(NewPassword newPassword);
}
