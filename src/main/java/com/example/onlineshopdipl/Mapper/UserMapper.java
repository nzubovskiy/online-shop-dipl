package com.example.onlineshopdipl.mapper;


import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(source = "username", target = "email")
    UserDto toDTO(User user);

    @Mapping(source = "email", target = "username")
    User toEntity(UserDto userDto);

    Collection<UserDto> toDtoCollection(Collection<User> users);
}
