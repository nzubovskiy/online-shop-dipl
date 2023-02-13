package com.example.onlineshopdipl.dto;

import com.example.onlineshopdipl.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private List<CommentDto> result;
}
