package com.example.onlineshopdipl.mapper;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.entity.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
    @Mapping(source = "pk",target = "pk")
    @Mapping(source = "author",target = "user.id")
    @Mapping(target = "ads.pk",ignore = true)
    Comment toEntity(CommentDto commentDto);

    @Mapping(source = "pk",target = "pk")
    @Mapping(source = "user.id",target = "author")
    CommentDto toDTO(Comment comment);


    List<CommentDto> toListDto(List<Comment> allComments);
}
