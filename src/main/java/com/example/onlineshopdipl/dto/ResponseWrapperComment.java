package com.example.onlineshopdipl.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private List<CommentDto> results;
}
