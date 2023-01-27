package com.example.onlineshopdipl.Mapper;

import com.example.onlineshopdipl.dto.UserDTO;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source ="user.password", target = "password")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.role", target = "role")
    UserDTO userToUserDTO(User user);

}
