package com.example.onlineshopdipl.Mapper;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    CommentDto toDTO(Comment comment);
    Comment toEntity(CommentDto commentDto);
}
