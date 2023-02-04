package com.example.onlineshopdipl.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer author;
    private LocalDateTime createdAt;
    private Integer pk;
    private String text;
}
