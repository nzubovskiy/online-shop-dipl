package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    CommentDto toDTO(Comment comment);
    Comment toEntity(CommentDto commentDto);
}
