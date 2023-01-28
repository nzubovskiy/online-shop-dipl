package com.example.onlineshopdipl.Mapper;


import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface UserMapper {
    UserDto toDTO(User user);
    User toUser(UserDto userDto);
}
