package com.example.onlineshopdipl.mapper;


import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDto toDTO(User user);
    User toUser(UserDto userDto);
}
