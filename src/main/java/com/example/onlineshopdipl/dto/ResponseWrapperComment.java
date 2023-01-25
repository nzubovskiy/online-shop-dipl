package com.example.onlineshopdipl.dto;

import com.example.onlineshopdipl.entity.Comment;
import lombok.Data;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private Comment result;
}
